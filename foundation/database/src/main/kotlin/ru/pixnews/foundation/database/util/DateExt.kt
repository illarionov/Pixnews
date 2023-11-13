/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.util

import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.DateTimeUnit.Companion.MONTH
import kotlinx.datetime.DateTimeUnit.Companion.QUARTER
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.HasYear
import ru.pixnews.domain.model.datetime.HasYearMonth
import ru.pixnews.domain.model.datetime.HasYearMonthDay
import ru.pixnews.domain.model.datetime.HasYearQuarter
import ru.pixnews.domain.model.datetime.localDate
import ru.pixnews.library.kotlin.datetime.utils.firstMonthOfQuarter
import kotlin.time.Duration.Companion.seconds

internal fun HasYearMonthDay.lastSecondOfDay(timeZone: TimeZone): Instant = localDate.lastSecondOfDay(timeZone)

internal fun HasYearMonth.lastSecondOfMonth(timeZone: TimeZone): Instant {
    return LocalDate(year, month, 1)
        .plus(1, MONTH)
        .atStartOfDayIn(timeZone) - 1.seconds
}

internal fun HasYearQuarter.lastSecondOfQuarter(timeZone: TimeZone): Instant {
    return LocalDate(
        year,
        firstMonthOfQuarter(quarter),
        1,
    )
        .plus(1, QUARTER)
        .atStartOfDayIn(timeZone) - 1.seconds
}

internal fun HasYear.lastSecondOfYear(timeZone: TimeZone): Instant {
    return LocalDate(year + 1, 1, 1)
        .atStartOfDayIn(timeZone) - 1.seconds
}

internal fun Date.Unknown.lastSecondOfPeriod(timeZone: TimeZone): Instant {
    return expected?.second?.lastSecondOfDay(timeZone) ?: Instant.DISTANT_FUTURE
}

private fun LocalDate.lastSecondOfDay(timeZone: TimeZone): Instant = this.plus(1, DAY)
    .atStartOfDayIn(timeZone) - 1.seconds
