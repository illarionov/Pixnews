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
package ru.pixnews.feature.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.pixnews.feature.calendar.model.CalendarScreenState
import ru.pixnews.foundation.di.ui.base.viewmodel.ContributesViewModel
import ru.pixnews.foundation.featuretoggles.FeatureManager

@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
@ContributesViewModel
internal class CalendarViewModel(
    featureManager: FeatureManager,
    logger: Logger,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val log = logger.withTag("CalendarViewModel")
    val viewState: StateFlow<CalendarScreenState> = MutableStateFlow(PreviewFixtures.previewSuccessState)
}
