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
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.width
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.foundation.ui.design.card.element.PixnewsGameCardElement
import ru.pixnews.foundation.ui.imageloader.coil.test.FakeImageLoaderRule
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.instrumented.test.util.assertBottomPaddingInParentIsEqualTo
import ru.pixnews.library.instrumented.test.util.assertLeftPaddingInParentIsEqualTo
import ru.pixnews.library.instrumented.test.util.assertRightPaddingInParentIsEqualTo
import ru.pixnews.library.instrumented.test.util.assertVerticalPaddingBetweenAdjacentItems

class PixnewsGameCardTest {
    @get:Rule()
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule()
    val imageLoaderRule = FakeImageLoaderRule(composeTestRule::activity)
    private val card = PixnewsGameCardElement(composeTestRule, useUnmergedTree = true)
    private val game = object : PixnewsGameCardUiModel {
        override val gameId = GameFixtures.slimeRancher2.id
        override val title = GameFixtures.slimeRancher2.name.value
        override val description = GameFixtures.slimeRancher2.summary.value.asPlainText()
        override val cover = GameFixtures.slimeRancher2.screenshots.firstOrNull()
        override val platforms = GameFixtures.slimeRancher2.platforms
        override val favourite = true
        override val genres = GameFixtures.slimeRancher2.genres.map(GameGenre::name).joinToString()
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PixnewsTheme(useDynamicColor = false) {
                PixnewsGameCard(
                    game = game,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }
        }
    }

    @Test
    fun pixnewsGameCard_title_shouldHaveCorrectPaddingsAndSize() {
        val title = card.headline.title()
            .assertLeftPaddingInParentIsEqualTo(card.root(), 16.dp)
            .assertHeightIsEqualTo(
                with(composeTestRule.density) {
                    24.sp.toDp()
                },
            )

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between image and title",
            expectedPadding = 16.dp,
            topItem = card.image(),
            bottomItem = title,
        )
    }

    @Test
    fun pixnewsGameCard_favouriteButton_shouldHaveCorrectPaddingsAndSize() {
        val favouriteButtonBounds = card.favouriteButton().getUnclippedBoundsInRoot()

        card.favouriteButton().assertRightPaddingInParentIsEqualTo(card.root(), 0.dp)

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between image and favourite button",
            expectedPadding = 0.dp,
            topItem = card.image(),
            bottomItem = card.favouriteButton(),
        )

        favouriteButtonBounds.width.assertIsEqualTo(48.dp, "favourite button width")
        favouriteButtonBounds.height.assertIsEqualTo(48.dp, "favourite button height")

        card.favouriteIcon()
            .assertWidthIsEqualTo(24.dp)
            .assertHeightIsEqualTo(24.dp)
    }

    @Test
    fun pixnewsGameCard_genres_shouldHaveCorrectPaddingsAndSize() {
        val genres = card.headline.genres()

        genres.assertHeightIsEqualTo(16.dp)

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between genres and title",
            expectedPadding = 1.dp,
            topItem = card.headline.title(),
            bottomItem = genres,
        )
    }

    @Test
    fun pixnewsGameCard_platforms_shouldHaveCorrectPaddingsAndSize() {
        val platforms = card.headline.platforms()

        platforms.assertHeightIsEqualTo(24.dp)

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between genres and platforms",
            expectedPadding = 1.dp,
            topItem = card.headline.genres(),
            bottomItem = platforms,
        )
    }

    @Test
    fun pixnewsGameCard_description_shouldHaveCorrectPaddingsAndSize() {
        val description = card.description()

        description
            .assertLeftPaddingInParentIsEqualTo(card.root(), 16.dp)
            .assertRightPaddingInParentIsEqualTo(card.root(), 16.dp)
            .assertBottomPaddingInParentIsEqualTo(card.root(), 16.dp)

        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between headline and title",
            expectedPadding = 24.dp,
            topItem = card.headline.root(),
            bottomItem = description,
        )
    }
}
