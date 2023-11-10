/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.util

import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.text.format.DateFormat
import arrow.core.andThen
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toJavaLocalDate
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.Date.ExactDateTime
import ru.pixnews.domain.model.datetime.Date.Unknown
import ru.pixnews.domain.model.datetime.localDate
import ru.pixnews.foundation.ui.design.R
import ru.pixnews.foundation.ui.design.R.string
import ru.pixnews.library.kotlin.utils.capitalize
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.LazyThreadSafetyMode.NONE

public class UpcomingReleaseDateLocalization(
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
        createBestDateSystemFormatter(MONTH_WEEKDAY_DAY_SKELETON, locale)
    }

    /**
     * Format a selected month for release group subheader
     *
     * * **Example:** December 2025
     * * **Example:** Декабрь 2025
     */
    private val yearMonthFormatter: (LocalDate) -> String by lazy(NONE) {
        val formatter = createBestDateSystemFormatter(YEAR_MONTH_SKELETON, locale)
        formatter andThen ::cleanupYear
    }

    /**
     * Format a selected quarter
     *
     * * **Example:** 2nd quarter, 2025
     * * **Example:** 2 квартал, 2025
     */
    private val quarterFormatter by lazy(NONE) {
        val formatter = createBestDateSystemFormatter(QUARTER_YEAR_SKELETON, locale)
        formatter andThen ::cleanupYear
    }

    /**
     * Format a selected year release group subheader
     *
     * * **Example:** year 2025
     * * **Example:** 2025 год
     */
    private val yearFormatter by lazy(NONE) { YearFormatter(locale, resources) }

    @Suppress("MagicNumber")
    public fun localize(
        groupId: Date,
    ): String = when (groupId) {
        is ExactDateTime -> monthDayWeekDayFormatter(groupId.localDate)
        is Date.YearMonthDay -> monthDayWeekDayFormatter(groupId.localDate)
        is Date.YearMonth -> yearMonthFormatter(
            LocalDate(groupId.year, groupId.month, 1),
        )
        is Date.Year -> yearFormatter(
            LocalDate(groupId.year, 1, 1),
        )
        is Date.YearQuarter -> quarterFormatter(
            LocalDate(groupId.year, groupId.quarter * 3 - 2, 1),
        )
        is Unknown -> resources.getString(string.upcoming_release_date_tbd)
    }

    private fun cleanupYear(year: String): String = year
        .replace(CLEANUP_YEAR_REGEX, "")

    private fun createBestDateSystemFormatter(
        skeleton: String,
        locale: Locale,
    ): (LocalDate) -> String = object : (LocalDate) -> String {
        val betterPattern = DateFormat.getBestDateTimePattern(locale, skeleton)
        val formatter = SimpleDateFormat(betterPattern, locale).apply {
            timeZone = TimeZone.GMT_ZONE
        }

        override fun invoke(date: LocalDate): String = formatter.format(
            java.util.Date(date.atStartOfDayIn(kotlinx.datetime.TimeZone.UTC).toEpochMilliseconds()),
        ).capitalize(locale)
    }

    private class YearFormatter(
        locale: Locale,
        private val resources: Resources,
    ) : (LocalDate) -> String {
        private val yearDateTimeFormat = DateTimeFormatter.ofPattern(YEAR_SKELETON, locale)

        override fun invoke(date: LocalDate): String {
            val year = date.toJavaLocalDate().format(yearDateTimeFormat)
            return resources.getString(R.string.upcoming_release_date_year_x, year)
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
