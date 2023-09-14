/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.pixnews.feature.calendar.CalendarViewModel
import ru.pixnews.feature.calendar.PreviewFixtures
import ru.pixnews.feature.calendar.model.CalendarScreenState
import ru.pixnews.feature.calendar.model.CalendarScreenState.Loading
import ru.pixnews.feature.calendar.model.CalendarScreenState.Success
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.foundation.di.ui.base.viewmodel.injectedViewModel
import ru.pixnews.foundation.ui.design.overlay.PixnewsLoadingOverlay
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.PhonePreviews

@Composable
internal fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = injectedViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle()

    CalendarScreen(
        state = state.value,
        modifier = modifier,
    )
}

@Composable
internal fun CalendarScreen(
    state: CalendarScreenState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        Loading -> Unit
        is Success -> CalendarScreenSuccessContent(state)
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = state is Loading,
        enter = EnterTransition.None,
    ) {
        Box(
            modifier = Modifier
                .testTag(CalendarTestTag.LOADING_OVERLAY)
                .background(MaterialTheme.colorScheme.surface),
        ) {
            PixnewsLoadingOverlay(
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
    }
}

@PhonePreviews
@Composable
private fun CalendarScreenPreview() {
    PixnewsTheme {
        Surface {
            CalendarScreen(state = PreviewFixtures.previewSuccessState)
        }
    }
}
