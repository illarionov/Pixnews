/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
