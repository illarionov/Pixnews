/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.locale.LanguageCode.Companion.ENGLISH
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbReleaseDateFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.belowTheStoneTbdCategory6
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.gta21Oct1997
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.gtaCategory1YyyyMmmm
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.nightShiftCategory6Q4
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.portalStatus6
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.starWarsCategory7Tbd
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.thief2Category2Yyyy
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.xxComCategory3Q1
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.xxComCategory4Q2
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate.zooTycoonCategory5Q3
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMM
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ3
import java.time.Instant
import java.time.Month.JUNE
import java.time.Month.MARCH
import java.time.Month.OCTOBER
import ru.pixnews.igdbclient.model.ReleaseDate as IgdbReleaseDate

class IgdbReleaseDateConverterTest {
    @ParameterizedTest
    @MethodSource("toApproximateDateTestSource")
    fun `toApproximateDate should convert dates`(testData: Pair<IgdbReleaseDate, ApproximateDate>) {
        val result = testData.first.toApproximateDate()
        result shouldBeEqual testData.second
    }

    @Test
    fun `toApproximateDate should convert dates with minimum required fields`() {
        val date = IgdbReleaseDate(
            category = YYYYQ3,
            date = Instant.ofEpochSecond(16_9603_2000),
            y = 2023,
            m = 9,
        )

        val result = date.toApproximateDate()

        result shouldBeEqual ApproximateDate.ToBeDeterminedQuarter(
            year = 2023,
            quarter = 3,
            description = Localized.EMPTY_STRING,
        )
    }

    @Test
    fun `toApproximateDate should throw on no fields`() {
        listOf(
            IgdbReleaseDate(id = 1),
            IgdbReleaseDate(
                date = Instant.ofEpochSecond(16_9603_2000),
                category = YYYYMMMM,
                m = 1,
            ),
            IgdbReleaseDate(
                date = Instant.ofEpochSecond(16_9603_2000),
                category = YYYYMMMM,
                y = 2000,
            ),
        ).forEach {
            shouldThrow<IllegalArgumentException> { it.toApproximateDate() }
        }
    }

    internal companion object {
        @JvmStatic
        fun toApproximateDateTestSource(): List<Pair<IgdbReleaseDate, ApproximateDate>> = listOf(
            IgdbReleaseDateFixtures.gta21Oct1997 to ApproximateDate.YearMonthDay(1997, OCTOBER, 21),
            IgdbReleaseDateFixtures.gtaCategory1YyyyMmmm to ApproximateDate.YearMonth(2003, MARCH),
            IgdbReleaseDateFixtures.thief2Category2Yyyy to ApproximateDate.Year(2000),
            IgdbReleaseDateFixtures.xxComCategory3Q1 to ApproximateDate.ToBeDeterminedQuarter(
                1994, 1,
                Localized("Q1 1994", ENGLISH),
            ),
            IgdbReleaseDateFixtures.xxComCategory4Q2 to ApproximateDate.ToBeDeterminedQuarter(
                1994, 2,
                Localized("Q2 1994", ENGLISH),
            ),
            IgdbReleaseDateFixtures.zooTycoonCategory5Q3 to ApproximateDate.ToBeDeterminedQuarter(
                2001, 3,
                Localized("Q3 2001", ENGLISH),
            ),
            IgdbReleaseDateFixtures.nightShiftCategory6Q4 to ApproximateDate.ToBeDeterminedQuarter(
                1990, 4,
                Localized("Q4 1990", ENGLISH),
            ),
            IgdbReleaseDateFixtures.belowTheStoneTbdCategory6 to ApproximateDate.ToBeDeterminedQuarter(
                2023, 4,
                Localized("Q4 2023", ENGLISH),
            ),
            IgdbReleaseDateFixtures.starWarsCategory7Tbd to ApproximateDate.ToBeDetermined(
                expected = null,
                description = Localized(value = "TBD", ENGLISH),
            ),
            IgdbReleaseDateFixtures.portalStatus6 to ApproximateDate.YearMonthDay(2022, JUNE, 28),
        )
    }
}
