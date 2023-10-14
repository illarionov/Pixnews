/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.game.halfLife3
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.feature.calendar.converter.ListOrderTestExpectedItem.Companion.game
import ru.pixnews.feature.calendar.converter.ListOrderTestExpectedItem.Companion.titleQuarter
import ru.pixnews.feature.calendar.converter.ListOrderTestExpectedItem.Companion.titleTbd
import ru.pixnews.feature.calendar.converter.ListOrderTestExpectedItem.Companion.titleYear
import ru.pixnews.feature.calendar.converter.ListOrderTestExpectedItem.Companion.titleYearMonth
import ru.pixnews.feature.calendar.converter.ListOrderTestExpectedItem.Companion.titleYearMonthDay
import ru.pixnews.feature.calendar.converter.ListOrderTestExpectedItem.Companion.toListOrderTestExpectedItem
import ru.pixnews.feature.calendar.converter.UpcomingGameListConverter.toCalendarListItem
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleasesResponse
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth.exactDateLater25
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth.exactDateLater26
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth.exactDateStartOfWeek
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth.exactDateToday
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth.exactDateTomorrow
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentMonth.exactDateYesterday
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentQuarter
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.CurrentYear
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.NextMonth
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.NextQuarter
import ru.pixnews.feature.calendar.fixture.UpcomingReleaseDateFixtures.NextYear
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesInCurrentMonth
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesInNextFewDays
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesInNextMonth
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesInNextQuarter
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesInThisQuarter
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesNextYear
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesTbd
import ru.pixnews.feature.calendar.fixture.UpcomingReleasesFixtures.ReleasesThisYear
import ru.pixnews.feature.calendar.model.CALENDAR_LIST_ITEM_GAME_FIELDS
import ru.pixnews.feature.calendar.model.CalendarListPixnewsGameUi

internal class UpcomingGameListConverterTest {
    internal val converter = UpcomingGameListConverter

    @Test
    fun `convert() should convert empty list`() {
        val response = UpcomingReleasesResponse(
            requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
            requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
            games = persistentListOf(),
        )

        val result = converter.convert(response)

        result.shouldBeEmpty()
    }

    @Test
    fun `convert() should convert extended full list in correct order`() {
        val response = UpcomingReleasesResponse(
            requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
            requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
            games = persistentListOf(
                ReleasesTbd.tbdGame2,
                ReleasesNextYear.tbdNextYear,
                ReleasesThisYear.tbdThisYear,
                ReleasesInNextQuarter.nextQuarterExactDate,
                ReleasesInThisQuarter.thisQuarterExactDate,
                ReleasesInNextMonth.tbdNextMonth1,
                ReleasesInCurrentMonth.thisMonthDay25,
                ReleasesInNextFewDays.releasedToday,
            ),
        )

        val result = converter.convert(response)
            .map { it.toListOrderTestExpectedItem() }

        result shouldContainExactly buildList {
            // Today
            titleYearMonthDay(FEW_DAYS, exactDateToday)
            game(ReleasesInNextFewDays.releasedToday)
            // This month
            titleYearMonthDay(CURRENT_MONTH, exactDateLater25)
            game(ReleasesInCurrentMonth.thisMonthDay25)
            // Next Month
            titleYearMonth(NEXT_MONTH, NextMonth.approxDate)
            game(ReleasesInNextMonth.tbdNextMonth1)
            // This quarter
            titleQuarter(CURRENT_QUARTER, CurrentQuarter.exactDate.date)
            game(ReleasesInThisQuarter.thisQuarterExactDate)
            // Next quarter
            titleQuarter(NEXT_QUARTER, NextQuarter.exactDate.date)
            game(ReleasesInNextQuarter.nextQuarterExactDate)
            // This year
            titleYear(CURRENT_YEAR, CurrentYear.approxDateYear.year)
            game(ReleasesThisYear.tbdThisYear)
            // Next year
            titleYear(NEXT_YEAR, NextYear.approxDateYear.year)
            game(ReleasesNextYear.tbdNextYear)
            // TBD
            titleTbd()
            game(ReleasesTbd.tbdGame2)
        }
    }

    @Nested
    inner class GameToCalendarListItemConverterTests {
        @Test
        fun `Game#toCalendarListItem should convert games`() {
            val game = GameFixtures.halfLife3

            val result = game.toCalendarListItem()

            result shouldBe CalendarListPixnewsGameUi(
                gameId = GameId("half-life-3"),
                title = "Half-Life 3",
                description = "The long awaited final part of the Half-Life saga",
                cover = DefaultImageUrl(
                    rawUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/co1t0u.png",
                    size = CanvasSize(width = 264U, height = 352U),
                ),
                platforms = persistentSetOf(GamePlatform.Windows),
                favourite = false,
                genres = "Shooter",
            )
        }
    }

    @Nested
    inner class FewDaysGroupTests {
        @Test
        fun `Few days group should be in correct order`() {
            val nextFewDaysReleases = persistentListOf(
                ReleasesInNextFewDays.releasedAtStartOfWeek,
                ReleasesInNextFewDays.releasedYesterday,
                ReleasesInNextFewDays.releasedToday,
                ReleasesInNextFewDays.releasedToday2,
                ReleasesInNextFewDays.willBeReleasedTomorrow,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = nextFewDaysReleases,
            )

            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                // Start of week
                titleYearMonthDay(FEW_DAYS, exactDateStartOfWeek)
                game(ReleasesInNextFewDays.releasedAtStartOfWeek)
                // Yesterday
                titleYearMonthDay(FEW_DAYS, exactDateYesterday)
                game(ReleasesInNextFewDays.releasedYesterday)
                // Today
                titleYearMonthDay(FEW_DAYS, exactDateToday)
                game(ReleasesInNextFewDays.releasedToday)
                game(ReleasesInNextFewDays.releasedToday2)
                // Tomorrow
                titleYearMonthDay(FEW_DAYS, exactDateTomorrow)
                game(ReleasesInNextFewDays.willBeReleasedTomorrow)
            }
        }
    }

    @Nested
    inner class CurrentMonthGroupTests {
        @Test
        fun `current month group should be in correct order`() {
            val thisMonthReleases = persistentListOf(
                ReleasesInCurrentMonth.tbdThisMonth1,
                ReleasesInCurrentMonth.thisMonthDay25,
                ReleasesInCurrentMonth.tbdThisMonth2,
                ReleasesInCurrentMonth.thisMonthDay26,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = thisMonthReleases,
            )

            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                // Current month
                titleYearMonthDay(CURRENT_MONTH, exactDateLater25)
                game(ReleasesInCurrentMonth.thisMonthDay25)
                titleYearMonthDay(CURRENT_MONTH, exactDateLater26)
                game(ReleasesInCurrentMonth.thisMonthDay26)
                titleYearMonth(CURRENT_MONTH, CurrentMonth.approxDate)
                game(ReleasesInCurrentMonth.tbdThisMonth1)
                game(ReleasesInCurrentMonth.tbdThisMonth2)
            }
        }
    }

    @Nested
    inner class NextMonthGroupTests {
        @Test
        fun `next month group should be in correct order`() {
            val nextMonthReleases = persistentListOf(
                ReleasesInNextMonth.tbdNextMonth1,
                ReleasesInNextMonth.tbdNextMonth2,
                ReleasesInNextMonth.nextMonthDay10,
                ReleasesInNextMonth.nextMonthDay15,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = nextMonthReleases,
            )

            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                titleYearMonth(NEXT_MONTH, NextMonth.approxDate)
                game(ReleasesInNextMonth.tbdNextMonth1)
                game(ReleasesInNextMonth.tbdNextMonth2)
                game(ReleasesInNextMonth.nextMonthDay10)
                game(ReleasesInNextMonth.nextMonthDay15)
            }
        }
    }

    @Nested
    inner class ThisQuarterGroupTests {
        @Test
        fun `this quarter group should be in correct order`() {
            val thisQuarterReleases = persistentListOf(
                ReleasesInThisQuarter.thisQuarterExactDate,
                ReleasesInThisQuarter.tbdThisQuarterAug,
                ReleasesInThisQuarter.tbdThisQuarter,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = thisQuarterReleases,
            )
            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                titleQuarter(CURRENT_QUARTER, CurrentQuarter.exactDate.date)
                game(ReleasesInThisQuarter.thisQuarterExactDate)
                game(ReleasesInThisQuarter.tbdThisQuarterAug)
                game(ReleasesInThisQuarter.tbdThisQuarter)
            }
        }
    }

    @Nested
    inner class NextQuarterGroupTests {
        @Test
        fun `Next quarter group should be in correct order`() {
            val nextQuarterReleases = persistentListOf(
                ReleasesInNextQuarter.tbdNextQuarter,
                ReleasesInNextQuarter.tbdNextQuarterOct,
                ReleasesInNextQuarter.nextQuarterExactDate,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = nextQuarterReleases,
            )
            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                titleQuarter(NEXT_QUARTER, NextQuarter.exactDate.date)
                game(ReleasesInNextQuarter.tbdNextQuarter)
                game(ReleasesInNextQuarter.tbdNextQuarterOct)
                game(ReleasesInNextQuarter.nextQuarterExactDate)
            }
        }
    }

    @Nested
    inner class ThisYearGroupTests {
        @Test
        fun `This year group should be in correct order`() {
            val thisYearReleases = persistentListOf(
                ReleasesThisYear.thisYearExactDate,
                ReleasesThisYear.tbdThisYearOct,
                ReleasesThisYear.tbdThisYear4Quarter,
                ReleasesThisYear.tbdThisYear,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = thisYearReleases,
            )
            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                titleYear(CURRENT_YEAR, CurrentYear.exactDate2Oct.date.year)
                game(ReleasesThisYear.thisYearExactDate)
                game(ReleasesThisYear.tbdThisYearOct)
                game(ReleasesThisYear.tbdThisYear4Quarter)
                game(ReleasesThisYear.tbdThisYear)
            }
        }
    }

    @Nested
    inner class NextYearGroupTests {
        @Test
        fun `Next year group should be in correct order`() {
            val nextYearReleases = persistentListOf(
                ReleasesNextYear.nextYearExactDate,
                ReleasesNextYear.tbdNextYearApril,
                ReleasesNextYear.tbdNextYear1Quarter,
                ReleasesNextYear.tbdNextYear,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = nextYearReleases,
            )
            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                titleYear(NEXT_YEAR, NextYear.approxDateYear.year)
                game(ReleasesNextYear.nextYearExactDate)
                game(ReleasesNextYear.tbdNextYearApril)
                game(ReleasesNextYear.tbdNextYear1Quarter)
                game(ReleasesNextYear.tbdNextYear)
            }
        }
    }

    @Nested
    inner class TbdReleasesGroupTests {
        @Test
        fun `Tbd releases group group should be in correct order`() {
            val tbdReleases = persistentListOf(
                ReleasesTbd.tdbGame1,
                ReleasesTbd.tbdGame2,
            )
            val response = UpcomingReleasesResponse(
                requestTime = UpcomingReleaseDateFixtures.currentDateTimestamp,
                requestedFields = CALENDAR_LIST_ITEM_GAME_FIELDS,
                games = tbdReleases,
            )
            val result = converter.convert(response)
                .map { it.toListOrderTestExpectedItem() }

            result shouldContainExactly buildList {
                titleTbd()
                game(ReleasesTbd.tdbGame1)
                game(ReleasesTbd.tbdGame2)
            }
        }
    }
}
