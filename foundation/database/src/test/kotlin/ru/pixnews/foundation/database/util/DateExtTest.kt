/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.util

import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.HasYearMonthDay
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
        fun `lastSecondOfDayTest() should return correct value`(testData: TestData) {
            val source = testData.date as HasYearMonthDay
            val lastSecond = source.lastSecondOfDay(testData.timeZone)
            lastSecond shouldBe testData.expected
        }

        fun lastSecondOfDayTestSource(): List<TestData> = listOf(
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

    // TODO: other tests
}
