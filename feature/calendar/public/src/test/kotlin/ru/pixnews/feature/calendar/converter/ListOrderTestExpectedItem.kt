/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.TBD
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListPixnewsGameUi
import ru.pixnews.feature.calendar.model.CalendarListTitle
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearMonth
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearMonthDay
import ru.pixnews.library.kotlin.datetime.utils.quarter
import ru.pixnews.domain.model.datetime.Date.YearMonth as ApYearMonth

internal sealed class ListOrderTestExpectedItem {
    data class ListItemTitle(
        val groupId: UpcomingReleaseGroupId,
    ) : ListOrderTestExpectedItem() {
        override fun toString(): String = "[Title $groupId]"
    }

    data class ListItemGame(
        val gameId: GameId,
    ) : ListOrderTestExpectedItem() {
        override fun toString(): String = gameId.toString()
    }

    internal companion object {
        internal fun CalendarListItem.toListOrderTestExpectedItem(): ListOrderTestExpectedItem = when (this) {
            is CalendarListTitle -> ListItemTitle(groupId)
            is CalendarListPixnewsGameUi -> ListItemGame(gameId)
        }

        internal fun MutableList<ListOrderTestExpectedItem>.title(
            groupId: UpcomingReleaseGroupId,
        ) = add(ListItemTitle(groupId))

        internal fun MutableList<ListOrderTestExpectedItem>.titleYearMonthDay(
            category: UpcomingReleaseTimeCategory,
            date: Date.YearMonthDay,
        ) = add(ListItemTitle(YearMonthDay(category, date.date)))

        internal fun MutableList<ListOrderTestExpectedItem>.titleYearMonth(
            category: UpcomingReleaseTimeCategory,
            date: ApYearMonth,
        ) = add(
            ListItemTitle(
                YearMonth(
                    category,
                    date.date.year,
                    date.date.monthNumber,
                ),
            ),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.titleQuarter(
            category: UpcomingReleaseTimeCategory,
            date: LocalDate,
        ) = add(
            ListItemTitle(
                UpcomingReleaseGroupId.YearQuarter(category, date.year, date.quarter),
            ),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.titleYear(
            category: UpcomingReleaseTimeCategory,
            year: Int,
        ) = add(
            ListItemTitle(
                UpcomingReleaseGroupId.Year(
                    category,
                    year,
                ),
            ),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.titleTbd(
            category: UpcomingReleaseTimeCategory = TBD,
        ) = add(
            ListItemTitle(UpcomingReleaseGroupId.Tbd(category)),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.game(
            release: UpcomingRelease,
        ) = add(ListItemGame(release.game.id))
    }
}
