/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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