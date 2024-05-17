/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.embedded

import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity.Type
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.library.internationalization.language.LanguageCode

class GameReleaseDateEntityTest {
    @ParameterizedTest
    @CsvSource(
        "2024-12-31T23:59:59Z | true  | '' |     |   |   | 2024-12-31T23:59:59",
        "2010-11-01T05:42:00Z | false | '' | eng |   |   | 2010-11-01T05:42",
        delimiter = '|',
    )
    fun `date of EXACT_DATE_TIME entity should return correct Date`(
        timestampString: String,
        isToBeDetermined: Boolean,
        description: String,
        descriptionLanguageCode: String?,
        from: Int?,
        to: Int?,
        expectedTimestamp: String,
    ) {
        val entity = GameReleaseDateEntity(
            type = Type.EXACT_DATE_TIME,
            timestamp = Instant.parse(timestampString),
            isToBeDetermined = isToBeDetermined,
            description = description,
            descriptionLanguageCode = descriptionLanguageCode?.let {
                LanguageCodeWrapper(LanguageCode.from(it))
            },
            expectedFrom = from,
            expectedTo = to,
        )

        val date = entity.date

        date shouldBe Date.ExactDateTime(
            LocalDateTime.parse(expectedTimestamp),
            isToBeDetermined = isToBeDetermined,
        )
    }

    @ParameterizedTest
    @CsvSource(
        "2024-12-31T23:59:59Z | true  | '' |     |   |   | 2024-12-31",
        "2010-05-05T23:59:59Z | false | '' | eng |   |   | 2010-05-05",
        delimiter = '|',
    )
    fun `date of YEAR_MONTH_DAY entity should return correct Date`(
        timestampString: String,
        isToBeDetermined: Boolean,
        description: String,
        descriptionLanguageCode: String?,
        from: Int?,
        to: Int?,
        expectedTimestamp: String,
    ) {
        val entity = GameReleaseDateEntity(
            type = Type.YEAR_MONTH_DAY,
            timestamp = Instant.parse(timestampString),
            isToBeDetermined = isToBeDetermined,
            description = description,
            descriptionLanguageCode = descriptionLanguageCode?.let {
                LanguageCodeWrapper(LanguageCode.from(it))
            },
            expectedFrom = from,
            expectedTo = to,
        )

        val date = entity.date

        date shouldBe Date.YearMonthDay(
            LocalDate.parse(expectedTimestamp),
            isToBeDetermined = isToBeDetermined,
        )
    }

    @ParameterizedTest
    @CsvSource(
        "2024-12-31T23:59:59Z | true  | '' |     |   |   | 2024 | DECEMBER",
        "2010-05-31T23:59:59Z | false | '' | eng |   |   | 2010 | MAY",
        delimiter = '|',
    )
    fun `date of YEAR_MONTH entity should return correct Date`(
        timestampString: String,
        isToBeDetermined: Boolean,
        description: String,
        descriptionLanguageCode: String?,
        from: Int?,
        to: Int?,
        expectedYear: Int,
        expectedMonth: Month,
    ) {
        val entity = GameReleaseDateEntity(
            type = Type.YEAR_MONTH,
            timestamp = Instant.parse(timestampString),
            isToBeDetermined = isToBeDetermined,
            description = description,
            descriptionLanguageCode = descriptionLanguageCode?.let {
                LanguageCodeWrapper(LanguageCode.from(it))
            },
            expectedFrom = from,
            expectedTo = to,
        )

        val date = entity.date

        date shouldBe Date.YearMonth(expectedYear, expectedMonth, isToBeDetermined)
    }

    @ParameterizedTest
    @CsvSource(
        "2024-12-31T23:59:59Z | true  | ''               |     |   |   | 2024 | 4",
        "2010-05-31T23:59:59Z | false | Test Description | eng |   |   | 2010 | 2",
        "2010-07-31T23:59:59Z | false | Test Description | jpn |   |   | 2010 | 3",
        delimiter = '|',
    )
    fun `date of YEAR_QUATER entity should return correct Date`(
        timestampString: String,
        isToBeDetermined: Boolean,
        description: String,
        descriptionLanguageCode: String?,
        from: Int?,
        to: Int?,
        expectedYear: Int,
        expectedQuarter: Int,
    ) {
        val entity = GameReleaseDateEntity(
            type = Type.YEAR_QUARTER,
            timestamp = Instant.parse(timestampString),
            isToBeDetermined = isToBeDetermined,
            description = description,
            descriptionLanguageCode = descriptionLanguageCode?.let {
                LanguageCodeWrapper(LanguageCode.from(it))
            },
            expectedFrom = from,
            expectedTo = to,
        )

        val date = entity.date

        date shouldBe Date.YearQuarter(
            expectedYear,
            expectedQuarter,
            isToBeDetermined,
            descriptionLanguageCode?.let {
                Localized(description, LanguageCode.from(it))
            } ?: Localized.EMPTY_STRING,
        )
    }

    @ParameterizedTest
    @CsvSource(
        "2024-12-31T23:59:59Z | true  | ''               |     |   |   | 2024",
        "2010-12-31T23:59:59Z | false | Test Description | eng |   |   | 2010",
        "2009-12-31T23:59:59Z | false | Test Description | jpn |   |   | 2009",
        delimiter = '|',
    )
    fun `date of YEAR entity should return correct Date`(
        timestampString: String,
        isToBeDetermined: Boolean,
        description: String,
        descriptionLanguageCode: String?,
        from: Int?,
        to: Int?,
        expectedYear: Int,
    ) {
        val entity = GameReleaseDateEntity(
            type = Type.YEAR,
            timestamp = Instant.parse(timestampString),
            isToBeDetermined = isToBeDetermined,
            description = description,
            descriptionLanguageCode = descriptionLanguageCode?.let {
                LanguageCodeWrapper(LanguageCode.from(it))
            },
            expectedFrom = from,
            expectedTo = to,
        )

        val date = entity.date

        date shouldBe Date.Year(
            expectedYear,
            isToBeDetermined,
            descriptionLanguageCode?.let {
                Localized(description, LanguageCode.from(it))
            } ?: Localized.EMPTY_STRING,
        )
    }

    @ParameterizedTest
    @CsvSource(
        "true   | ''               | eng |         |        ",
        "false  | Test Description | jpn |         |        ",
        "true   | ''               |     | 14770   |        ",
        "false  | ''               |     | 14770   | 20068  ",
        delimiter = '|',
    )
    fun `date of UNKNOWN entity should return correct Date`(
        isToBeDetermined: Boolean,
        description: String,
        descriptionLanguageCode: String?,
        from: Int?,
        to: Int?,
    ) {
        val entity = GameReleaseDateEntity(
            type = Type.UNKNOWN,
            timestamp = Instant.DISTANT_FUTURE,
            isToBeDetermined = isToBeDetermined,
            description = description,
            descriptionLanguageCode = descriptionLanguageCode?.let {
                LanguageCodeWrapper(LanguageCode.from(it))
            },
            expectedFrom = from,
            expectedTo = to,
        )

        val date = entity.date

        date shouldBe Date.Unknown(
            expected = if (from != null) {
                LocalDate.fromEpochDays(from) to to?.let(LocalDate::fromEpochDays)
            } else {
                null
            },
            isToBeDetermined = isToBeDetermined,
            description = descriptionLanguageCode?.let {
                Localized(description, LanguageCode.from(it))
            } ?: Localized.EMPTY_STRING,
        )
    }

    @Nested
    internal inner class GameReleaseDateEntityFactoryTests {
        @ParameterizedTest
        @CsvSource(
            "2024-12-31T23:59:59.999 | true  | 2024-12-31T23:59:59Z",
            "2010-01-01T05:40:31     | false | 2010-01-01T05:40:31Z",
            delimiter = '|',
        )
        fun `GameReleaseDateEntity from ExactDateTime should return create correct entity`(
            date: String,
            isToBeDetermined: Boolean,
            expectedInstant: String,
        ) {
            val exactDateTime = Date.ExactDateTime(
                LocalDateTime.parse(date),
                isToBeDetermined = isToBeDetermined,
            )

            val dateEntity = GameReleaseDateEntity(exactDateTime)

            dateEntity shouldBe GameReleaseDateEntity(
                type = Type.EXACT_DATE_TIME,
                timestamp = Instant.parse(expectedInstant),
                isToBeDetermined = isToBeDetermined,
            )
        }

        @ParameterizedTest
        @CsvSource(
            "2024 | DECEMBER | 31 | true   | 2024-12-31T23:59:59Z",
            "2010 | MAY      |  5 | false  | 2010-05-05T23:59:59Z",
            delimiter = '|',
        )
        fun `GameReleaseDateEntity from YearMonthDay should return correct value`(
            year: Int,
            month: Month,
            dayOfMonth: Int,
            isToBeDetermined: Boolean,
            expectedInstant: String,
        ) {
            val yearMonthDay = Date.YearMonthDay(year, month, dayOfMonth, isToBeDetermined)

            val dateEntity = GameReleaseDateEntity(yearMonthDay)

            dateEntity shouldBe GameReleaseDateEntity(
                type = Type.YEAR_MONTH_DAY,
                timestamp = Instant.parse(expectedInstant),
                isToBeDetermined = isToBeDetermined,
            )
        }

        @ParameterizedTest
        @CsvSource(
            "2024 | DECEMBER | true   | 2024-12-31T23:59:59Z",
            "2010 | MAY      | false  | 2010-05-31T23:59:59Z",
            delimiter = '|',
        )
        fun `GameReleaseDateEntity from YearMonth should return correct value`(
            year: Int,
            month: Month,
            isToBeDetermined: Boolean,
            expectedInstant: String,
        ) {
            val yearMonth = Date.YearMonth(year, month, isToBeDetermined)

            val dateEntity = GameReleaseDateEntity(yearMonth)

            dateEntity shouldBe GameReleaseDateEntity(
                type = Type.YEAR_MONTH,
                timestamp = Instant.parse(expectedInstant),
                isToBeDetermined = isToBeDetermined,
            )
        }

        @ParameterizedTest
        @CsvSource(
            "2024 | 1 | eng | ''                 | true  | 2024-03-31T23:59:59Z",
            "2010 | 2 | jpn | Test Description   | false | 2010-06-30T23:59:59Z",
            delimiter = '|',
        )
        fun `GameReleaseDateEntity from YearQuarter should return correct value`(
            year: Int,
            quarter: Int,
            languageCodeIdentifier: String,
            description: String,
            isToBeDetermined: Boolean,
            expectedInstant: String,
        ) {
            val languageCode = LanguageCode.from(languageCodeIdentifier)
            val yearQuarter = Date.YearQuarter(
                year = year,
                quarter = quarter,
                isToBeDetermined = isToBeDetermined,
                description = Localized(description, languageCode),
            )

            val dateEntity = GameReleaseDateEntity(yearQuarter)

            dateEntity shouldBe GameReleaseDateEntity(
                type = Type.YEAR_QUARTER,
                timestamp = Instant.parse(expectedInstant),
                isToBeDetermined = isToBeDetermined,
                descriptionLanguageCode = LanguageCodeWrapper(languageCode),
                description = description,
            )
        }

        @ParameterizedTest
        @CsvSource(
            "2024 | eng | ''                 | true  | 2024-12-31T23:59:59Z",
            "2010 | jpn | Test Description   | false | 2010-12-31T23:59:59Z",
            delimiter = '|',
        )
        fun `GameReleaseDateEntity from Year should return correct value`(
            year: Int,
            languageCodeIdentifier: String,
            description: String,
            isToBeDetermined: Boolean,
            expectedInstant: String,
        ) {
            val languageCode = LanguageCode.from(languageCodeIdentifier)
            val yearMonth = Date.Year(
                year = year,
                isToBeDetermined = isToBeDetermined,
                description = Localized(description, languageCode),
            )

            val dateEntity = GameReleaseDateEntity(yearMonth)

            dateEntity shouldBe GameReleaseDateEntity(
                type = Type.YEAR,
                timestamp = Instant.parse(expectedInstant),
                isToBeDetermined = isToBeDetermined,
                descriptionLanguageCode = LanguageCodeWrapper(languageCode),
                description = description,
            )
        }

        @ParameterizedTest
        @CsvSource(
            "            |            | eng | ''                 | true  | ",
            "            |            | jpn | Test Description   | false | ",
            " 2010-05-06 |            | jpn | Test Description   | false | ",
            " 2010-06-10 | 2024-12-11 | jpn | Test Description   | false | 2024-12-11T23:59:59Z",
            delimiter = '|',
        )
        fun `GameReleaseDateEntity from Unknown date should return correct value`(
            expectedFromDate: String?,
            expectedToDate: String?,
            languageCodeIdentifier: String,
            description: String,
            isToBeDetermined: Boolean,
            expectedInstant: String?,
        ) {
            val languageCode = LanguageCode.from(languageCodeIdentifier)
            val expectedFromLocalDate = expectedFromDate?.let(LocalDate::parse)
            val expectedToLocalDate = expectedToDate?.let(LocalDate::parse)
            val yearMonth = Date.Unknown(
                expected = expectedFromLocalDate?.let { it to expectedToLocalDate },
                isToBeDetermined = isToBeDetermined,
                description = Localized(description, languageCode),
            )

            val dateEntity = GameReleaseDateEntity(yearMonth)

            dateEntity shouldBe GameReleaseDateEntity(
                type = Type.UNKNOWN,
                timestamp = expectedInstant?.let(Instant::parse) ?: Instant.DISTANT_FUTURE,
                isToBeDetermined = isToBeDetermined,
                descriptionLanguageCode = LanguageCodeWrapper(languageCode),
                description = description,
                expectedFrom = expectedFromLocalDate?.toEpochDays(),
                expectedTo = if (expectedFromLocalDate != null) expectedToLocalDate?.toEpochDays() else null,
            )
        }
    }
}
