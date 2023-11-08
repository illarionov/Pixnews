/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.util

import android.icu.text.DateFormatSymbols
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone.GMT_ZONE
import android.os.Build
import android.text.format.DateFormat
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toJavaLocalDate
import ru.pixnews.library.kotlin.utils.capitalize
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

    /**
     * Format a selected date to be used as content description for screen readers
     *
     * * **Example:** "Saturday, March 27, 2021"
     */
    public fun formatCalendarDateForContentDescription(date: LocalDate, locale: Locale): String {
        return date.formatWithBestDateTimePattern(locale, YEAR_MONTH_WEEKDAY_DAY_SKELETON)
    }

    /**
     * Format a selected month for month selection widget
     *
     * * **Example:** "December, 2025"
     * * **Example:** "Декабрь, 2025"
     */
    public fun formatYearMonthSelectionTitle(date: LocalDate, locale: Locale): String {
        return date.formatWithDateFormat(YEAR_MONTH_SKELETON, locale)
    }

    /**
     * Gets the first day-of-week.
     * The first day-of-week varies by culture. For example, the US uses Sunday,
     * while France and the ISO-8601 standard use Monday.
     */
    public fun getFirstDayOfWeek(locale: Locale): DayOfWeek {
        return WeekFields.of(locale).firstDayOfWeek
    }

    /**
     * Gets short names of the days of the week for use in the calendar widget.
     * The implication is that all the days can fit into single line on a small phone screen.
     *
     * Capitalized and without a dot at the end.
     *
     * * **Example:** Mo Tu We Th Fr Sa Su
     * * **Example:** Mon Tue Wed Thu Fri Sat Sun
     * * **Example:** Пн Вт Ср Чт Пт Сб Вс
     */
    public fun getShortWeekDays(locale: Locale): Map<DayOfWeek, String> {
        val dayNames = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DateFormatSymbols.getInstance(locale).getWeekdays(DateFormatSymbols.STANDALONE, DateFormatSymbols.SHORT)
        } else {
            java.text.DateFormatSymbols.getInstance(locale).shortWeekdays
        }
        return DayOfWeek.entries
            .associateWithTo(EnumMap(DayOfWeek::class.java)) {
                val calendarDayOfWeek = when (it) {
                    DayOfWeek.SUNDAY -> Calendar.SUNDAY
                    else -> it.value + 1
                }
                dayNames[calendarDayOfWeek].capitalize(locale)
            }
    }

    // Workaround for https://issuetracker.google.com/issues/160113376
    // Month in year (LLLL standalone form) is not supported by desugaring library.
    internal fun createLocalDateSystemFormatter(
        skeleton: String,
        locale: Locale,
    ): (LocalDate) -> String = object : (LocalDate) -> String {
        val formatter = SimpleDateFormat(skeleton, locale).apply {
            timeZone = GMT_ZONE
        }

        override fun invoke(date: LocalDate): String = formatter.format(
            Date(date.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()),
        ).capitalize(locale)
    }

    internal fun createBestDatePatternFormatter(
        skeleton: String,
        locale: Locale,
    ): (LocalDate) -> String = object : (LocalDate) -> String {
        val betterPattern = DateFormat.getBestDateTimePattern(locale, skeleton)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(betterPattern, locale)
            .withDecimalStyle(DecimalStyle.of(locale))

        override fun invoke(date: LocalDate): String = date.toJavaLocalDate().format(formatter)
    }

    private fun LocalDate.formatWithDateFormat(pattern: String, locale: Locale): String =
        createLocalDateSystemFormatter(pattern, locale).invoke(this)

    private fun LocalDate.formatWithBestDateTimePattern(locale: Locale, skeleton: String): String =
        createBestDatePatternFormatter(skeleton, locale).invoke(this)
}
