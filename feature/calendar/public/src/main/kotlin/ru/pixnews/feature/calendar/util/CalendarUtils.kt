/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Suppress("MagicNumber")
internal fun getWeekDays(date: LocalDate, firstDayOfWeek: DayOfWeek): List<LocalDate> {
    val week: ArrayDeque<LocalDate> = ArrayDeque(7)
    var day = date
    while (day.dayOfWeek != firstDayOfWeek) {
        day = day.minus(DateTimeUnit.DAY)
        week.addFirst(day)
    }
    day = date
    do {
        week.addLast(day)
        day = day.plus(DateTimeUnit.DAY)
    } while (day.dayOfWeek != firstDayOfWeek)
    return week
}
