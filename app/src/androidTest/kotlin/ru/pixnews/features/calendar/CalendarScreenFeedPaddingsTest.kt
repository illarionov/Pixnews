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
package ru.pixnews.features.calendar

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import org.junit.Rule
import org.junit.Test
import ru.pixnews.MainActivity
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.beyondGoodEvil2
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.sims5
import ru.pixnews.features.calendar.element.GameFeedElement
import ru.pixnews.features.calendar.testing.element.CalendarHeaderElement
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.instrumented.testing.rule.InjectDependenciesRule
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import ru.pixnews.foundation.testing.util.assertVerticalPaddingBetweenAdjacentItems
import javax.inject.Inject

class CalendarScreenFeedPaddingsTest : BaseInstrumentedTest() {
    @get:Rule(order = 10)
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule(order = 20)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val calendarHeader = CalendarHeaderElement(composeTestRule)
    private val gameFeed = GameFeedElement(composeTestRule)

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var appConfig: AppConfig

    @Test
    fun calendarScreen_majorReleases_shouldHaveCorrectTopPadding() {
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between chips and major releases",
            expectedPadding = 16.dp,
            topItem = calendarHeader.chipsRow.chipsButtons().onFirst(),
            bottomItem = gameFeed.majorReleases.title(),
        )
    }

    @Test
    fun calendarScreen_majorReleases_to_card_shouldHaveCorrectTopPadding() {
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between title and major game releases carousel",
            expectedPadding = 8.dp,
            topItem = gameFeed.majorReleases.title(),
            bottomItem = gameFeed.majorReleases.gameCards()[1],
        )
    }

    @Test
    fun calendarScreen_majorReleasesCard_to_calendarTitle_shouldHaveCorrectPadding() {
        gameFeed.scrollToDateSubheader()
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between major releases card and first date subheader in feed",
            expectedPadding = 16.dp,
            topItem = gameFeed.majorReleases.gameCards()[1],
            bottomItem = gameFeed.dateSubheader(),
        )
    }

    @Test
    fun calendarScreen_adjacent_dateSubheader_gameCard_shouldHaveCorrectPadding() {
        gameFeed.scrollToDateSubheader()
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between date subheader and game card",
            expectedPadding = 8.dp,
            topItem = gameFeed.dateSubheader(),
            bottomItem = gameFeed.gameCard(),
        )
    }

    @Test
    fun calendarScreen_adjacent_gameCard_gameSubheader_shouldHaveCorrectPadding() {
        val gameId = GameFixtures.hytale.id
        val dateSubheader = "2 January 2024"

        with(gameFeed) {
            scrollToGameCard(gameId)
            scrollToDateSubheader(dateSubheader)

            assertVerticalPaddingBetweenAdjacentItems(
                subject = "padding between came card and next date subheader",
                expectedPadding = 24.dp,
                topItem = gameCard(gameId),
                bottomItem = dateSubheader(dateSubheader),
            )
        }
    }

    @Test
    fun calendarScreen_adjacent_gameCard_gameCard_shouldHaveCorrectPadding() {
        val game1Id = GameFixtures.sims5.id
        val game2Id = GameFixtures.beyondGoodEvil2.id
        with(gameFeed) {
            scrollToGameCard(game1Id)
            scrollToGameCard(game2Id)

            assertVerticalPaddingBetweenAdjacentItems(
                subject = "padding between came card and next game card",
                expectedPadding = 24.dp,
                topItem = gameCard(game1Id),
                bottomItem = gameCard(game2Id),
            )
        }
    }

    @Test
    fun calendarScreen_adjacent_dateSubheader_dateSubheader_shouldHaveCorrectPadding() {
        val dateSubheader1Title = "TBD February 2024"
        val dateSubheader2Title = "TBD March 2024"

        with(gameFeed) {
            scrollToDateSubheader(dateSubheader1Title)
            scrollToDateSubheader(dateSubheader2Title)

            assertVerticalPaddingBetweenAdjacentItems(
                subject = "padding between adjacent date headers",
                expectedPadding = 24.dp,
                topItem = dateSubheader(dateSubheader1Title),
                bottomItem = dateSubheader(dateSubheader2Title),
            )
        }
    }
}
