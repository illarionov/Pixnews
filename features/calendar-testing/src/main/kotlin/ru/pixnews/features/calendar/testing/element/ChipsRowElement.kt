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
package ru.pixnews.features.calendar.testing.element

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
import ru.pixnews.features.calendar.testing.element.util.havingAchestor
import ru.pixnews.foundation.ui.design.R as uiDesignR

public class ChipsRowElement(
    private val composeRule: AndroidComposeTestRule<*, *>,
    private val parentSemanticMatcher: SemanticsMatcher? = null,
) {
    private val context
        get() = composeRule.activity

    public val lazyListMatcher: SemanticsMatcher
        get() = hasTestTag("calendar:header:chips_lazy_row")
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
