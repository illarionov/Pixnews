/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.util

import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_MONTH
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_QUARTER
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.CURRENT_YEAR
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.feature.calendar.util.TestCase.Companion.testYear
import ru.pixnews.feature.calendar.util.TestCase.Companion.testYearMonth
import ru.pixnews.feature.calendar.util.TestCase.Companion.testYearMonthDay
import ru.pixnews.feature.calendar.util.TestCase.Companion.testYearQuarter
import ru.pixnews.library.instrumented.test.util.getLocalizedResources
import java.util.Locale
import java.util.Locale.ENGLISH

private val RUSSIAN = Locale("ru", "RU")

@RunWith(Enclosed::class)
class CalendarScreenSubheaderLocalizationTest {
    @RunWith(Parameterized::class)
    class CommonTests(
        private val test: TestCase,
    ) {
        @Test
        fun localizeShouldReturnCorrectResult() {
            val localizer = CalendarScreenSubheaderLocalization(
                locale = test.locale,
                resources = InstrumentationRegistry.getInstrumentation().targetContext
                    .getLocalizedResources(test.locale),
            )
            val result = localizer.localize(test.groupId)
            assertThat(result).isEqualTo(test.expectedResult)
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "{0}")
            fun data(): Collection<TestCase> = listOf(
                testYearMonthDay(2024, 2, 3, ENGLISH, "Saturday, February 3"),
                testYearMonthDay(2024, 7, 23, RUSSIAN, "Вторник, 23 июля"),
                testYearMonth(2024, 2, ENGLISH, "February 2024"),
                testYearMonth(2024, 7, RUSSIAN, "Июль 2024"),
                testYearQuarter(2024, 1, ENGLISH, "1st quarter 2024"),
                testYearQuarter(2024, 2, RUSSIAN, "2-й квартал 2024"),
                testYear(2024, ENGLISH, "2024 year"),
                testYear(2024, RUSSIAN, "2024 год"),
            )
        }
    }
}

class TestCase(
    val groupId: UpcomingReleaseGroupId,
    val locale: Locale,
    val expectedResult: String,
) {
    override fun toString(): String {
        return "$groupId in $locale is `$expectedResult`"
    }

    companion object {
        fun testYearMonthDay(
            year: Int,
            monthNumber: Int,
            dayOfMonth: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseGroupId.YearMonthDay(FEW_DAYS, year, monthNumber, dayOfMonth),
            locale = locale,
            expectedResult = expectedResult,
        )

        fun testYearMonth(
            year: Int,
            monthNumber: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseGroupId.YearMonth(CURRENT_MONTH, year, monthNumber),
            locale = locale,
            expectedResult = expectedResult,
        )

        fun testYearQuarter(
            year: Int,
            quarter: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseGroupId.YearQuarter(CURRENT_QUARTER, year, quarter),
            locale = locale,
            expectedResult = expectedResult,
        )

        fun testYear(
            year: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseGroupId.Year(CURRENT_YEAR, year),
            locale = locale,
            expectedResult = expectedResult,
        )
    }
}
