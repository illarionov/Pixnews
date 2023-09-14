/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
