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
package ru.pixnews.features.calendar.element

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChildren
import ru.pixnews.features.calendar.R as calendarR

internal class CalendarHeaderElement(
    composeRule: AndroidComposeTestRule<*, *>,
) {
    val dateSelectionHeader = DateSelectionHeaderElement(composeRule)
    val chipsRow = ChipsRowElement(composeRule)

    class DateSelectionHeaderElement(
        private val composeRule: AndroidComposeTestRule<*, *>,
        private val parentSemanticMatcher: SemanticsMatcher? = null,
    ) {
        val yearMonthPickerMatcher = hasTestTag("calendar:header:year_month_picker_button")
        val weekDaysRootMatcher = hasTestTag("calendar:header:week_days_row")

        fun yearMonthPicker(useUnmergedTree: Boolean = false): SemanticsNodeInteraction = composeRule.onNode(
            yearMonthPickerMatcher.havingAchestor(parentSemanticMatcher),
            useUnmergedTree = useUnmergedTree,
        )

        fun weekDaysRoot(): SemanticsNodeInteraction = composeRule.onNode(
            weekDaysRootMatcher.havingAchestor(parentSemanticMatcher),
        )
    }

    class ChipsRowElement(
        private val composeRule: AndroidComposeTestRule<*, *>,
        private val parentSemanticMatcher: SemanticsMatcher? = null,
    ) {
        private val context
            get() = composeRule.activity

        val lazyListMatcher: SemanticsMatcher
            get() = hasTestTag("calendar:header:chips_lazy_row")
        val filterButtonMatcher
            get() = hasClickAction()
                .and(
                    hasAnyChild(
                        hasContentDescription(
                            context.getString(calendarR.string.open_filter_menu_content_description),
                        ),
                    ),
                )
        val gridButtonMatcher
            get() = hasClickAction()
                .and(
                    hasAnyChild(
                        hasContentDescription(
                            context.getString(calendarR.string.switch_display_mode_to_grid_content_description),
                        ).or(
                            hasContentDescription(
                                context.getString(calendarR.string.switch_display_mode_to_list_content_description),
                            ),
                        ),
                    ),
                )

        fun chipsLazyList(): SemanticsNodeInteraction = composeRule.onNode(
            lazyListMatcher.havingAchestor(parentSemanticMatcher),
        )

        fun chipsButtons(): SemanticsNodeInteractionCollection = chipsLazyList()
            .onChildren()
            .filter(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox))

        fun filterButton(): SemanticsNodeInteraction = composeRule.onNode(
            filterButtonMatcher.havingAchestor(parentSemanticMatcher),
        )

        fun gridButton(useUnmergedTree: Boolean = false): SemanticsNodeInteraction = composeRule.onNode(
            gridButtonMatcher.havingAchestor(parentSemanticMatcher),
            useUnmergedTree = useUnmergedTree,
        )
    }
}

private fun SemanticsMatcher.havingAchestor(parent: SemanticsMatcher?): SemanticsMatcher {
    if (parent == null) {
        return this
    }
    return this.and(havingAchestor(parent))
}