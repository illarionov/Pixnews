/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.CURRENT_QUARTER
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.CURRENT_YEAR
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.NEXT_MONTH
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.NEXT_QUARTER
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.NEXT_YEAR
import ru.pixnews.domain.model.UpcomingReleaseTimeCategory.TBD
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
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListPixnewsGameUi
import ru.pixnews.feature.calendar.model.CalendarListTitle
import ru.pixnews.foundation.ui.design.card.UpcomingReleaseDateUiModel
import ru.pixnews.foundation.ui.design.card.toUiModel
import ru.pixnews.library.kotlin.datetime.utils.hasDifferentDayFrom
import java.util.EnumMap

internal object UpcomingGameListConverter {
    fun convert(
        games: List<UpcomingRelease>,
        actualFields: Set<GameField>,
    ): ImmutableList<CalendarListItem> {
        require(GameField.ReleaseDate in actualFields)
        val grouped: Map<UpcomingReleaseTimeCategory, List<Game>> = games.groupByTo(
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
            releaseDate = releaseDate.toUiModel(),
        )
    }

    @Suppress("CyclomaticComplexMethod")
    private fun Date.toUpcomingReleaseGroupId(
        category: UpcomingReleaseTimeCategory,
    ): UpcomingReleaseDateUiModel {
        return when (category) {
            FEW_DAYS, CURRENT_MONTH -> if (this is HasYearMonthDay) {
                UpcomingReleaseDateUiModel.YearMonthDay(localDate)
            } else {
                toUiModel()
            }

            NEXT_MONTH -> if (this is HasYearMonth) {
                UpcomingReleaseDateUiModel.YearMonth(year, month)
            } else {
                toUiModel()
            }

            CURRENT_QUARTER, NEXT_QUARTER -> if (this is HasYearQuarter) {
                UpcomingReleaseDateUiModel.YearQuarter(year, quarter)
            } else {
                toUiModel()
            }

            CURRENT_YEAR, NEXT_YEAR -> if (this is HasYear) {
                UpcomingReleaseDateUiModel.Year(year)
            } else {
                toUiModel()
            }

            TBD -> UpcomingReleaseDateUiModel.Tbd
        }
    }
}
