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
package ru.pixnews.feature.calendar.test.element

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.calendar.test.element.util.havingAchestor

public class DateSelectionHeaderElement(
    private val composeRule: AndroidComposeTestRule<*, *>,
    private val parentSemanticMatcher: SemanticsMatcher? = null,
) {
    public val yearMonthPickerMatcher: SemanticsMatcher =
        hasTestTag(CalendarTestTag.HEADER_YEAR_MONTH_PICKER_BUTTON)
    public val weekDaysRootMatcher: SemanticsMatcher = hasTestTag(CalendarTestTag.HEADER_WEEK_DAYS_ROW)

    public fun yearMonthPicker(useUnmergedTree: Boolean = false): SemanticsNodeInteraction = composeRule.onNode(
        yearMonthPickerMatcher.havingAchestor(parentSemanticMatcher),
        useUnmergedTree = useUnmergedTree,
    )

    public fun weekDaysRoot(): SemanticsNodeInteraction = composeRule.onNode(
        weekDaysRootMatcher.havingAchestor(parentSemanticMatcher),
    )
}