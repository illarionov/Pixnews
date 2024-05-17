/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.internal.DataSourceWithPriority
import ru.pixnews.foundation.featuretoggles.internal.DefaultVariantDataSource
import ru.pixnews.foundation.featuretoggles.internal.FeatureManagerImpl

@ContributesTo(AppScope::class, replaces = [FeatureManagerModule::class])
@Module
object TestFeatureManagerModule {
    @Provides
    public fun providesDefaultVariantDataSource(
        experiments: DaggerSet<Experiment>,
    ): DefaultVariantDataSource = DefaultVariantDataSource(experiments)

    @SingleIn(AppScope::class)
    @Provides
    @Suppress("MagicNumber")
    public fun provideTestFeatureManager(
        experiments: DaggerSet<Experiment>,
        defaultDataSource: DefaultVariantDataSource,
        logger: Logger,
    ): FeatureManager {
        return FeatureManagerImpl(
            experiments = experiments,
            dataSources = buildList {
                add(DataSourceWithPriority(defaultDataSource, 19))
            },
            logger,
        )
    }
}
