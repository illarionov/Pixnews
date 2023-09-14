/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.feature.calendar.PreviewFixtures
import ru.pixnews.feature.calendar.model.CalendarScreenState
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.calendar.ui.content.CalendarScreenContent
import ru.pixnews.feature.calendar.ui.header.CalendarScreenHeader
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun CalendarScreenSuccessContent(
    state: CalendarScreenState.Success,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .testTag(CalendarTestTag.SUCCESS_CONTENT)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CalendarScreenHeader(
            onSearch = {},
            onDaySelectionClick = {},
            onYearMonthSelectionClick = {},
            onOpenFilterClick = {},
            onViewModeClick = {},
            onFilterChipClick = {},
        )
        CalendarScreenContent(
            games = state.games,
            majorReleases = state.majorReleases,
            onMajorReleaseClick = {},
            onGameClick = {},
            onFavouriteClick = {},
        )
    }
}

@Preview
@Composable
private fun CalendarMainContentPreview() {
    PixnewsTheme(useDynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            CalendarScreenSuccessContent(
                state = PreviewFixtures.previewSuccessState,
            )
        }
    }
}
