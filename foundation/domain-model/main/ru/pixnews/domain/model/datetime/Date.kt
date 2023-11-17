/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("WRONG_ORDER_IN_CLASS_LIKE_STRUCTURES")

package ru.pixnews.domain.model.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import ru.pixnews.domain.model.locale.Localized

private const val YEAR_MIN = 1958

public sealed class Date(
    public open val isToBeDetermined: Boolean,
) {
    public data class ExactDateTime(
        override val year: Int,
        override val month: Month,
        override val dayOfMonth: Int,
        val hour: Int = 0,
        val minute: Int = 0,
        val second: Int = 0,
        override val isToBeDetermined: Boolean = false,
    ) : Date(isToBeDetermined), HasYearMonthDay {
        public val date: LocalDateTime = LocalDateTime(year, month, dayOfMonth, hour, minute, second)

        init {
            require(year > YEAR_MIN)
        }

        public constructor(
            date: LocalDateTime,
            isToBeDetermined: Boolean = false,
        ) : this(date.year, date.month, date.dayOfMonth, date.hour, date.minute, date.second, isToBeDetermined)
    }

    public data class YearMonthDay(
        public val date: LocalDate,
        override val isToBeDetermined: Boolean = false,
    ) : Date(isToBeDetermined), HasYearMonthDay {
        override val year: Int get() = date.year
        override val month: Month get() = date.month
        override val dayOfMonth: Int get() = date.dayOfMonth

        init {
            require(date.year > YEAR_MIN)
        }

        public constructor(
            year: Int,
            month: Month,
            dayOfMonth: Int,
            isToBeDetermined: Boolean = false,
        ) : this(
            LocalDate(
                year = year,
                month = month,
                dayOfMonth = dayOfMonth,
            ),
            isToBeDetermined = isToBeDetermined,
        )
    }

    public data class YearMonth(
        override val year: Int,
        override val month: Month,
        override val isToBeDetermined: Boolean = true,
    ) : Date(isToBeDetermined), HasYearMonth {
        public val date: LocalDate = LocalDate(year, month, 1)

        init {
            require(year > YEAR_MIN)
        }

        public constructor(
            date: LocalDate,
            isToBeDetermined: Boolean = true,
        ) : this(date.year, date.month, isToBeDetermined)
    }

    public data class Year(
        override val year: Int,
        override val isToBeDetermined: Boolean = true,
        public val description: Localized<String> = Localized.EMPTY_STRING,
    ) : Date(isToBeDetermined), HasYear {
        init {
            require(year > YEAR_MIN)
        }
    }

    public data class YearQuarter(
        override val year: Int,
        override val quarter: Int,
        override val isToBeDetermined: Boolean = true,
        public val description: Localized<String> = Localized.EMPTY_STRING,
    ) : Date(isToBeDetermined), HasYearQuarter {
        init {
            require(year > YEAR_MIN)
            @Suppress("MagicNumber")
            require(quarter in 1..4)
        }
    }

    public data class Unknown(
        val expected: Pair<LocalDate, LocalDate?>? = null,
        public val description: Localized<String> = Localized.EMPTY_STRING,
        override val isToBeDetermined: Boolean = true,
    ) : Date(isToBeDetermined)
}
