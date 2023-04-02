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
package ru.pixnews.features.calendar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.features.calendar.PreviewFixtures
import ru.pixnews.features.calendar.model.CalendarScreenState
import ru.pixnews.features.calendar.ui.content.CalendarScreenContent
import ru.pixnews.features.calendar.ui.header.CalendarScreenHeader
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun CalendarScreenSuccessContent(
    state: CalendarScreenState.Success,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .testTag("calendar:success_content")
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
