/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.util

import android.icu.text.DateFormatSymbols
import android.os.Build
import android.text.format.DateFormat
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toJavaLocalDate
import ru.pixnews.library.kotlin.utils.capitalize
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Date
import java.util.EnumMap
import java.util.Locale

public object DateLocalization {
    /**
     * A date format skeleton used to format a selected date to be used as content description for
     * screen readers (e.g. "Saturday, March 27, 2021")
     */
    private const val YEAR_MONTH_WEEKDAY_DAY_SKELETON: String = "yMMMMEEEEd"

    /**
     * A date format skeleton used to format a selected month for month selection widget
     */
    private const val YEAR_MONTH_SKELETON: String = "LLLL, yyyy"

    public fun formatCalendarDateForContentDescription(date: LocalDate, locale: Locale): String {
        val pattern = DateFormat.getBestDateTimePattern(locale, YEAR_MONTH_WEEKDAY_DAY_SKELETON)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern, locale)
            .withDecimalStyle(DecimalStyle.of(locale))
        return date
            .toJavaLocalDate()
            .format(formatter)
    }

    public fun formatYearMonthSelectionTitle(date: LocalDate, locale: Locale): String {
        return date.formatWithDateFormat(YEAR_MONTH_SKELETON, locale)
    }

    public fun getFirstDayOfWeek(locale: Locale): DayOfWeek {
        return WeekFields.of(locale).firstDayOfWeek
    }

    public fun getShortWeekDays(locale: Locale): Map<DayOfWeek, String> {
        val dayNames = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DateFormatSymbols.getInstance(locale).getWeekdays(DateFormatSymbols.STANDALONE, DateFormatSymbols.SHORT)
        } else {
            java.text.DateFormatSymbols.getInstance(locale).shortWeekdays
        }
        return DayOfWeek.values()
            .associateWithTo(EnumMap(DayOfWeek::class.java)) {
                val calendarDayOfWeek = when (it) {
                    DayOfWeek.SUNDAY -> Calendar.SUNDAY
                    else -> it.value + 1
                }
                dayNames[calendarDayOfWeek].capitalize(locale)
            }
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
