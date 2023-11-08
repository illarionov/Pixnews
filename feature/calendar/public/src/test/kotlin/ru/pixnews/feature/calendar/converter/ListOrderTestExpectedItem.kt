/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import kotlinx.datetime.LocalDate
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingRelease
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListPixnewsGameUi
import ru.pixnews.feature.calendar.model.CalendarListTitle
import ru.pixnews.foundation.ui.design.card.UpcomingReleaseDateUiModel
import ru.pixnews.foundation.ui.design.card.UpcomingReleaseDateUiModel.YearMonth
import ru.pixnews.foundation.ui.design.card.UpcomingReleaseDateUiModel.YearMonthDay
import ru.pixnews.library.kotlin.datetime.utils.quarter
import ru.pixnews.domain.model.datetime.Date.YearMonth as DomainYearMonth

internal sealed class ListOrderTestExpectedItem {
    data class ListItemTitle(
        val groupId: UpcomingReleaseDateUiModel,
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
            groupId: UpcomingReleaseDateUiModel,
        ) = add(ListItemTitle(groupId))

        internal fun MutableList<ListOrderTestExpectedItem>.titleYearMonthDay(
            date: Date.YearMonthDay,
        ) = add(ListItemTitle(YearMonthDay(date.date)))

        internal fun MutableList<ListOrderTestExpectedItem>.titleYearMonth(
            date: DomainYearMonth,
        ) = add(
            ListItemTitle(
                YearMonth(
                    date.date.year,
                    date.date.monthNumber,
                ),
            ),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.titleQuarter(
            date: LocalDate,
        ) = add(
            ListItemTitle(
                UpcomingReleaseDateUiModel.YearQuarter(date.year, date.quarter),
            ),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.titleYear(
            year: Int,
        ) = add(
            ListItemTitle(UpcomingReleaseDateUiModel.Year(year)),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.titleTbd() = add(
            ListItemTitle(UpcomingReleaseDateUiModel.Tbd),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.game(
            release: UpcomingRelease,
        ) = add(ListItemGame(release.game.id))
    }
}
