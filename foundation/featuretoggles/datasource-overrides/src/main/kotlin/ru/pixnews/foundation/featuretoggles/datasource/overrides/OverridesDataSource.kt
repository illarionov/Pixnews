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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.featuretoggles.internal.DataSourceResult
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSource
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.DataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.pub.FeatureToggleException
import ru.pixnews.libraries.functional.NetworkRequestStatus.Complete
import ru.pixnews.libraries.functional.NetworkRequestStatus.Loading

public class OverridesDataSource constructor(
    private val dataStore: DataStore<Overrides>,
    private val serializers: Map<@JvmSuppressWildcards ExperimentKey, ExperimentVariantSerializer>,
    ioDispatcherProvider: IoCoroutineDispatcherProvider,
    logger: Logger,
) : FeatureToggleDataSource {
    private val log = logger.withTag(TAG)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log.e(throwable) { "Unhandled coroutine exception in $TAG" }
    }
    private val scope: CoroutineScope = CoroutineScope(
        ioDispatcherProvider.get() +
                SupervisorJob() +
                exceptionHandler +
                CoroutineName("$TAG scope"),
    )
    private val overridesFlow: StateFlow<DataSourceResult<Overrides>> = dataStore.data
        .map { overrides ->
            DataSourceResult.success(overrides) as DataSourceResult<Overrides>
        }
        .catch { err -> emit(DataSourceResult.failure(DataSourceError(err))) }
        .stateIn(scope, started = SharingStarted.WhileSubscribed(), Loading)
    private val initJob: Job = scope.launch {
        val result = overridesFlow
            .first { status -> status != Loading }
        log.v { "Initialization complete. Status: $result" }
    }

    public constructor(
        context: Context,
        serializers: Map<@JvmSuppressWildcards ExperimentKey, ExperimentVariantSerializer>,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
    ) : this(context.applicationContext.overridesDataStore, serializers, ioDispatcherProvider, logger)

    override fun getAssignedVariant(experimentKey: ExperimentKey): Flow<DataSourceResult<ExperimentVariant>> {
        return overridesFlow
            .map { dataSourceResult ->
                when (dataSourceResult) {
                    is Loading -> Loading
                    is Complete -> dataSourceResult.result.fold(
                        ifLeft = { DataSourceResult.failure(it) },
                        ifRight = { overrides -> deserializeVariant(overrides, experimentKey) },
                    )
                }
            }
    }

    @Suppress("TooGenericExceptionCaught")
    private fun deserializeVariant(
        overrides: Overrides,
        experimentKey: ExperimentKey,
    ): DataSourceResult<ExperimentVariant> {
        return try {
            val payloadContainer = overrides.getTogglesOrDefault(experimentKey.key, null)
            return if (payloadContainer != null) {
                val variant = serializers[experimentKey]?.fromString(experimentKey, payloadContainer.payload)
                    ?: throw FeatureToggleException("No serializer for experiment `$experimentKey` found")
                DataSourceResult.success(variant)
            } else {
                DataSourceResult.failure<FeatureToggleDataSourceError>(ExperimentNotFound)
            }
        } catch (other: Throwable) {
            DataSourceResult.failure(DataSourceError(other))
        }
    }

    public suspend fun setAssistedVariant(experimentKey: ExperimentKey, variant: ExperimentVariant?) {
        dataStore.updateData { overrides ->
            if (variant != null) {
                val payload: String = serializers[experimentKey]?.toString(experimentKey, variant)
                    ?: throw FeatureToggleException("No serializer for experiment `$experimentKey` found")
                overrides.toBuilder()
                    .putToggles(experimentKey.key, VariantPayload.newBuilder().setPayload(payload).build())
                    .build()
            } else {
                overrides.toBuilder()
                    .removeToggles(experimentKey.key)
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