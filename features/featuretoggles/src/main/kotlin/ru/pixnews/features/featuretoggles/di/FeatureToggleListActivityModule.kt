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
package ru.pixnews.features.featuretoggles.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.features.featuretoggles.FeatureToggleListViewModel
import ru.pixnews.foundation.di.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.scopes.ActivityScope
import ru.pixnews.foundation.di.scopes.SingleIn
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.datasource.overrides.OverridesDataSource
import javax.inject.Named

@Module
@ContributesTo(ActivityScope::class)
public object FeatureToggleListActivityModule {
    @Provides
    @SingleIn(ActivityScope::class)
    public fun providesOverridesDataSource(
        @ApplicationContext context: Context,
        serializers: Map<@JvmSuppressWildcards ExperimentKey, @JvmSuppressWildcards ExperimentVariantSerializer>,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
    ): OverridesDataSource {
        return OverridesDataSource(context, serializers, ioDispatcherProvider, logger)
    }

    @Provides
    @Reusable
    @Named("FeatureTogglesViewModel")
    public fun featureTogglesViewModelFactory(
        featureManager: FeatureManager,
        overridesDataSource: OverridesDataSource,
        logger: Logger,
    ): ViewModelProvider.Factory = viewModelFactory {
        initializer {
            FeatureToggleListViewModel(featureManager, overridesDataSource, logger)
        }
    }
}
