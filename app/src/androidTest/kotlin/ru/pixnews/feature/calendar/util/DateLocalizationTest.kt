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

import assertk.assertThat
import assertk.assertions.isIn
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Locale

private val localeRussian = Locale("ru", "RU")

@RunWith(Enclosed::class)
class DateLocalizationTest {
    @RunWith(Parameterized::class)
    class FormatYearMonthSelectionTest(
        private val test: YearMonthTestCase,
    ) {
        @Test
        fun formatYearMonthSelectionTitle_shouldFormatCorrectly() {
            val formatted = DateLocalization.formatYearMonthSelectionTitle(test.date, test.locale)
            assertThat(formatted).isIn(*test.expectedResult.toTypedArray())
        }

        class YearMonthTestCase(
            val date: LocalDate,
            val locale: Locale,
            val expectedResult: List<String>,
        ) {
            constructor(date: LocalDate, locale: Locale, expectedResult: String) : this(
                date,
                locale,
                listOf(expectedResult),
            )

            override fun toString(): String {
                return "$date in $locale is one of `$expectedResult`"
            }
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "{0}")
            fun data(): Collection<YearMonthTestCase> = listOf(
                YearMonthTestCase("2025-12-01".toLocalDate(), Locale.US, "December, 2025"),
                YearMonthTestCase("2025-12-01".toLocalDate(), localeRussian, "Декабрь, 2025"),
            )
        }
    }

    @RunWith(Parameterized::class)
    class GetShortWeekDaysTest(
        private val test: WeekdaysTestCase,
    ) {
        @Test
        fun getShortWeekDays_shouldReturnCorrectlyFormattedDays() {
            val weekDays = DateLocalization.getShortWeekDays(test.locale)
            assertThat(weekDays).isIn(*test.expectedDays.toTypedArray())
        }

        class WeekdaysTestCase(
            val locale: Locale,
            val expectedDays: List<Map<DayOfWeek, String>>,
        ) {
            override fun toString(): String {
                return "$locale days"
            }

            companion object {
                operator fun invoke(locale: Locale, variants: List<List<String>>): WeekdaysTestCase {
                    return WeekdaysTestCase(
                        locale,
                        variants.map { days ->
                            days.mapIndexed { index, dayName -> DayOfWeek.of(index + 1) to dayName }.toMap()
                        },
                    )
                }
            }
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "{0}")
            fun data(): Collection<WeekdaysTestCase> = listOf(
                WeekdaysTestCase(
                    Locale.US,
                    listOf(
                        listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"),
                        listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"),
                    ),
                ),
                WeekdaysTestCase(
                    localeRussian,
                    listOf(
                        listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"),
                    ),
                ),
            )
        }
    }
}
