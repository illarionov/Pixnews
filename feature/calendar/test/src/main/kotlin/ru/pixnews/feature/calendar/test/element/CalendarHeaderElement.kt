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

public class CalendarHeaderElement(
    private val composeRule: AndroidComposeTestRule<*, *>,
) {
    public val searchBoxMatcher: SemanticsMatcher = hasTestTag(CalendarTestTag.HEADER_SEARCH_BOX)
    public val dateSelectionHeader: DateSelectionHeaderElement = DateSelectionHeaderElement(composeRule)
    public val chipsRow: ChipsRowElement = ChipsRowElement(composeRule)

    public fun searchBox(): SemanticsNodeInteraction = composeRule.onNode(searchBoxMatcher)
}
