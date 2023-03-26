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
package ru.pixnews.app

import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
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
        experiments: Set<@JvmSuppressWildcards Experiment>,
    ): DefaultVariantDataSource = DefaultVariantDataSource(experiments)

    @SingleIn(AppScope::class)
    @Provides
    @Suppress("MagicNumber")
    public fun provideTestFeatureManager(
        experiments: Set<@JvmSuppressWildcards Experiment>,
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
