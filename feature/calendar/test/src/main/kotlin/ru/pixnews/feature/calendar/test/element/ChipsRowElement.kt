/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.test.element

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
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.calendar.test.element.util.havingAchestor
import ru.pixnews.foundation.ui.design.R as uiDesignR

public class ChipsRowElement(
    private val composeRule: AndroidComposeTestRule<*, *>,
    private val parentSemanticMatcher: SemanticsMatcher? = null,
) {
    private val context
        get() = composeRule.activity

    public val lazyListMatcher: SemanticsMatcher
        get() = hasTestTag(CalendarTestTag.HEADER_CHIPS_LAZY_ROW)
    public val filterButtonMatcher: SemanticsMatcher
        get() = hasClickAction()
            .and(
                hasAnyChild(
                    hasContentDescription(
                        context.getString(uiDesignR.string.open_filter_menu_content_description),
                    ),
                ),
            )
    public val gridButtonMatcher: SemanticsMatcher
        get() = hasClickAction()
            .and(
                hasAnyChild(
                    hasContentDescription(
                        context.getString(uiDesignR.string.switch_display_mode_to_grid_content_description),
                    ).or(
                        hasContentDescription(
                            context.getString(uiDesignR.string.switch_display_mode_to_list_content_description),
                        ),
                    ),
                ),
            )

    public fun chipsLazyList(): SemanticsNodeInteraction = composeRule.onNode(
        lazyListMatcher.havingAchestor(parentSemanticMatcher),
    )

    public fun chipsButtons(): SemanticsNodeInteractionCollection = chipsLazyList()
        .onChildren()
        .filter(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox))

    public fun filterButton(): SemanticsNodeInteraction = composeRule.onNode(
        filterButtonMatcher.havingAchestor(parentSemanticMatcher),
    )

    public fun gridButton(useUnmergedTree: Boolean = false): SemanticsNodeInteraction = composeRule.onNode(
        gridButtonMatcher.havingAchestor(parentSemanticMatcher),
        useUnmergedTree = useUnmergedTree,
    )
}
