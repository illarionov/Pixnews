/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
