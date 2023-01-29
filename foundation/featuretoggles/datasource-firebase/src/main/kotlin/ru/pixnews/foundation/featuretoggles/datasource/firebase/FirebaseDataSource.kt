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
package ru.pixnews.foundation.featuretoggles.datasource.firebase

import co.touchlab.kermit.Logger
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
import ru.pixnews.libraries.functional.NetworkRequestStatus.Loading
import kotlin.time.Duration.Companion.seconds

@Suppress("TooGenericExceptionCaught")
public class FirebaseDataSource(
    private val remoteConfig: FirebaseRemoteConfig,
    private val serializers: Map<@JvmSuppressWildcards ExperimentKey, ExperimentVariantSerializer>,
    ioDispatcherProvider: IoCoroutineDispatcherProvider,
    logger: Logger,
) : FeatureToggleDataSource {
    private val log = logger.withTag("FirebaseDataSource")
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log.e(throwable) { "Unhandled coroutine exception in FirebaseDataSource" }
    }
    private val scope: CoroutineScope = CoroutineScope(
        ioDispatcherProvider.get() +
                SupervisorJob() +
                exceptionHandler +
                CoroutineName("FirebaseDataSource scope"),
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
            val value = remoteConfig.getValue(experimentKey.key)
            if (value.source != FirebaseRemoteConfig.VALUE_SOURCE_STATIC) {
                val serialized = value.asString()
                val activeVariant = serializers[experimentKey]?.fromString(experimentKey, serialized)
                    ?: throw FeatureToggleException("No serializer for experiment `$experimentKey` found")
                emit(DataSourceResult.success(activeVariant))
            } else {
                log.w { "No remote config value for experiment $experimentKey" }
                emit(DataSourceResult.failure<FeatureToggleDataSourceError>(ExperimentNotFound))
            }
        }
            .catch { error -> emit(DataSourceResult.failure(DataSourceError(error))) }
    }

    override suspend fun awaitUntilInitialized() {
        initFirebaseJob.join()
    }

    public fun cancel() {
        scope.cancel()
    }

    private companion object {
        private val FIREBASE_FETCH_CONFIG_INITIAL_DELAY = 10.seconds
    }
}
