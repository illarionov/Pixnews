/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.datasource.overrides

import android.content.Context
import androidx.datastore.core.DataStore
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
import ru.pixnews.library.coroutines.newChildSupervisorScope
import ru.pixnews.library.functional.RequestStatus
import ru.pixnews.library.functional.RequestStatus.Loading
import ru.pixnews.library.functional.completeFailure
import ru.pixnews.library.functional.mapComplete

@Suppress("MaxLineLength")
public class OverridesDataSource constructor(
    private val dataStore: DataStore<Overrides>,
    private val serializers: Map<ExperimentKey, ExperimentVariantSerializer>,
    rootCoroutineScope: CoroutineScope,
    private val backgroundDispatcher: CoroutineDispatcher,
    logger: Logger,
) : FeatureToggleDataSource {
    private val log = logger.withTag(TAG)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log.e(throwable) { "Unhandled coroutine exception in $TAG" }
    }
    private val scope: CoroutineScope = rootCoroutineScope.newChildSupervisorScope(
        backgroundDispatcher + exceptionHandler + CoroutineName("$TAG scope"),
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
        serializers: Map<ExperimentKey, ExperimentVariantSerializer>,
        rootCoroutineScope: CoroutineScope,
        backgroundDispatcher: CoroutineDispatcher,
        logger: Logger,
    ) : this(
        context.applicationContext.overridesDataStore,
        serializers,
        rootCoroutineScope,
        backgroundDispatcher,
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
        }.flowOn(backgroundDispatcher)
    }

    private fun deserializeVariant(
        overrides: Overrides,
        experimentKey: ExperimentKey,
    ): Either<FeatureToggleDataSourceError, ExperimentVariant> {
        val variant = overrides.toggles[experimentKey.stringValue]
            ?: return ExperimentNotFound.completeFailure()
        return try {
            variant.deserialize(experimentKey).right()
        } catch (toggleException: FeatureToggleException) {
            DataSourceError(toggleException).left()
        }
    }

    private fun Overrides.deserialize(): Map<ExperimentKey, ExperimentVariant> {
        return this.toggles
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
                overrides.copy(
                    toggles = overrides.toggles + (experimentKey.stringValue to VariantPayload(payload)),
                )
            } else {
                overrides.copy(
                    toggles = overrides.toggles - experimentKey.stringValue,
                )
            }
        }
    }

    public suspend fun clearOverrides() {
        dataStore.updateData { Overrides() }
    }

    override suspend fun awaitUntilInitialized() {
        initJob.join()
    }

    private companion object {
        private const val TAG: String = "OverridesDataSource"
    }
}
