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
import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.pixnews.features.featuretoggles.FeatureToggleListViewModel
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.di.ui.base.viewmodel.ViewModelKey
import ru.pixnews.foundation.di.ui.base.viewmodel.ViewModelScope
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.datasource.overrides.OverridesDataSource

@Module
@ContributesTo(ViewModelScope::class)
public object ToggleListViewModelModule {
    @Provides
    @IntoMap
    @ViewModelKey(FeatureToggleListViewModel::class)
    public fun providesViewModel(
        featureManager: FeatureManager,
        overridesDataSource: OverridesDataSource,
        logger: Logger,
    ): ViewModel {
        return FeatureToggleListViewModel(featureManager, overridesDataSource, logger)
    }

    @Provides
    @SingleIn(ViewModelScope::class)
    public fun providesOverridesDataSource(
        @ApplicationContext context: Context,
        serializers: Map<@JvmSuppressWildcards ExperimentKey, @JvmSuppressWildcards ExperimentVariantSerializer>,
        ioDispatcherProvider: IoCoroutineDispatcherProvider,
        logger: Logger,
    ): OverridesDataSource {
        return OverridesDataSource(context, serializers, ioDispatcherProvider, logger)
    }
}
