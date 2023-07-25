/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.ui.header

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
import ru.pixnews.feature.calendar.PreviewFixtures
import ru.pixnews.feature.calendar.PreviewFixtures.FilterChip
import ru.pixnews.feature.calendar.model.GameListFilterChip
import ru.pixnews.feature.calendar.model.GameListViewMode
import ru.pixnews.feature.calendar.model.GameListViewMode.GRID
import ru.pixnews.feature.calendar.model.GamesOnDay
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
public fun CalendarScreenHeader(
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
                .testTag(CalendarTestTag.HEADER_SEARCH_BOX),
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
