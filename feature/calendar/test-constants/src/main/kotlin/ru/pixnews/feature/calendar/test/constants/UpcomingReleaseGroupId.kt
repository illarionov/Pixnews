/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("WRONG_ORDER_IN_CLASS_LIKE_STRUCTURES")

package ru.pixnews.feature.calendar.test.constants

import android.os.Parcelable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number
import kotlinx.parcelize.Parcelize
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.Date.ExactDateTime
import ru.pixnews.domain.model.datetime.Date.Unknown
import ru.pixnews.domain.model.datetime.Date.Year
import ru.pixnews.domain.model.datetime.Date.YearMonth
import ru.pixnews.domain.model.datetime.Date.YearMonthDay
import ru.pixnews.domain.model.datetime.Date.YearQuarter

@Parcelize
public sealed class UpcomingReleaseGroupId : Parcelable {
    public data class YearMonthDay(
        val year: Int,
        val monthNumber: Int,
        val dayOfMonth: Int,
    ) : UpcomingReleaseGroupId() {
        public constructor(
            date: LocalDate,
        ) : this(date.year, date.monthNumber, date.dayOfMonth)
    }

    public data class YearMonth(
        val year: Int,
        val monthNumber: Int,
    ) : UpcomingReleaseGroupId() {
        public constructor(
            year: Int,
            month: Month,
        ) : this(year, month.number)
    }

    public data class YearQuarter(
        val year: Int,
        val quarter: Int,
    ) : UpcomingReleaseGroupId()

    public data class Year(
        val year: Int,
    ) : UpcomingReleaseGroupId()

    public data object Tbd : UpcomingReleaseGroupId()
}

public fun Date.toGroupId(): UpcomingReleaseGroupId = when (this) {
    is ExactDateTime -> UpcomingReleaseGroupId.YearMonthDay(date.year, date.month.number, date.dayOfMonth)
    is YearMonthDay -> UpcomingReleaseGroupId.YearMonthDay(date)
    is YearMonth -> UpcomingReleaseGroupId.YearMonth(date.year, date.monthNumber)
    is YearQuarter -> UpcomingReleaseGroupId.YearQuarter(year, quarter)
    is Year -> UpcomingReleaseGroupId.Year(year)
    is Unknown -> UpcomingReleaseGroupId.Tbd
}
