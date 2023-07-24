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
