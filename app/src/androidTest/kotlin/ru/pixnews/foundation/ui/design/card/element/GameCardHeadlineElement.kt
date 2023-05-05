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
