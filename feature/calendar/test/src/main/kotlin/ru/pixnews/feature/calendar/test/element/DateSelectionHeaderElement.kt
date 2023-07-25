/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
