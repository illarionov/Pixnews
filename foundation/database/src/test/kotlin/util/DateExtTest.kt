/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("UnusedPrivateMember")

package ru.pixnews.foundation.database.util

import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.HasYear
import ru.pixnews.domain.model.datetime.HasYearMonth
import ru.pixnews.domain.model.datetime.HasYearMonthDay
import ru.pixnews.domain.model.datetime.HasYearQuarter
import ru.pixnews.library.kotlin.datetime.utils.fixtures.InstantFixtures.LeapSecondDec2016.lastNonLeapSecondOfDec2016
import ru.pixnews.library.kotlin.datetime.utils.fixtures.InstantFixtures.LeapSecondJun2015.lastNonLeapSecondOfJun2015

class DateExtTest {
    data class TestData(
        val date: Date,
        val timeZone: TimeZone = TimeZone.UTC,
        val expected: Instant,
    )

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class LastSecondOfDayTests {
        @ParameterizedTest
        @MethodSource("lastSecondOfDayTestSource")
        fun `lastSecondOfDay() should return correct value`(testData: TestData) {
            val source = testData.date as HasYearMonthDay
            val lastSecond = source.lastSecondOfDay(testData.timeZone)
            lastSecond shouldBe testData.expected
        }

        private fun lastSecondOfDayTestSource(): List<TestData> = listOf(
            TestData(
                date = Date.YearMonthDay(2024, Month.OCTOBER, 5),
                expected = Instant.parse("2024-10-05T23:59:59Z"),
            ),
            TestData(
                date = Date.YearMonthDay(2015, Month.JUNE, 30),
                expected = lastNonLeapSecondOfJun2015.instant,
            ),
            TestData(
                date = Date.YearMonthDay(2016, Month.DECEMBER, 31),
                expected = lastNonLeapSecondOfDec2016.instant,
            ),
            TestData(
                date = Date.YearMonthDay(2017, Month.JANUARY, 1),
                expected = Instant.parse("2017-01-01T23:59:59Z"),
            ),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class LastSecondOfMonthTests {
        @ParameterizedTest
        @MethodSource("lastSecondOfMonthTestSource")
        fun `lastSecondOfMonth() should return correct value`(testData: TestData) {
            val source = testData.date as HasYearMonth
            val lastSecond = source.lastSecondOfMonth(testData.timeZone)
            lastSecond shouldBe testData.expected
        }

        private fun lastSecondOfMonthTestSource(): List<TestData> = listOf(
            TestData(
                date = Date.YearMonthDay(2024, Month.OCTOBER, 5),
                expected = Instant.parse("2024-10-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearMonth(2017, Month.JANUARY),
                expected = Instant.parse("2017-01-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearMonthDay(2015, Month.JUNE, 10),
                expected = lastNonLeapSecondOfJun2015.instant,
            ),
            TestData(
                date = Date.YearMonthDay(2016, Month.DECEMBER, 31),
                expected = lastNonLeapSecondOfDec2016.instant,
            ),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class LastSecondOfQuarterTests {
        @ParameterizedTest
        @MethodSource("lastSecondOfQuarterTestSource")
        fun `lastSecondOfQuarter() should return correct value`(testData: TestData) {
            val source = testData.date as HasYearQuarter
            val lastSecond = source.lastSecondOfQuarter(testData.timeZone)
            lastSecond shouldBe testData.expected
        }

        private fun lastSecondOfQuarterTestSource(): List<TestData> = listOf(
            TestData(
                date = Date.YearMonthDay(2024, Month.OCTOBER, 5),
                expected = Instant.parse("2024-12-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearMonth(2017, Month.JANUARY),
                expected = Instant.parse("2017-03-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearQuarter(2017, 1),
                expected = Instant.parse("2017-03-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearQuarter(2015, 2),
                expected = lastNonLeapSecondOfJun2015.instant,
            ),
            TestData(
                date = Date.YearMonthDay(2016, Month.NOVEMBER, 6),
                expected = lastNonLeapSecondOfDec2016.instant,
            ),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class LastSecondOfYearTests {
        @ParameterizedTest
        @MethodSource("lastSecondOfYearTestSource")
        fun `lastSecondOfQuarter() should return correct value`(testData: TestData) {
            val source = testData.date as HasYear
            val lastSecond = source.lastSecondOfYear(testData.timeZone)
            lastSecond shouldBe testData.expected
        }

        fun lastSecondOfYearTestSource(): List<TestData> = listOf(
            TestData(
                date = Date.ExactDateTime(
                    LocalDateTime(2024, 4, 5, 10, 23, 55),
                ),
                expected = Instant.parse("2024-12-31T23:59:59Z"),
            ),
            TestData(
                date = Date.ExactDateTime(
                    LocalDateTime(2024, 12, 31, 23, 59, 59, 999_999_999),
                ),
                expected = Instant.parse("2024-12-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearMonthDay(2024, Month.OCTOBER, 5),
                expected = Instant.parse("2024-12-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearMonth(2017, Month.MAY),
                expected = Instant.parse("2017-12-31T23:59:59Z"),
            ),
            TestData(
                date = Date.YearQuarter(2015, 3),
                expected = Instant.parse("2015-12-31T23:59:59Z"),
            ),
            TestData(
                date = Date.Year(2016),
                expected = lastNonLeapSecondOfDec2016.instant,
            ),
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class LastSecondOfPeriodTests {
        @ParameterizedTest
        @MethodSource("lastSecondOfPeriodTestSource")
        fun `lastSecondOfQuarter() should return correct value`(testData: TestData) {
            val source = testData.date as Date.Unknown
            val lastSecond = source.lastSecondOfPeriod(testData.timeZone)
            lastSecond shouldBe testData.expected
        }

        fun lastSecondOfPeriodTestSource(): List<TestData> = listOf(
            TestData(
                date = Date.Unknown(),
                expected = Instant.DISTANT_FUTURE,
            ),
            TestData(
                date = Date.Unknown(expected = LocalDate(2024, Month.MAY, 5) to null),
                expected = Instant.DISTANT_FUTURE,
            ),
            TestData(
                date = Date.Unknown(
                    expected = LocalDate(2024, Month.JANUARY, 5) to LocalDate(2027, Month.MARCH, 23),
                ),
                expected = Instant.parse("2027-03-23T23:59:59Z"),
            ),
        )
    }
}
