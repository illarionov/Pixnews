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
package ru.pixnews.feature.calendar

import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.feature.calendar.util.getWeekDays
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.SUNDAY

class CalendarUtilsTest {
    @ParameterizedTest
    @MethodSource("testWeekDaysMethodSourceMonday")
    fun `getWeekDays should return correct list on firstDayOfWeek=MONDAY`(day: LocalDate) {
        val days = getWeekDays(day, MONDAY)
        days shouldBe expectedWeekMonday
    }

    @ParameterizedTest
    @MethodSource("testWeekDaysMethodSourceSunday")
    fun `getWeekDays should return correct list on firstDayOfWeek=SUNDAY`(day: LocalDate) {
        val days = getWeekDays(day, SUNDAY)
        days shouldBe expectedWeekSunday
    }

    companion object {
        val expectedWeekMonday = listOf(
            LocalDate(2023, 2, 27),
            LocalDate(2023, 2, 28),
            LocalDate(2023, 3, 1),
            LocalDate(2023, 3, 2),
            LocalDate(2023, 3, 3),
            LocalDate(2023, 3, 4),
            LocalDate(2023, 3, 5),
        )
        val expectedWeekSunday = listOf(
            LocalDate(2023, 2, 26),
            LocalDate(2023, 2, 27),
            LocalDate(2023, 2, 28),
            LocalDate(2023, 3, 1),
            LocalDate(2023, 3, 2),
            LocalDate(2023, 3, 3),
            LocalDate(2023, 3, 4),
        )

        @JvmStatic
        fun testWeekDaysMethodSourceMonday(): List<LocalDate> = expectedWeekMonday

        @JvmStatic
        fun testWeekDaysMethodSourceSunday(): List<LocalDate> = expectedWeekSunday
    }
}
