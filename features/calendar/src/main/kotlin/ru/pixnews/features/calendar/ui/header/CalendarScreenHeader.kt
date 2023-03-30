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
package ru.pixnews.features.calendar.ui.header

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Clock.System
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.pixnews.features.calendar.PreviewFixtures
import ru.pixnews.features.calendar.PreviewFixtures.FilterChip
import ru.pixnews.features.calendar.model.GameListFilterChip
import ru.pixnews.features.calendar.model.GameListViewMode
import ru.pixnews.features.calendar.model.GameListViewMode.GRID
import ru.pixnews.features.calendar.model.GamesOnDay
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun CalendarScreenHeader(
    onSearch: (String) -> Unit,
    onDaySelectionClick: (LocalDate) -> Unit,
    onYearMonthSelectionClick: () -> Unit,
    onOpenFilterClick: () -> Unit,
    onViewModeClick: () -> Unit,
    onFilterChipClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    activeDate: State<LocalDate> = remember { mutableStateOf(System.todayIn(TimeZone.currentSystemDefault())) },
    games: ImmutableMap<LocalDate, GamesOnDay> = PreviewFixtures.gamesSummaryOnActiveDate,
    chips: ImmutableList<GameListFilterChip> = FilterChip.sampleChips.toImmutableList(),
    viewMode: State<GameListViewMode> = remember { mutableStateOf(GRID) },
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = spacedBy(4.dp),
    ) {
        SearchBox(
            modifier = Modifier
                .testTag("calendar:header:search_box"),
            onSearch = onSearch,
        )
        DateSelectionHeader(
            activeDate = activeDate,
            onYearMonthSelectionClick = onYearMonthSelectionClick,
            games = games,
            onDaySelectionClick = onDaySelectionClick,
        )
        ChipsRow(
            chips = chips,
            viewMode = viewMode,
            onOpenFilterClick = onOpenFilterClick,
            onViewModeClick = onViewModeClick,
            onFilterChipClick = onFilterChipClick,
        )
    }
}

@Preview
@Composable
private fun CalendarScreenHeaderPreview() {
    PixnewsTheme(useDynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            CalendarScreenHeader(
                onSearch = {},
                onDaySelectionClick = {},
                onYearMonthSelectionClick = {},
                onOpenFilterClick = {},
                onViewModeClick = {},
                onFilterChipClick = {},
            )
        }
    }
}
