/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.kotlin.datetime.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number

public val LocalDate.quarter: Int
    get() = month.quarter

@Suppress("MagicNumber")
public val Month.quarter: Int get() = (number + 2) / 3

@Suppress("MagicNumber")
public fun firstMonthOfQuarter(quarter: Int): Month = Month(3 * quarter - 2)

public fun LocalDate.hasDifferentDayFrom(date: LocalDate?): Boolean {
    if (date == null) {
        return true
    }

    return this.year != date.year ||
            this.dayOfYear != date.dayOfYear
}
