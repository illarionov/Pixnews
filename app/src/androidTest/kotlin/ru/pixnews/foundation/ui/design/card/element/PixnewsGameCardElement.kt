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
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.foundation.ui.design.GameIdKey
import ru.pixnews.foundation.ui.design.test.constants.card.PixnewsGameCardTestTags

class PixnewsGameCardElement(
    private val parent: (matcher: SemanticsMatcher) -> SemanticsNodeInteraction,
    private val gameId: GameId? = null,
) {
    val headline = GameCardHeadlineElement(parent)
    constructor (
        composeRule: AndroidComposeTestRule<*, *>,
        useUnmergedTree: Boolean = false,
        gameId: GameId? = null,
    ) : this(
        gameId = gameId,
        parent = { composeRule.onNode(matcher = it, useUnmergedTree) },
    )

    fun root(): SemanticsNodeInteraction = parent(rootGameMatcher(gameId))

    fun image(): SemanticsNodeInteraction = root().onChildren().filterToOne(imageMatcher)

    fun favouriteButton(): SemanticsNodeInteraction = root().onChildren().filterToOne(favouriteButtonMatcher)

    fun favouriteIcon(): SemanticsNodeInteraction = favouriteButton().onChild()

    fun description(): SemanticsNodeInteraction = root().onChildren()
        .filterToOne(hasTestTag(PixnewsGameCardTestTags.DESCRIPTION))

    companion object {
        val imageMatcher: SemanticsMatcher = hasTestTag(PixnewsGameCardTestTags.IMAGE)
        val favouriteButtonMatcher: SemanticsMatcher = hasTestTag(PixnewsGameCardTestTags.FAVOURITE_BUTTON)
        fun rootGameMatcher(gameId: GameId?): SemanticsMatcher = if (gameId != null) {
            SemanticsMatcher.expectValue(GameIdKey, gameId.toString())
        } else {
            SemanticsMatcher.keyIsDefined(GameIdKey)
        }
}
}
