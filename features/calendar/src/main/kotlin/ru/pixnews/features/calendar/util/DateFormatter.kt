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
package ru.pixnews.features.calendar.util

import android.text.format.DateFormat
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toJavaLocalDate
import ru.pixnews.features.calendar.model.DateSelectionHeaderDefaults.YEAR_MONTH_SKELETON
import ru.pixnews.features.calendar.model.DateSelectionHeaderDefaults.YEAR_MONTH_WEEKDAY_DAY_SKELETON
import ru.pixnews.libraries.kotlin.utils.capitalize
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.util.Date
import java.util.Locale

internal object DateFormatter {
    fun formatCalendarDateForContentDescription(date: LocalDate, locale: Locale): String {
        val pattern = DateFormat.getBestDateTimePattern(locale, YEAR_MONTH_WEEKDAY_DAY_SKELETON)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern, locale)
            .withDecimalStyle(DecimalStyle.of(locale))
        return date
            .toJavaLocalDate()
            .format(formatter)
    }

    fun formatYearMonthSelectionTitle(date: LocalDate, locale: Locale): String {
        return date.formatWithDateFormat(YEAR_MONTH_SKELETON, locale)
    }

    // Workaround for https://issuetracker.google.com/issues/160113376
    private fun LocalDate.formatWithDateFormat(pattern: String, locale: Locale): String {
        val formatter = SimpleDateFormat(pattern, locale).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }
        return formatter.format(Date(this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()))
            .capitalize(locale)
    }
}
