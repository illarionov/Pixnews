/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.util

import android.content.res.Resources
import arrow.core.andThen
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.jetbrains.annotations.VisibleForTesting
import ru.pixnews.feature.calendar.R
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.Tbd
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.Year
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearMonth
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearMonthDay
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId.YearQuarter
import ru.pixnews.feature.calendar.util.DateLocalization.createLocalDateBestDatePatternSystemFormatter
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.LazyThreadSafetyMode.NONE

@VisibleForTesting
public class CalendarScreenSubheaderLocalization(
    private val locale: Locale,
    private val resources: Resources,
) {
    /**
     * Format a selected day and month
     *
     * * **Example:** Saturday, March 27
     * * **Example:** Суббота, 27 марта
     */
    private val monthDayWeekDayFormatter by lazy(NONE) {
        createLocalDateBestDatePatternSystemFormatter(MONTH_WEEKDAY_DAY_SKELETON, locale)
    }

    /**
     * Format a selected month for release group subheader
     *
     * * **Example:** December 2025
     * * **Example:** Декабрь 2025
     */
    private val yearMonthFormatter: (LocalDate) -> String by lazy(NONE) {
        val formatter = createLocalDateBestDatePatternSystemFormatter(YEAR_MONTH_SKELETON, locale)
        formatter andThen ::cleanupYear
    }

    /**
     * Format a selected quarter
     *
     * * **Example:** 2nd quarter, 2025
     * * **Example:** 2 квартал, 2025
     */
    private val quarterFormatter by lazy(NONE) {
        val formatter = createLocalDateBestDatePatternSystemFormatter(QUARTER_YEAR_SKELETON, locale)
        formatter andThen ::cleanupYear
    }

    /**
     * Format a selected year release group subheader
     *
     * * **Example:** year 2025
     * * **Example:** 2025 год
     */
    private val yearFormatter by lazy(NONE) { YearFormatter(locale, resources) }

    private fun cleanupYear(year: String): String = year
        .replace(CLEANUP_YEAR_REGEX, "")

    @Suppress("MagicNumber")
    public fun localize(
        groupId: UpcomingReleaseGroupId,
    ): String = when (groupId) {
        is YearMonthDay -> monthDayWeekDayFormatter(
            LocalDate(groupId.year, groupId.monthNumber, groupId.dayOfMonth),
        )

        is YearMonth -> yearMonthFormatter(
            LocalDate(groupId.year, groupId.monthNumber, 1),
        )

        is YearQuarter -> quarterFormatter(
            LocalDate(groupId.year, groupId.quarter * 3 - 2, 1),
        )

        is Year -> yearFormatter(
            LocalDate(groupId.year, 1, 1),
        )

        is Tbd -> resources.getString(R.string.calendar_screen_feed_subheader_to_be_determined)
    }

    private class YearFormatter(
        locale: Locale,
        private val resources: Resources,
    ) : (LocalDate) -> String {
        private val yearDateTimeFormat = DateTimeFormatter.ofPattern(YEAR_SKELETON, locale)

        override fun invoke(date: LocalDate): String {
            val year = date.toJavaLocalDate().format(yearDateTimeFormat)
            return resources.getString(R.string.calendar_screen_feed_subheader_year_x, year)
        }
    }

    private companion object {
        /**
         * A date format skeleton used to format a selected date for release group subheader
         */
        private const val MONTH_WEEKDAY_DAY_SKELETON: String = "dMMMMEEEE"

        /**
         * A date format skeleton used to format a selected month for release group subheader
         */
        private const val YEAR_MONTH_SKELETON: String = "yyyy LLLL"

        /**
         * A date format skeleton used to format a selected quarter for release group subheader
         */
        private const val QUARTER_YEAR_SKELETON: String = "QQQQy"

        /**
         * A date format skeleton used to format a selected year for release group subheader
         */
        private const val YEAR_SKELETON: String = "y"
        private val CLEANUP_YEAR_REGEX = Regex("""\s+г\.$""")
}
}
