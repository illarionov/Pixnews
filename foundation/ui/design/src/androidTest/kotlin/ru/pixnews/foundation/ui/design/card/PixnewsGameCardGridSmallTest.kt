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
package ru.pixnews.foundation.ui.design.card

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import ru.pixnews.foundation.testing.util.assertVerticalPaddingBetweenAdjacentItems
import ru.pixnews.foundation.ui.design.card.element.PixnewsGameCardGridSmallElement
import ru.pixnews.foundation.ui.imageloader.coil.test.FakeImageLoaderRule
import ru.pixnews.foundation.ui.theme.PixnewsTheme

class PixnewsGameCardGridSmallTest : BaseInstrumentedTest() {
    @get:Rule()
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule()
    val imageLoaderRule = FakeImageLoaderRule(composeTestRule::activity)
    private val card = PixnewsGameCardGridSmallElement(composeTestRule, useUnmergedTree = true)
    private val game = GameFixtures.hytale.toGameCardGridMajorReleasesUiItem(favourite = false)

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