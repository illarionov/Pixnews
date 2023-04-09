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
package ru.pixnews.inject

import android.content.Context
import co.touchlab.kermit.Logger
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.datasource.firebase.FirebaseDataSource
import ru.pixnews.foundation.featuretoggles.datasource.overrides.OverridesDataSource
import ru.pixnews.foundation.featuretoggles.internal.DataSourceWithPriority
import ru.pixnews.foundation.featuretoggles.internal.DefaultVariantDataSource
import ru.pixnews.foundation.featuretoggles.internal.FeatureManagerImpl
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSource
import javax.inject.Named

@ContributesTo(AppScope::class)
@Module
public object FeatureManagerModule {
    @Provides
    public fun providesDefaultVariantDataSource(
        experiments: DaggerSet<Experiment>,
    ): DefaultVariantDataSource = DefaultVariantDataSource(experiments)

    @Provides
    @SingleIn(AppScope::class)
    @Named("firebase")
    public fun providesFirebaseDataSource(
        remoteConfig: FirebaseRemoteConfig,
        serializers: DaggerMap<ExperimentKey, ExperimentVariantSerializer>,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
    ): FeatureToggleDataSource {
        return FirebaseDataSource(remoteConfig, serializers, ioDispatcherProvider, logger)
    }

    @Provides
    @SingleIn(AppScope::class)
    @Named("overrides")
    public fun providesOverridesDataSource(
        @ApplicationContext context: Context,
        serializers: DaggerMap<ExperimentKey, ExperimentVariantSerializer>,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
        appConfig: AppConfig,
    ): FeatureToggleDataSource? {
        return if (appConfig.isDebug) {
            OverridesDataSource(context, serializers, ioDispatcherProvider, logger)
        } else {
            null
        }
    }

    @SingleIn(AppScope::class)
    @Provides
    @Suppress("MagicNumber")
    public fun provideFeatureManager(
        experiments: DaggerSet<Experiment>,
        defaultDataSource: DefaultVariantDataSource,
        @Named("overrides") overridesDataSource: FeatureToggleDataSource?,
        @Named("firebase") firebaseDataSource: FeatureToggleDataSource?,
        logger: Logger,
    ): FeatureManager {
        return FeatureManagerImpl(
            experiments = experiments,
            dataSources = buildList {
                overridesDataSource?.let {
                    add(DataSourceWithPriority(it, 0))
                }
                firebaseDataSource?.let {
                    add(DataSourceWithPriority(firebaseDataSource, 10))
                }
                add(DataSourceWithPriority(defaultDataSource, 19))
            },
            logger,
        )
    }
}
