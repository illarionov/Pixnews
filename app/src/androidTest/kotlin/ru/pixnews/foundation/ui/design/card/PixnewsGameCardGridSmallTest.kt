/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.design.card

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import kotlinx.collections.immutable.toImmutableSet
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.getObjectOrThrow
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.ui.design.card.element.PixnewsGameCardGridSmallElement
import ru.pixnews.foundation.ui.imageloader.coil.test.FakeImageLoaderRule
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.instrumented.test.util.assertVerticalPaddingBetweenAdjacentItems

class PixnewsGameCardGridSmallTest : BaseInstrumentedTest() {
    @get:Rule()
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule()
    val imageLoaderRule = FakeImageLoaderRule(composeTestRule::activity)
    private val card = PixnewsGameCardGridSmallElement(composeTestRule, useUnmergedTree = true)
    private val game = object : PixnewsGameCardGridSmallUiModel {
        override val gameId = GameFixtures.hytale.id
        override val title = GameFixtures.hytale.name.value
        override val cover = GameFixtures.hytale.screenshots.firstOrNull()
        override val platforms = GameFixtures.hytale.platforms
            .map(Ref<GamePlatform, *>::getObjectOrThrow)
            .toImmutableSet()
        override val favourite = false
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCardGridSmall(
                    game = game,
                    onClick = {},
                )
            }
        }
    }

    @Test
    fun pixnewsGameCardGridSmall_platforms_shouldHaveCorrectPaddings() {
        card.platforms().assertLeftPositionInRootIsEqualTo(8.dp)

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between image and platforms",
            expectedPadding = 6.dp,
            topItem = card.image(),
            bottomItem = card.platforms(),
        )

        card.platforms().assertHeightIsEqualTo(8.dp)

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between platforms and title",
            expectedPadding = 4.dp,
            topItem = card.platforms(),
            bottomItem = card.title(game.title),
        )
    }

    @Test
    fun pixnewsGameCardGridSmall_favouriteIcon_shouldHaveCorrectPaddings() {
        val favRight = card.favouriteIcon()
            .getUnclippedBoundsInRoot().right
        val cardWidth = card.root().getUnclippedBoundsInRoot().width

        (cardWidth - favRight).assertIsEqualTo(4.dp, "favourite icon end padding")

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between image and favicon",
            expectedPadding = 4.dp,
            topItem = card.platforms(),
            bottomItem = card.title(game.title),
        )
    }
}
