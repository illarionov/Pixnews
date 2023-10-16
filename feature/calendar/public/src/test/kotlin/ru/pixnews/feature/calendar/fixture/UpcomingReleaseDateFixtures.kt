/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.fixture

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import ru.pixnews.domain.model.datetime.Date.Year
import ru.pixnews.domain.model.datetime.Date.YearMonth
import ru.pixnews.domain.model.datetime.Date.YearMonthDay
import ru.pixnews.domain.model.datetime.Date.YearQuarter
import java.time.Month.APRIL
import java.time.Month.AUGUST
import java.time.Month.JULY
import java.time.Month.JUNE
import java.time.Month.MARCH
import java.time.Month.MAY
import java.time.Month.OCTOBER

object UpcomingReleaseDateFixtures {
    val currentTimeZone = TimeZone.of("UTC+3")
    val currentDateTimestamp = LocalDateTime(2023, 4, 19, 23, 10, 0).toInstant(currentTimeZone)

    object LastMonth {
        val exactDate25 = YearMonthDay(2023, MARCH, 5)
        val approxDate = YearMonth(2023, MARCH)
    }

    object CurrentMonth {
        val exactDateStartOfWeek = YearMonthDay(2023, APRIL, 17)
        val exactDateYesterday = YearMonthDay(2023, APRIL, 18)
        val exactDateToday = YearMonthDay(2023, APRIL, 19)
        val exactDateTomorrow = YearMonthDay(2023, APRIL, 20)
        val exactDateLater25 = YearMonthDay(2023, APRIL, 25)
        val exactDateLater26 = YearMonthDay(2023, APRIL, 26)
        val approxDate = YearMonth(2023, APRIL)
    }

    object NextMonth {
        val exactDate10 = YearMonthDay(2023, MAY, 10)
        val exactDate15 = YearMonthDay(2023, MAY, 15)
        val approxDate = YearMonth(2023, MAY)
    }

    object CurrentQuarter {
        val exactDate = YearMonthDay(2023, JUNE, 11)
        val approxDateMonth = YearMonth(2023, JUNE)
        val approxDateQuarter = YearQuarter(2023, 2)
    }

    object NextQuarter {
        val exactDate = YearMonthDay(2023, JULY, 2)
        val approxDateMonth = YearMonth(2023, AUGUST)
        val approxDate3Quarter = YearQuarter(2023, 3)
    }

    object CurrentYear {
        val exactDate2Oct = YearMonthDay(2023, OCTOBER, 2)
        val approxDateOctober = YearMonth(2023, OCTOBER)
        val approxDate4Quarter = YearQuarter(2023, 4)
        val approxDateYear = Year(2023)
    }

    object NextYear {
        val exactDate1jun = YearMonthDay(2024, JUNE, 1)
        val approxDateApril = YearMonth(2024, APRIL)
        val approxDateQuarter = YearQuarter(2024, 1)
        val approxDateYear = Year(2024)
    }
}
