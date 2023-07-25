/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
import ru.pixnews.foundation.ui.design.test.constants.card.PixnewsGameCardGridSmallTestTags

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
