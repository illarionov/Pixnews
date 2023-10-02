/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.kotlin.datetime.utils

import kotlinx.datetime.LocalDate

public val LocalDate.quarter: Int
    get() = monthNumberToQuarter(monthNumber)

@Suppress("MagicNumber")
public fun monthNumberToQuarter(monthNumber: Int): Int = (monthNumber + 2) / 3

public fun LocalDate.hasDifferentDayFrom(date: LocalDate?): Boolean {
    if (date == null) {
        return true
    }

    return this.year != date.year ||
            this.dayOfYear != date.dayOfYear
}
