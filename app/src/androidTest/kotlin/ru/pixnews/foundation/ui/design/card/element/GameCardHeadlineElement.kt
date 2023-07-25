/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.design.card.element

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import ru.pixnews.foundation.ui.design.test.constants.card.PixnewsGameCardTestTags

public class GameCardHeadlineElement(
    private val parent: (matcher: SemanticsMatcher) -> SemanticsNodeInteraction,
) {
    constructor (
        composeRule: AndroidComposeTestRule<*, *>,
        useUnmergedTree: Boolean = false,
    ) : this(
        parent = { composeRule.onNode(matcher = it, useUnmergedTree) },
    )

    fun root(): SemanticsNodeInteraction = parent(rootMatcher)

    fun title(): SemanticsNodeInteraction = root().onChildren().onFirst()

    fun genres(): SemanticsNodeInteraction = root().onChildren()[1]

    fun platforms(): SemanticsNodeInteraction = root().onChildren().filterToOne(
        hasTestTag(PixnewsGameCardTestTags.PLATFORMS),
    )

    companion object {
        val rootMatcher: SemanticsMatcher = hasTestTag(PixnewsGameCardTestTags.HEADLINE)
    }
}
