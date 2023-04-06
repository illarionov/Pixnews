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

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import assertk.assertThat
import assertk.assertions.hasSize
import kotlinx.collections.immutable.toImmutableList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.feature.calendar.CalendarHeaderTestFixtures
import ru.pixnews.feature.calendar.model.GameListViewMode.GRID
import ru.pixnews.feature.calendar.testing.element.CalendarHeaderElement
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import ru.pixnews.foundation.testing.util.assertHorizontalPaddingBetweenAdjacentItems
import ru.pixnews.foundation.testing.util.assertVerticalPaddingBetweenAdjacentItems
import ru.pixnews.foundation.ui.theme.PixnewsTheme

class CalendarScreenHeaderPaddingsTest : BaseInstrumentedTest() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    val header = CalendarHeaderElement(composeTestRule)

    @Before
    fun setup() {
        composeTestRule.setContent {
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

    @Test
    fun monthPickerButton_shouldHaveCorrectPaddingAndSize() {
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between search box and month picker",
            expectedPadding = 8.dp,
            topItem = header.searchBox(),
            bottomItem = header.dateSelectionHeader.yearMonthPicker(),
        )

        header.dateSelectionHeader.yearMonthPicker()
            .assertHeightIsEqualTo(40.dp)
    }

    @Test
    fun weekDaysRow_shouldHaveCorrectPaddingsAndSize() {
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between month picker and week days",
            expectedPadding = 4.dp,
            topItem = header.dateSelectionHeader.yearMonthPicker(),
            bottomItem = header.dateSelectionHeader.weekDaysRoot(),
        )

        header.dateSelectionHeader.weekDaysRoot()
            .getUnclippedBoundsInRoot()
            .height
            .assertIsEqualTo(42.dp, "height", tolerance = 2.dp)

        val days = header.dateSelectionHeader.weekDaysRoot()
            .onChildren()
            .filter(hasClickAction())
            .fetchSemanticsNodes()

        assertThat(days).hasSize(7)
    }

    @Test
    fun chipsRow_shouldHaveCorrectPaddingAndSize() {
        val chipButtons = header.chipsRow.chipsButtons()

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between week days and chips",
            expectedPadding = 12.dp,
            topItem = header.dateSelectionHeader.weekDaysRoot(),
            bottomItem = chipButtons.onFirst(),
        )

        chipButtons.onFirst()
            .assertHeightIsEqualTo(32.dp)

        assertHorizontalPaddingBetweenAdjacentItems(
            subject = "padding between chips",
            expectedPadding = 8.dp,
            startItem = chipButtons[0],
            endItem = chipButtons[1],
        )
    }
}
