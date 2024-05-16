/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PREVIEW_ANNOTATION")

package ru.pixnews.feature.calendar.ui.header

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.feature.calendar.model.GameListViewMode.GRID
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.ScreenshotTestPreview

class CalendarScreenHeaderScreenshotTest {
    @Composable
    @ScreenshotTestPreview
    fun CalendarScreenHeaderTest() {
        PixnewsTheme(useDynamicColor = false) {
            CalendarScreenHeader(
                onSearch = {},
                onDaySelectionClick = {},
                onYearMonthSelectionClick = {},
                onOpenFilterClick = {},
                onViewModeClick = {},
                onFilterChipClick = {},
                activeDate = remember { mutableStateOf(CalendarHeaderTestFixtures.activeDate) },
                games = CalendarHeaderTestFixtures.gamesSummaryOnActiveDate,
                chips = CalendarHeaderTestFixtures.chips.toImmutableList(),
                viewMode = remember { mutableStateOf(GRID) },
            )
        }
    }
}
