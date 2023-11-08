/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.util

import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.pixnews.foundation.ui.design.card.UpcomingReleaseDateUiModel
import ru.pixnews.foundation.ui.design.util.TestCase.Companion.testYear
import ru.pixnews.foundation.ui.design.util.TestCase.Companion.testYearMonth
import ru.pixnews.foundation.ui.design.util.TestCase.Companion.testYearMonthDay
import ru.pixnews.foundation.ui.design.util.TestCase.Companion.testYearQuarter
import ru.pixnews.library.instrumented.test.util.getLocalizedResources
import java.util.Locale
import java.util.Locale.ENGLISH

private val RUSSIAN = Locale("ru", "RU")

@RunWith(Enclosed::class)
class UpcomingReleaseDateLocalizationTest {
    @RunWith(Parameterized::class)
    class CommonTests(
        private val test: TestCase,
    ) {
        @Test
        fun localizeShouldReturnCorrectResult() {
            val localizer = UpcomingReleaseDateLocalization(
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
    val groupId: UpcomingReleaseDateUiModel,
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
            groupId = UpcomingReleaseDateUiModel.YearMonthDay(year, monthNumber, dayOfMonth),
            locale = locale,
            expectedResult = expectedResult,
        )

        fun testYearMonth(
            year: Int,
            monthNumber: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseDateUiModel.YearMonth(year, monthNumber),
            locale = locale,
            expectedResult = expectedResult,
        )

        fun testYearQuarter(
            year: Int,
            quarter: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseDateUiModel.YearQuarter(year, quarter),
            locale = locale,
            expectedResult = expectedResult,
        )

        fun testYear(
            year: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseDateUiModel.Year(year),
            locale = locale,
            expectedResult = expectedResult,
        )
    }
}
