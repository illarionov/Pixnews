/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.kotlin.datetime.utils

import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LocalDateExtTest {
    private val quarterToMonth = mapOf(
        1 to listOf(Month.JANUARY, Month.FEBRUARY, Month.MARCH),
        2 to listOf(Month.APRIL, Month.MAY, Month.JUNE),
        3 to listOf(Month.JULY, Month.AUGUST, Month.SEPTEMBER),
        4 to listOf(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER),
    )

    @Test
    fun `Month#getQuarter() should return correct result`() {
        quarterToMonth.forEach { (expectedQuarter, months) ->
            months.forEach { month ->
                month.quarter shouldBe expectedQuarter
            }
        }
    }

    @Test
    fun `firstMonthOfQuarter should return correct result`() {
        quarterToMonth.forEach { (quarter, months) ->
            firstMonthOfQuarter(quarter) shouldBe months[0]
        }
    }

    @ParameterizedTest
    @CsvSource(
        "2024-12-31, 2024-12-31, false",
        "2024-05-06, 2023-05-06, true",
        "2024-05-06, 2023-06-06, true",
    )
    fun `hasDifferentDayFrom should return correct result`(
        date1: String,
        date2: String,
        expected: Boolean,
    ) {
        val firstDate = LocalDate.parse(date1)
        val secondDate: LocalDate = LocalDate.parse(date2)

        val isAnotherDay = firstDate.hasDifferentDayFrom(secondDate)

        isAnotherDay shouldBe expected
    }
}
