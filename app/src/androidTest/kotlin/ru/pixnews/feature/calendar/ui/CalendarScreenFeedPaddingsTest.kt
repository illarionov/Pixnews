/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import android.os.Build
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import org.junit.Assume
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import ru.pixnews.MainActivity
import ru.pixnews.anvil.codegen.test.inject.ContributesTest
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.CURRENT_QUARTER
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.beyondGoodEvil2
import ru.pixnews.domain.model.game.game.gta6
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.sims5
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase.UpcomingRelease
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.feature.calendar.test.constants.toGroupId
import ru.pixnews.feature.calendar.test.element.CalendarHeaderElement
import ru.pixnews.feature.calendar.test.element.GameFeedElement
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.foundation.instrumented.test.di.rule.InjectDependenciesRule
import ru.pixnews.library.instrumented.test.util.assertVerticalPaddingBetweenAdjacentItems
import ru.pixnews.test.assumption.UpcomingReleaseUseCaseAssumptions
import java.time.Month.AUGUST
import java.time.Month.MAY
import javax.inject.Inject

@ContributesTest
class CalendarScreenFeedPaddingsTest : BaseInstrumentedTest() {
    @get:Rule(order = 10)
    val injectDependencies = InjectDependenciesRule(this)

    @get:Rule(order = 20)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val calendarHeader = CalendarHeaderElement(composeTestRule)
    private val gameFeed = GameFeedElement(composeTestRule)

    @get:Rule
    val upcomingReleaseUseCaseAssumptions = UpcomingReleaseUseCaseAssumptions(autoInitialize = false)

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var appConfig: AppConfig

    @Test
    fun calendarScreen_majorReleases_shouldHaveCorrectTopPadding() {
        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseDefaultGame()
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between chips and major releases",
            expectedPadding = 16.dp,
            topItem = calendarHeader.chipsRow.chipsButtons().onFirst(),
            bottomItem = gameFeed.majorReleases.title(),
        )
    }

    @Test
    fun calendarScreen_majorReleases_to_card_shouldHaveCorrectTopPadding() {
        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseDefaultGame()
        assertVerticalPaddingBetweenAdjacentItems(
            subject = "padding between title and major game releases carousel",
            expectedPadding = 8.dp,
            topItem = gameFeed.majorReleases.title(),
            bottomItem = gameFeed.majorReleases.gameCards()[1],
        )
    }

    @Test
    fun calendarScreen_majorReleasesCard_to_calendarTitle_shouldHaveCorrectPadding() {
        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseDefaultGame()
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
        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseDefaultGame()
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
        val dateToday = Date.YearMonthDay(2023, MAY, 17)
        val dateTomorrow = Date.YearMonthDay(2023, MAY, 18)

        upcomingReleaseUseCaseAssumptions.assumeUpcomingReleasesSuccessfully(
            releases = listOf(
                UpcomingRelease(GameFixtures.hytale.copy(releaseDate = dateToday), FEW_DAYS),
                UpcomingRelease(GameFixtures.gta6.copy(releaseDate = dateTomorrow), FEW_DAYS),
            ),
        )
        val gameId = GameFixtures.hytale.id
        val dateSubheader = Date.YearMonthDay(dateTomorrow.date).toGroupId()

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
        Assume.assumeTrue("Too far scrolling on small screens", Build.VERSION.SDK_INT > 24)

        upcomingReleaseUseCaseAssumptions.assumeUpcomingReleasesSuccessfully(
            releases = listOf(
                UpcomingRelease(
                    GameFixtures.sims5.copy(
                        releaseDate = Date.YearMonthDay(2023, AUGUST, 11),
                    ),
                    CURRENT_QUARTER,
                ),
                UpcomingRelease(
                    GameFixtures.beyondGoodEvil2.copy(
                        releaseDate = Date.YearMonth(2023, AUGUST),
                    ),
                    CURRENT_QUARTER,
                ),
            ),
        )
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
    @Ignore("Adjacent date headers do not exist without games between them")
    fun calendarScreen_adjacent_dateSubheader_dateSubheader_shouldHaveCorrectPadding() {
        val dateSubheader1Title = UpcomingReleaseGroupId.YearMonth(2024, 2)
        val dateSubheader2Title = UpcomingReleaseGroupId.YearMonth(2024, 3)

        upcomingReleaseUseCaseAssumptions.assumeUpcomingGamesResponseDefaultGame()

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
