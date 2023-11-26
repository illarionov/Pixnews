/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.embedded

import androidx.room.Entity
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.Date.ExactDateTime
import ru.pixnews.domain.model.datetime.Date.Unknown
import ru.pixnews.domain.model.datetime.Date.Year
import ru.pixnews.domain.model.datetime.Date.YearMonth
import ru.pixnews.domain.model.datetime.Date.YearMonthDay
import ru.pixnews.domain.model.datetime.Date.YearQuarter
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Companion.TIMESTAMP_TIME_ZONE
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Type.EXACT_DATE_TIME
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Type.UNKNOWN
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Type.YEAR
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Type.YEAR_MONTH
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Type.YEAR_MONTH_DAY
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Type.YEAR_QUARTER
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.foundation.database.util.lastSecondOfDay
import ru.pixnews.foundation.database.util.lastSecondOfMonth
import ru.pixnews.foundation.database.util.lastSecondOfPeriod
import ru.pixnews.foundation.database.util.lastSecondOfQuarter
import ru.pixnews.foundation.database.util.lastSecondOfYear
import ru.pixnews.library.internationalization.language.LanguageCode
import ru.pixnews.library.kotlin.datetime.utils.quarter
import ru.pixnews.library.kotlin.datetime.utils.truncateToSeconds
import kotlin.LazyThreadSafetyMode.PUBLICATION

public fun GameReleaseDateEntity(
    date: Date,
): GameReleaseDateEntity = when (date) {
    is ExactDateTime -> GameReleaseDateEntity(
        type = EXACT_DATE_TIME,
        timestamp = date.date.toInstant(TIMESTAMP_TIME_ZONE).truncateToSeconds(),
        isToBeDetermined = date.isToBeDetermined,
    )

    is YearMonthDay -> GameReleaseDateEntity(
        type = YEAR_MONTH_DAY,
        timestamp = date.lastSecondOfDay(TIMESTAMP_TIME_ZONE),
        isToBeDetermined = date.isToBeDetermined,
    )

    is YearMonth -> GameReleaseDateEntity(
        type = YEAR_MONTH,
        timestamp = date.lastSecondOfMonth(TIMESTAMP_TIME_ZONE),
        isToBeDetermined = date.isToBeDetermined,
    )

    is YearQuarter -> GameReleaseDateEntity(
        type = YEAR_QUARTER,
        timestamp = date.lastSecondOfQuarter(TIMESTAMP_TIME_ZONE),
        isToBeDetermined = date.isToBeDetermined,
        description = date.description.value,
        descriptionLanguageCode = LanguageCodeWrapper(date.description.language),
    )

    is Year -> GameReleaseDateEntity(
        type = YEAR,
        timestamp = date.lastSecondOfYear(TIMESTAMP_TIME_ZONE),
        isToBeDetermined = date.isToBeDetermined,
        description = date.description.value,
        descriptionLanguageCode = LanguageCodeWrapper(date.description.language),
    )

    is Unknown -> GameReleaseDateEntity(
        type = UNKNOWN,
        timestamp = date.lastSecondOfPeriod(TIMESTAMP_TIME_ZONE),
        isToBeDetermined = date.isToBeDetermined,
        description = date.description.value,
        descriptionLanguageCode = LanguageCodeWrapper(date.description.language),
        expectedFrom = date.expected?.first?.toEpochDays(),
        expectedTo = date.expected?.second?.toEpochDays(),
    )
}

/**
 * Release date of an upcoming or already released game. Serialization of the [Date] data type in the database.
 *
 * #### Fields
 * * [type]: type of the [Date]
 * * [timestamp]: last second of the period specified by [type]
 * * [description]: description field of the [Date] (TODO: localization?)
 * * [expectedFrom]: First value of the [Date.Unknown.expected] in epoch days
 * * [expectedTo]: Second value of the [Date.Unknown.expected] in epoch days
 */
@Entity
public data class GameReleaseDateEntity(
    val type: Type,
    val timestamp: Instant,
    val isToBeDetermined: Boolean = false,
    val description: String = "",
    val descriptionLanguageCode: LanguageCodeWrapper? = null,
    val expectedFrom: Int? = null,
    val expectedTo: Int? = null,
) {
    val date: Date by lazy(PUBLICATION) {
        when (type) {
            EXACT_DATE_TIME -> ExactDateTime(
                date = timestampDateTime,
                isToBeDetermined = isToBeDetermined,
            )

            YEAR_MONTH_DAY -> YearMonthDay(
                date = timestampDateTime.date,
                isToBeDetermined = isToBeDetermined,
            )

            YEAR_MONTH -> YearMonth(
                date = timestampDateTime.date,
                isToBeDetermined = isToBeDetermined,
            )

            YEAR_QUARTER -> {
                val date = timestampDateTime.date
                YearQuarter(
                    year = date.year,
                    quarter = date.quarter,
                    isToBeDetermined = isToBeDetermined,
                    description = localizedDescription,
                )
            }

            YEAR -> Year(
                year = timestampDateTime.year,
                isToBeDetermined = isToBeDetermined,
                description = localizedDescription,
            )

            UNKNOWN -> Unknown(
                expected = expectedFrom?.let { from ->
                    LocalDate.fromEpochDays(from) to expectedTo?.let(LocalDate::fromEpochDays)
                },
                description = localizedDescription,
                isToBeDetermined = isToBeDetermined,
            )
        }
    }

    private val timestampDateTime: LocalDateTime
        get() = timestamp.toLocalDateTime(TIMESTAMP_TIME_ZONE)

    private val localizedDescription: Localized<String>
        get() = Localized(
            description,
            descriptionLanguageCode?.code ?: LanguageCode.ENGLISH,
        )

    @Suppress("MagicNumber")
    public enum class Type(internal val databaseId: Int) {
        EXACT_DATE_TIME(1),
        YEAR_MONTH_DAY(2),
        YEAR_MONTH(3),
        YEAR_QUARTER(4),
        YEAR(5),
        UNKNOWN(6),
    }

    public companion object {
        public val TIMESTAMP_TIME_ZONE: TimeZone = TimeZone.UTC
    }
}
