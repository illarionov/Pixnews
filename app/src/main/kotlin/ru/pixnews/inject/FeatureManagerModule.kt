/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import android.content.Context
import co.touchlab.kermit.Logger
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.RootCoroutineScope
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
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
        rootScope: RootCoroutineScope,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
    ): FeatureToggleDataSource {
        return FirebaseDataSource(remoteConfig, serializers, rootScope, ioDispatcherProvider.get(), logger)
    }

    @Provides
    @SingleIn(AppScope::class)
    @Named("overrides")
    public fun providesOverridesDataSource(
        @ApplicationContext context: Context,
        serializers: DaggerMap<ExperimentKey, ExperimentVariantSerializer>,
        rootCoroutineScope: RootCoroutineScope,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
        appConfig: AppConfig,
    ): FeatureToggleDataSource? {
        return if (appConfig.isDebug) {
            OverridesDataSource(context, serializers, rootCoroutineScope, ioDispatcherProvider.get(), logger)
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
