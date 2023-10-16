/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.Date.ExactDateTime
import ru.pixnews.domain.model.datetime.Date.Unknown
import ru.pixnews.domain.model.datetime.Date.Year
import ru.pixnews.domain.model.datetime.Date.YearMonth
import ru.pixnews.domain.model.datetime.Date.YearMonthDay
import ru.pixnews.domain.model.datetime.Date.YearQuarter
import ru.pixnews.domain.model.datetime.HasYear
import ru.pixnews.domain.model.datetime.HasYearMonth
import ru.pixnews.domain.model.datetime.HasYearMonthDay
import ru.pixnews.domain.model.datetime.HasYearQuarter
import ru.pixnews.domain.model.datetime.localDate
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.getObjectOrThrow
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.NEXT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.TBD
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleasesResponse
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListPixnewsGameUi
import ru.pixnews.feature.calendar.model.CalendarListTitle
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.library.kotlin.datetime.utils.hasDifferentDayFrom
import java.util.EnumMap

internal object UpcomingGameListConverter {
    fun convert(response: UpcomingReleasesResponse): ImmutableList<CalendarListItem> {
        require(GameField.ReleaseDate in response.requestedFields)
        val grouped: Map<UpcomingReleaseTimeCategory, List<Game>> = response.games.groupByTo(
            EnumMap(UpcomingReleaseTimeCategory::class.java),
            UpcomingRelease::group,
            UpcomingRelease::game,
        )

        return grouped
            .flatMap { (category, releases) ->
                when (category) {
                    FEW_DAYS, CURRENT_MONTH -> convertReleasesSplitByDays(category, releases)
                    else -> convertReleasesSingleGroup(category, releases)
                }
            }.toImmutableList()
    }

    private fun convertReleasesSplitByDays(
        category: UpcomingReleaseTimeCategory,
        releases: List<Game>,
    ): Sequence<CalendarListItem> {
        if (releases.isEmpty()) {
            return emptySequence()
        }

        val gamesSequence = releases.asSequence()

        return sequence {
            var currentGroupDate: LocalDate? = null

            gamesSequence
                .filter {
                    it.releaseDate is ExactDateTime || it.releaseDate is YearMonthDay
                }
                .forEach { game ->
                    val gameReleaseDate = game.releaseDate
                    val groupDate = (gameReleaseDate as HasYearMonthDay).localDate
                    if (groupDate.hasDifferentDayFrom(currentGroupDate)) {
                        currentGroupDate = groupDate
                        yield(CalendarListTitle(gameReleaseDate.toUpcomingReleaseGroupId(category)))
                    }
                    yield(game.toCalendarListItem())
                }

            val tbdGames = gamesSequence
                .filter {
                    !(it.releaseDate is ExactDateTime || it.releaseDate is YearMonthDay)
                }
                .groupBy { it.releaseDate::class }

            listOf(
                YearMonth::class,
                YearQuarter::class,
                Year::class,
                Unknown::class,
            ).forEach { group ->
                tbdGames[group]?.let { yieldAll(convertReleasesSingleGroup(category, it)) }
            }
        }
    }

    private fun convertReleasesSingleGroup(
        category: UpcomingReleaseTimeCategory,
        releases: List<Game>,
    ): Sequence<CalendarListItem> {
        if (releases.isEmpty()) {
            return emptySequence()
        }

        val headerGroupId = releases[0].releaseDate.toUpcomingReleaseGroupId(category)
        return sequenceOf(CalendarListTitle(headerGroupId)) +
                releases.asSequence().map { it.toCalendarListItem() }
    }

    fun Game.toCalendarListItem(): CalendarListPixnewsGameUi {
        return CalendarListPixnewsGameUi(
            gameId = id,
            title = name.value,
            description = summary.value.asPlainText(),
            cover = screenshots.firstOrNull(),
            platforms = platforms.map(Ref<GamePlatform>::getObjectOrThrow).toImmutableSet(),
            favourite = false,
            genres = genres.map(GameGenre::name).joinToString(),
        )
    }

    @Suppress("CyclomaticComplexMethod")
    private fun Date.toUpcomingReleaseGroupId(
        category: UpcomingReleaseTimeCategory,
    ): UpcomingReleaseGroupId {
        return when (category) {
            FEW_DAYS, CURRENT_MONTH -> if (this is HasYearMonthDay) {
                UpcomingReleaseGroupId.YearMonthDay(category, localDate)
            } else {
                toUpcomingReleaseDate(category)
            }

            NEXT_MONTH -> if (this is HasYearMonth) {
                UpcomingReleaseGroupId.YearMonth(category, year, month)
            } else {
                toUpcomingReleaseDate(category)
            }

            CURRENT_QUARTER, NEXT_QUARTER -> if (this is HasYearQuarter) {
                UpcomingReleaseGroupId.YearQuarter(category, year, quarter)
            } else {
                toUpcomingReleaseDate(category)
            }

            CURRENT_YEAR, NEXT_YEAR -> if (this is HasYear) {
                UpcomingReleaseGroupId.Year(category, year)
            } else {
                toUpcomingReleaseDate(category)
            }

            TBD -> UpcomingReleaseGroupId.Tbd(category)
        }
    }

    private fun Date.toUpcomingReleaseDate(
        category: UpcomingReleaseTimeCategory,
    ): UpcomingReleaseGroupId = when (this) {
        is ExactDateTime -> UpcomingReleaseGroupId.YearMonthDay(
            category,
            date.year,
            date.month.number,
            date.dayOfMonth,
        )
        is YearMonthDay -> UpcomingReleaseGroupId.YearMonthDay(category, date)
        is YearMonth -> UpcomingReleaseGroupId.YearMonth(category, date.year, date.monthNumber)
        is YearQuarter -> UpcomingReleaseGroupId.YearQuarter(category, year, quarter)
        is Year -> UpcomingReleaseGroupId.Year(category, year)
        is Unknown -> UpcomingReleaseGroupId.Tbd(category)
    }
}
