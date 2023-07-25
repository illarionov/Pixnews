/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.ui.header

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams.RenderingMode.SHRINK
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test
import ru.pixnews.feature.calendar.model.GameListViewMode.GRID
import ru.pixnews.foundation.ui.theme.PixnewsTheme

class CalendarScreenHeaderScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(
            locale = "en",
        ),
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SHRINK,
        showSystemUi = false,
    )

    @Test
    fun checkCalendarScreenHeader() {
        paparazzi.snapshot {
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
}
