/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.datasource.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.internal.DuplicateExperimentException
import javax.inject.Qualifier
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@ContributesTo(AppScope::class)
@Module
public object FirebaseRemoteConfigModule {
    private val MINIMUM_FETCH_INTERVAL_DEBUG = 5.minutes
    private val MINIMUM_FETCH_INTERVAL_RELEASE = 12.hours

    @Provides
    @ExperimentFirebaseDefaults
    public fun provideExperimentDefaults(
        experiments: DaggerSet<Experiment>,
        serializers: DaggerMap<ExperimentKey, ExperimentVariantSerializer>,
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
        firebaseApp: FirebaseApp,
        @ExperimentFirebaseDefaults firebaseDefaults: Map<String, String>,
    ): FirebaseRemoteConfig {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = when {
                appConfig.isDebug -> MINIMUM_FETCH_INTERVAL_DEBUG
                else -> MINIMUM_FETCH_INTERVAL_RELEASE
            }.inWholeSeconds
        }
        return FirebaseRemoteConfig.getInstance(firebaseApp).apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(firebaseDefaults)
        }
    }

    @Qualifier
    internal annotation class ExperimentFirebaseDefaults
}
