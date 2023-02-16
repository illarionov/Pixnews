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

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.internal.DuplicateExperimentException
import javax.inject.Qualifier
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Module
@ContributesTo(AppScope::class)
public object FirebaseRemoteConfigModule {
    private val MINIMUM_FETCH_INTERVAL_DEBUG = 5.minutes
    private val MINIMUM_FETCH_INTERVAL_RELEASE = 12.hours

    @Provides
    @ExperimentFirebaseDefaults
    public fun provideExperimentDefaults(
        experiments: Set<@JvmSuppressWildcards Experiment>,
        serializers: Map<@JvmSuppressWildcards ExperimentKey, @JvmSuppressWildcards ExperimentVariantSerializer>,
    ): Map<String, String> {
        return experiments
            .groupingBy { experiment -> experiment.key.stringValue }
            .reduce { key, _, element ->
                throw DuplicateExperimentException("Duplicate key '$key' encountered for element '$element'")
            }
            .mapValues { (_, experiment) ->
                val serializer = serializers[experiment.key]
                    ?: error("No serializer for experiment `$experiment`")
                val controlGroup = experiment.variants[ExperimentVariantKey.CONTROL_GROUP]
                    ?: error("No control group on experiment `$experiment`")
                val serialized = serializer.toString(experiment.key, controlGroup)
                serialized
            }
    }

    @Provides
    @SingleIn(AppScope::class)
    public fun provideFirebaseRemoteConfig(
        appConfig: AppConfig,
        @ExperimentFirebaseDefaults firebaseDefaults: Map<String, String>,
    ): FirebaseRemoteConfig {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = when {
                appConfig.isDebug -> MINIMUM_FETCH_INTERVAL_DEBUG
                else -> MINIMUM_FETCH_INTERVAL_RELEASE
            }.inWholeSeconds
        }
        return Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(firebaseDefaults)
        }
    }

    @Qualifier
    internal annotation class ExperimentFirebaseDefaults
}
