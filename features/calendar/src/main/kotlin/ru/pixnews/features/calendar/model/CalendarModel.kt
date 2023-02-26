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
package ru.pixnews.features.calendar.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.datetime.Clock.System
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.EnumMap

@Immutable
internal data class CalendarModel(
    val today: LocalDate,
    val firstDayOfWeek: DayOfWeek,
    val weekdayShortNames: EnumMap<DayOfWeek, String>,
)

@Suppress("CompositionLocalAllowlist")
internal val LocalCalendarModel = staticCompositionLocalOf {
    DEFAULT_CALENDAR_MODEL
}

internal val DEFAULT_CALENDAR_MODEL: CalendarModel = CalendarModel(
    today = System.todayIn(TimeZone.UTC),
    firstDayOfWeek = DayOfWeek.MONDAY,
    weekdayShortNames = EnumMap(
        mapOf(
            DayOfWeek.MONDAY to "Mon",
            DayOfWeek.TUESDAY to "Tue",
            DayOfWeek.WEDNESDAY to "Wed",
            DayOfWeek.THURSDAY to "Thu",
            DayOfWeek.FRIDAY to "Fri",
            DayOfWeek.SATURDAY to "Sat",
            DayOfWeek.SUNDAY to "Sun",
        ),
    ),
)
