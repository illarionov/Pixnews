/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
import ru.pixnews.feature.calendar.model.GameListViewMode.GRID
import ru.pixnews.feature.calendar.test.element.CalendarHeaderElement
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.instrumented.test.util.assertHorizontalPaddingBetweenAdjacentItems
import ru.pixnews.library.instrumented.test.util.assertVerticalPaddingBetweenAdjacentItems

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
