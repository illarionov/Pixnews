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
package ru.pixnews.feature.navigation

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import ru.pixnews.feature.root.BOTTOM_NAVIGATION_BAR_TEST_TAG
import ru.pixnews.feature.root.TopLevelDestination

internal class BottomBarElement(
    private val composeRule: AndroidComposeTestRule<*, *>,
) {
    private val context = composeRule.activity
    val root: SemanticsMatcher = hasTestTag(BOTTOM_NAVIGATION_BAR_TEST_TAG)
    private val buttons: Map<TopLevelDestination, BottomBarButton> = TopLevelDestination.values()
        .associateWith(::BottomBarButton)

    fun tab(destination: TopLevelDestination): BottomBarButton = buttons[destination]!!

    fun assertActiveTab(destination: TopLevelDestination) {
        buttons.values.forEach { tab ->
            if (tab.destination == destination) {
                tab.assertActive()
            } else {
                tab.assertNotActive()
            }
        }
    }

    inner class BottomBarButton(
        val destination: TopLevelDestination,
    ) {
        private val buttonText = context.getString(destination.title)
        private val buttonMatcher: SemanticsMatcher = hasText(buttonText).and(hasAnyAncestor(root))

        fun button(): SemanticsNodeInteraction = composeRule.onNode(buttonMatcher)
        fun assertActive() = button().assertIsSelected()
        fun assertNotActive() = button().assertIsNotSelected()
    }
}

internal fun BottomBarElement.button(destination: TopLevelDestination): SemanticsNodeInteraction =
    tab(destination).button()
