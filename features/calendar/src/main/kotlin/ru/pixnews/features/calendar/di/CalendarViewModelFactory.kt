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
package ru.pixnews.features.calendar.di

import androidx.lifecycle.createSavedStateHandle
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.pixnews.features.calendar.CalendarViewModel
import ru.pixnews.foundation.di.ui.base.viewmodel.ViewModelFactory
import ru.pixnews.foundation.di.ui.base.viewmodel.ViewModelKey
import ru.pixnews.foundation.di.ui.base.viewmodel.ViewModelScope
import ru.pixnews.foundation.featuretoggles.FeatureManager

@Module
@ContributesTo(ViewModelScope::class)
public object CalendarViewModelFactory {
    @Provides
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    public fun providesViewModel(
        featureManager: FeatureManager,
        logger: Logger,
    ): ViewModelFactory = ViewModelFactory {
        CalendarViewModel(
            featureManager = featureManager,
            logger = logger,
            savedStateHandle = it.createSavedStateHandle(),
        )
    }
}
