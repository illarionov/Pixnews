/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.converter

import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.data.domain.upcoming.ObserveUpcomingReleasesByDateUseCase.UpcomingRelease
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListPixnewsGameUi
import ru.pixnews.feature.calendar.model.CalendarListTitle

internal sealed class ListOrderTestExpectedItem {
    data class ListItemTitle(
        val groupId: Date,
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
            groupId: Date,
        ) = add(ListItemTitle(groupId))

        internal fun MutableList<ListOrderTestExpectedItem>.titleYearMonthDay(
            date: Date.YearMonthDay,
        ) = title(date)

        internal fun MutableList<ListOrderTestExpectedItem>.titleYearMonth(
            date: Date.YearMonth,
        ) = title(date)

        internal fun MutableList<ListOrderTestExpectedItem>.titleQuarter(
            date: Date.YearQuarter,
        ) = title(date)

        internal fun MutableList<ListOrderTestExpectedItem>.titleYear(
            year: Int,
        ) = add(ListItemTitle(Date.Year(year)))

        internal fun MutableList<ListOrderTestExpectedItem>.titleTbd() = add(
            ListItemTitle(Date.Unknown()),
        )

        internal fun MutableList<ListOrderTestExpectedItem>.game(
            release: UpcomingRelease,
        ) = add(ListItemGame(release.game.id))
    }
}
