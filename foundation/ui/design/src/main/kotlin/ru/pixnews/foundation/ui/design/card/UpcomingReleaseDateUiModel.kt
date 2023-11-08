/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("WRONG_ORDER_IN_CLASS_LIKE_STRUCTURES")

package ru.pixnews.foundation.ui.design.card

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
public sealed class UpcomingReleaseDateUiModel : Parcelable {
    public data class YearMonthDay(
        val year: Int,
        val monthNumber: Int,
        val dayOfMonth: Int,
    ) : UpcomingReleaseDateUiModel() {
        public constructor(
            date: LocalDate,
        ) : this(date.year, date.monthNumber, date.dayOfMonth)
    }

    public data class YearMonth(
        val year: Int,
        val monthNumber: Int,
    ) : UpcomingReleaseDateUiModel() {
        public constructor(
            year: Int,
            month: Month,
        ) : this(year, month.number)
    }

    public data class YearQuarter(
        val year: Int,
        val quarter: Int,
    ) : UpcomingReleaseDateUiModel()

    public data class Year(
        val year: Int,
    ) : UpcomingReleaseDateUiModel()

    public data object Tbd : UpcomingReleaseDateUiModel()
}

public fun Date.toUiModel(): UpcomingReleaseDateUiModel = when (this) {
    is ExactDateTime -> UpcomingReleaseDateUiModel.YearMonthDay(date.year, date.month.number, date.dayOfMonth)
    is YearMonthDay -> UpcomingReleaseDateUiModel.YearMonthDay(date)
    is YearMonth -> UpcomingReleaseDateUiModel.YearMonth(date.year, date.monthNumber)
    is YearQuarter -> UpcomingReleaseDateUiModel.YearQuarter(year, quarter)
    is Year -> UpcomingReleaseDateUiModel.Year(year)
    is Unknown -> UpcomingReleaseDateUiModel.Tbd
}
