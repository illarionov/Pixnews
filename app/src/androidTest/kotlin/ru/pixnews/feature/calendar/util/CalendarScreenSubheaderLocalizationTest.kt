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
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory.FEW_DAYS
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.feature.calendar.util.TestCase.Companion.testYearMonthDay
import java.util.Locale
import java.util.Locale.ENGLISH

private val RUSSIAN = Locale("ru", "RU")

@RunWith(Enclosed::class)
class CalendarScreenSubheaderLocalizationTest {
    @RunWith(Parameterized::class)
    class YearMonthDayTests(
        private val test: TestCase,
    ) {
        @Test
        fun localizeShouldReturnCorrectResult() {
            val localizer = CalendarScreenSubheaderLocalization(
                locale = test.locale,
                resources = InstrumentationRegistry.getInstrumentation().targetContext.resources,
            )
            val result = localizer.localize(test.groupId)
            assertThat(result).isEqualTo(test.expectedResult)
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "{0}")
            fun data(): Collection<TestCase> = listOf(
                testYearMonthDay(FEW_DAYS, 2024, 2, 3, ENGLISH, "Saturday, February 3"),
                testYearMonthDay(FEW_DAYS, 2024, 7, 23, RUSSIAN, "Вторник, 23 июля"),
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
            category: UpcomingReleaseTimeCategory,
            year: Int,
            monthNumber: Int,
            dayOfMonth: Int,
            locale: Locale,
            expectedResult: String,
        ): TestCase = TestCase(
            groupId = UpcomingReleaseGroupId.YearMonthDay(category, year, monthNumber, dayOfMonth),
            locale = locale,
            expectedResult = expectedResult,
        )
    }
}
