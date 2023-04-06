/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.foundation.featuretoggles.datasource.overrides

import android.content.Context
import androidx.datastore.core.DataStore
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.FeatureToggleException
import ru.pixnews.foundation.featuretoggles.internal.DataSourceResult
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSource
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.DataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.library.coroutines.asRequestStatus
import ru.pixnews.library.functional.RequestStatus
import ru.pixnews.library.functional.RequestStatus.Loading
import ru.pixnews.library.functional.completeFailure
import ru.pixnews.library.functional.mapComplete

@Suppress("MaxLineLength")
public class OverridesDataSource constructor(
    private val dataStore: DataStore<Overrides>,
    private val serializers: Map<@JvmSuppressWildcards ExperimentKey, @JvmSuppressWildcards ExperimentVariantSerializer>,
    private val backgroundDispatcherProvider: IoCoroutineDispatcherProvider,
    logger: Logger,
) : FeatureToggleDataSource {
    private val log = logger.withTag(TAG)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log.e(throwable) { "Unhandled coroutine exception in $TAG" }
    }
    private val scope: CoroutineScope = CoroutineScope(
        backgroundDispatcherProvider.get() +
                SupervisorJob() +
                exceptionHandler +
                CoroutineName("$TAG scope"),
    )
    private val overridesFlow: StateFlow<RequestStatus<Throwable, Overrides>> = dataStore.data
        .asRequestStatus()
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = Loading,
        )
    private val initJob: Job = scope.launch {
        val result = overridesFlow
            .first { status -> status != Loading }
        log.v { "Initialization complete. Status: $result" }
    }

    public constructor(
        context: Context,
        serializers: Map<@JvmSuppressWildcards ExperimentKey, @JvmSuppressWildcards ExperimentVariantSerializer>,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
    ) : this(
        context.applicationContext.overridesDataStore,
        serializers,
        ioDispatcherProvider,
        logger,
    )

    override fun getAssignedVariant(experimentKey: ExperimentKey): Flow<DataSourceResult<ExperimentVariant>> {
        return overridesFlow.map { loadingOrComplete ->
            loadingOrComplete.mapComplete { result: Either<Throwable, Overrides> ->
                result
                    .mapLeft(::DataSourceError)
                    .flatMap { overrides ->
                        deserializeVariant(overrides, experimentKey)
                    }
            }
        }
    }

    public fun getOverrides(): Flow<RequestStatus<Throwable, Map<ExperimentKey, ExperimentVariant>>> {
        return overridesFlow.map { loadingOrComplete ->
            loadingOrComplete.mapComplete { result: Either<Throwable, Overrides> ->
                result.flatMap { overrides ->
                    Either.catch { overrides.deserialize() }
                }
            }
        }.flowOn(backgroundDispatcherProvider.get())
    }

    private fun deserializeVariant(
        overrides: Overrides,
        experimentKey: ExperimentKey,
    ): Either<FeatureToggleDataSourceError, ExperimentVariant> {
        val variant = overrides.getTogglesOrDefault(experimentKey.stringValue, null)
            ?: return ExperimentNotFound.completeFailure()
        return try {
            variant.deserialize(experimentKey).right()
        } catch (toggleException: FeatureToggleException) {
            DataSourceError(toggleException).left()
        }
    }

    private fun Overrides.deserialize(): Map<ExperimentKey, ExperimentVariant> {
        return this.togglesMap
            .map { (key, value) ->
                val experimentKey = ExperimentKey(key)
                experimentKey to value.deserialize(experimentKey)
            }
            .toMap()
    }

    private fun VariantPayload.deserialize(experimentKey: ExperimentKey): ExperimentVariant {
        return serializers[experimentKey]?.fromString(experimentKey, this.payload)
            ?: throw FeatureToggleException("No serializer for experiment `$experimentKey` found")
    }

    public suspend fun setOverride(experimentKey: ExperimentKey, variant: ExperimentVariant?) {
        dataStore.updateData { overrides ->
            if (variant != null) {
                val payload: String = serializers[experimentKey]?.toString(experimentKey, variant)
                    ?: throw FeatureToggleException("No serializer for experiment `$experimentKey` found")
                overrides.toBuilder()
                    .putToggles(experimentKey.stringValue, VariantPayload.newBuilder().setPayload(payload).build())
                    .build()
            } else {
                overrides.toBuilder()
                    .removeToggles(experimentKey.stringValue)
                    .build()
            }
        }
    }

    public suspend fun clearOverrides() {
        dataStore.updateData { Overrides.getDefaultInstance() }
    }

    override suspend fun awaitUntilInitialized() {
        initJob.join()
    }

    public fun cancel() {
        scope.cancel()
    }

    private companion object {
        private const val TAG: String = "OverridesDataSource"
    }
}
