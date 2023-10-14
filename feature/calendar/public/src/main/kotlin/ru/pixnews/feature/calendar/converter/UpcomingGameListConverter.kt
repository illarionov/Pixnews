/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.ApproximateDate.ExactDateTime
import ru.pixnews.domain.model.util.ApproximateDate.Quarter
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDetermined
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedQuarter
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.ApproximateDate.Unknown
import ru.pixnews.domain.model.util.ApproximateDate.Year
import ru.pixnews.domain.model.util.ApproximateDate.YearMonth
import ru.pixnews.domain.model.util.ApproximateDate.YearMonthDay
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
import ru.pixnews.feature.calendar.test.constants.asYear
import ru.pixnews.feature.calendar.test.constants.asYearMonth
import ru.pixnews.feature.calendar.test.constants.asYearQuarter
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
                    val groupDate = gameReleaseDate.getExactDateYearMothDay()
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
                Quarter::class,
                ToBeDeterminedQuarter::class,
                Year::class,
                ToBeDeterminedYear::class,
                ToBeDetermined::class,
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
    private fun ApproximateDate.toUpcomingReleaseGroupId(
        category: UpcomingReleaseTimeCategory,
    ): UpcomingReleaseGroupId {
        val groupIdByCategory = toGroupIdByReleaseDate(category)
        return when (category) {
            FEW_DAYS, CURRENT_MONTH -> groupIdByCategory
            NEXT_MONTH -> when (groupIdByCategory) {
                is UpcomingReleaseGroupId.YearMonthDay -> groupIdByCategory.asYearMonth
                else -> groupIdByCategory
            }
            CURRENT_QUARTER, NEXT_QUARTER -> when (groupIdByCategory) {
                is UpcomingReleaseGroupId.YearMonthDay -> groupIdByCategory.asYearQuarter
                is UpcomingReleaseGroupId.YearMonth -> groupIdByCategory.asYearQuarter
                else -> groupIdByCategory
            }
            CURRENT_YEAR, NEXT_YEAR -> when (groupIdByCategory) {
                is UpcomingReleaseGroupId.YearMonthDay -> groupIdByCategory.asYear
                is UpcomingReleaseGroupId.YearMonth -> groupIdByCategory.asYear
                is UpcomingReleaseGroupId.YearQuarter -> groupIdByCategory.asYear
                else -> groupIdByCategory
            }
            TBD -> UpcomingReleaseGroupId.Tbd(category)
        }
    }

    private fun ApproximateDate.toGroupIdByReleaseDate(
        category: UpcomingReleaseTimeCategory,
    ): UpcomingReleaseGroupId = when (this) {
        is ExactDateTime -> UpcomingReleaseGroupId.YearMonthDay(category, this.date.date)
        is YearMonthDay -> UpcomingReleaseGroupId.YearMonthDay(category, this.date)
        is YearMonth -> UpcomingReleaseGroupId.YearMonth(category, this.date.year, this.date.monthNumber)
        is Quarter -> UpcomingReleaseGroupId.YearQuarter(category, this.year, this.quarter)
        is ToBeDeterminedQuarter -> UpcomingReleaseGroupId.YearQuarter(category, this.year, this.quarter)
        is ToBeDeterminedYear -> UpcomingReleaseGroupId.Year(category, this.year)
        is Year -> UpcomingReleaseGroupId.Year(category, this.year)
        is ToBeDetermined -> UpcomingReleaseGroupId.Tbd(category)
        is Unknown -> UpcomingReleaseGroupId.Tbd(category)
    }

    private fun ApproximateDate.getExactDateYearMothDay(): LocalDate = when (this) {
        is ExactDateTime -> this.date.date
        is YearMonthDay -> this.date
        else -> error("Can not get exact date on `$this`")
    }
}
