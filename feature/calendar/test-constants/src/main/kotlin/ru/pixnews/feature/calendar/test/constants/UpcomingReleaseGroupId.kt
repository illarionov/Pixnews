/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.test.constants

import android.os.Parcelable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number
import kotlinx.parcelize.Parcelize
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.TBD
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.Year
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearMonth
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearMonthDay
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearQuarter
import ru.pixnews.library.kotlin.datetime.utils.monthNumberToQuarter

@Parcelize
public sealed class UpcomingReleaseGroupId(
    public open val category: UpcomingReleaseTimeCategory,
) : Parcelable {
    public data class YearMonthDay(
        override val category: UpcomingReleaseTimeCategory,
        val year: Int,
        val monthNumber: Int,
        val dayOfMonth: Int,
    ) : UpcomingReleaseGroupId(category) {
        public constructor(
            category: UpcomingReleaseTimeCategory,
            date: LocalDate,
        ) : this(category, date.year, date.monthNumber, date.dayOfMonth)
    }

    public data class YearMonth(
        override val category: UpcomingReleaseTimeCategory,
        val year: Int,
        val monthNumber: Int,
    ) : UpcomingReleaseGroupId(category) {
        public constructor(
            category: UpcomingReleaseTimeCategory,
            year: Int,
            month: Month,
        ) : this(category, year, month.number)
    }

    public data class YearQuarter(
        override val category: UpcomingReleaseTimeCategory,
        val year: Int,
        val quarter: Int,
    ) : UpcomingReleaseGroupId(category)

    public data class Year(
        override val category: UpcomingReleaseTimeCategory,
        val year: Int,
    ) : UpcomingReleaseGroupId(category)

    public data class Tbd(
        override val category: UpcomingReleaseTimeCategory = TBD,
    ) : UpcomingReleaseGroupId(category)
}

public val YearMonthDay.localDate: LocalDate
    get() = LocalDate(year, monthNumber, dayOfMonth)

public val YearMonthDay.quarter: Int
    get() = monthNumberToQuarter(this.monthNumber)

public val YearMonth.quarter: Int
    get() = monthNumberToQuarter(this.monthNumber)

public val YearMonthDay.asYearMonth: YearMonth
    get() = YearMonth(category, year, monthNumber)

public val YearMonthDay.asYearQuarter: YearQuarter
    get() = YearQuarter(category, year, quarter)

public val YearMonthDay.asYear: Year
    get() = Year(category, year)

public val YearMonth.asYearQuarter: YearQuarter
    get() = YearQuarter(category, year, quarter)

public val YearMonth.asYear: Year
    get() = Year(category, year)

public val YearQuarter.asYear: Year
    get() = Year(category, year)
