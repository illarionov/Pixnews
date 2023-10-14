/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.locale.Localized.Companion

private const val YEAR_MIN = 1958

public sealed class ApproximateDate {
    public data class Unknown(
        val description: Localized<String> = Localized.EMPTY_STRING,
    ) : ApproximateDate()

    public data class Year(val year: Int) : ApproximateDate() {
        init {
            require(year > YEAR_MIN)
        }
    }

    public data class YearMonth(val date: LocalDate) : ApproximateDate() {
        init {
            require(date.year > YEAR_MIN)
        }

        public constructor(year: Int, month: Month) : this(
            LocalDate(
                year = year,
                month = month,
                dayOfMonth = 1,
            ),
        )
    }

    public data class YearMonthDay(val date: LocalDate) : ApproximateDate() {
        init {
            require(date.year > YEAR_MIN)
        }

        public constructor(year: Int, month: Month, dayOfMonth: Int) : this(
            LocalDate(
                year = year,
                month = month,
                dayOfMonth = dayOfMonth,
            ),
        )
    }

    public data class Quarter(
        val year: Int,
        val quarter: Int,
    ) : ApproximateDate() {
        init {
            require(year > YEAR_MIN)
            @Suppress("MagicNumber")
            require(quarter in 1..4)
        }
    }

    public data class ExactDateTime(val date: LocalDateTime) : ApproximateDate()

    public data class ToBeDeterminedYear(
        val year: Int,
        val description: Localized<String>,
    ) : ApproximateDate() {
        init {
            require(year > YEAR_MIN)
        }
    }

    public data class ToBeDeterminedQuarter(
        val year: Int,
        val quarter: Int,
        val description: Localized<String>,
    ) : ApproximateDate() {
        init {
            @Suppress("MagicNumber")
            require(year > YEAR_MIN)
            @Suppress("MagicNumber")
            require(quarter in 1..4)
        }
    }

    public data class ToBeDetermined(
        val expected: Pair<Instant, Instant?>?,
        val description: Localized<String> = Companion.EMPTY_STRING,
    ) : ApproximateDate()
}
