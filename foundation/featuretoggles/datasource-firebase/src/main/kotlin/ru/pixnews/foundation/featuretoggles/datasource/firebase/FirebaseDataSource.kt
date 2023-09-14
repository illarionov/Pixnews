/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.datasource.firebase

import co.touchlab.kermit.Logger
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.FeatureToggleException
import ru.pixnews.foundation.featuretoggles.internal.DataSourceResult
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSource
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.DataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.library.coroutines.newChildSupervisorScope
import ru.pixnews.library.functional.RequestStatus.Loading
import kotlin.time.Duration.Companion.seconds

@Suppress("TooGenericExceptionCaught")
public class FirebaseDataSource(
    private val remoteConfig: FirebaseRemoteConfig,
    private val serializers: DaggerMap<ExperimentKey, ExperimentVariantSerializer>,
    rootCoroutineScope: CoroutineScope,
    backgroundDispatcher: CoroutineDispatcher,
    logger: Logger,
) : FeatureToggleDataSource {
    private val log = logger.withTag("FirebaseDataSource")
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log.e(throwable) { "Unhandled coroutine exception in FirebaseDataSource" }
    }
    private val scope: CoroutineScope = rootCoroutineScope.newChildSupervisorScope(
        backgroundDispatcher + exceptionHandler + CoroutineName("FirebaseDataSource scope"),
    )
    private val initFirebaseJob: Job = scope.launch {
        val activatedStatus = remoteConfig.activate().await()
        remoteConfig.ensureInitialized().await()
        log.d {
            if (activatedStatus) {
                "Fetched config activated"
            } else {
                "All the latest fetched configs were already activated"
            }
        }
    }

    // Strategy 3: Load new values for next startup
    // https://firebase.google.com/docs/remote-config/loading#strategy_3_load_new_values_for_next_startup
    init {
        scope.launch {
            try {
                initFirebaseJob.join()
                delay(FIREBASE_FETCH_CONFIG_INITIAL_DELAY)
                remoteConfig.fetch().await()
                this@FirebaseDataSource.log.d("New config fetched")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (other: Throwable) {
                log.e(other) { "Can not fetch new firebase config" }
            }
        }
    }

    override fun getAssignedVariant(experimentKey: ExperimentKey): Flow<DataSourceResult<ExperimentVariant>> {
        return flow {
            if (!initFirebaseJob.isCompleted) {
                emit(Loading)
                initFirebaseJob.join()
            }
            val value = remoteConfig.getValue(experimentKey.stringValue)
            if (value.source != FirebaseRemoteConfig.VALUE_SOURCE_STATIC) {
                val serialized = value.asString()
                val activeVariant = serializers[experimentKey]?.fromString(experimentKey, serialized)
                    ?: throw FeatureToggleException("No serializer for experiment `$experimentKey` found")
                emit(DataSourceResult.completeSuccess(activeVariant))
            } else {
                log.w { "No remote config value for experiment $experimentKey" }
                emit(DataSourceResult.completeFailure<FeatureToggleDataSourceError>(ExperimentNotFound))
            }
        }
            .catch { error -> emit(DataSourceResult.completeFailure(DataSourceError(error))) }
    }

    override suspend fun awaitUntilInitialized() {
        initFirebaseJob.join()
    }

    private companion object {
        private val FIREBASE_FETCH_CONFIG_INITIAL_DELAY = 10.seconds
    }
}
