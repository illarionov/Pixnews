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
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChildren
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.foundation.ui.design.GameIdKey
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardGridSmallTestTags

class PixnewsGameCardGridSmallElement(
    private val parent: (matcher: SemanticsMatcher) -> SemanticsNodeInteraction,
    private val gameId: GameId? = null,
) {
    val rootGameMatcher: SemanticsMatcher = if (gameId != null) {
        SemanticsMatcher.expectValue(GameIdKey, gameId.toString())
    } else {
        SemanticsMatcher.keyIsDefined(GameIdKey)
    }
    constructor (
        composeRule: AndroidComposeTestRule<*, *>,
        useUnmergedTree: Boolean = false,
        gameId: GameId? = null,
    ) : this(
        gameId = gameId,
        parent = { composeRule.onNode(matcher = it, useUnmergedTree) },
    )

    fun root(): SemanticsNodeInteraction = parent(rootGameMatcher)

    fun image(): SemanticsNodeInteraction = root().onChildren()
        .filterToOne(hasTestTag(PixnewsGameCardGridSmallTestTags.IMAGE))

    fun favouriteIcon(): SemanticsNodeInteraction = root().onChildren()
        .filterToOne(hasTestTag(PixnewsGameCardGridSmallTestTags.FAVOURITE_ICON))

    fun platforms(): SemanticsNodeInteraction = root().onChildren()
        .filterToOne(hasTestTag(PixnewsGameCardGridSmallTestTags.PLATFORMS_ROW))

    fun title(title: String): SemanticsNodeInteraction = root().onChildren()
        .filterToOne(hasText(title))
}
