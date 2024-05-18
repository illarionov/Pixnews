/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.datetime.Date.Unknown
import ru.pixnews.domain.model.datetime.Date.Year
import ru.pixnews.domain.model.datetime.Date.YearMonth
import ru.pixnews.domain.model.datetime.Date.YearMonthDay
import ru.pixnews.domain.model.datetime.Date.YearQuarter
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameReleaseDateConverter.convert
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
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.ReleaseDate
import ru.pixnews.library.internationalization.language.LanguageCode
import java.time.Instant
import java.time.Month.JUNE
import java.time.Month.MARCH
import java.time.Month.OCTOBER

class IgdbGameReleaseDateConverterTest {
    @ParameterizedTest
    @MethodSource("toApproximateDateTestSource")
    fun `should convert dates`(testData: Pair<ReleaseDate, Date>) {
        val game = Game(release_dates = listOf(testData.first))
        val result = convert(game)
        result shouldBeEqual testData.second
    }

    @Test
    fun `should convert dates with minimum required fields`() {
        val date = ReleaseDate(
            category = YYYYQ3,
            date = Instant.ofEpochSecond(16_9603_2000),
            updated_at = Instant.ofEpochSecond(16_9603_2000),
        )

        val result = convert(
            Game(
                release_dates = listOf(date),
            ),
        )

        result shouldBeEqual YearQuarter(
            year = 2023,
            quarter = 3,
        )
    }

    @Test
    fun `should throw on no fields`() {
        listOf(
            ReleaseDate(id = 1),
            ReleaseDate(
                date = Instant.ofEpochSecond(16_9603_2000),
                category = YYYYMMMM,
            ),
            ReleaseDate(
                date = Instant.ofEpochSecond(16_9603_2000),
                category = YYYYMMMM,
            ),
        ).forEach {
            shouldThrow<IllegalArgumentException> { convert(Game(release_dates = listOf(it))) }
        }
    }

    internal companion object {
        @JvmStatic
        fun toApproximateDateTestSource(): List<Pair<ReleaseDate, Date>> = listOf(
            IgdbReleaseDateFixtures.gta21Oct1997 to YearMonthDay(1997, OCTOBER, 21),
            IgdbReleaseDateFixtures.gtaCategory1YyyyMmmm to YearMonth(2003, MARCH),
            IgdbReleaseDateFixtures.thief2Category2Yyyy to Year(2000),
            IgdbReleaseDateFixtures.xxComCategory3Q1 to YearQuarter(
                year = 1994,
                quarter = 1,
                description = Localized("Q1 1994", LanguageCode.ENGLISH),
            ),
            IgdbReleaseDateFixtures.xxComCategory4Q2 to YearQuarter(
                year = 1994,
                quarter = 2,
                description = Localized("Q2 1994", LanguageCode.ENGLISH),
            ),
            IgdbReleaseDateFixtures.zooTycoonCategory5Q3 to YearQuarter(
                year = 2001,
                quarter = 3,
                description = Localized("Q3 2001", LanguageCode.ENGLISH),
            ),
            IgdbReleaseDateFixtures.nightShiftCategory6Q4 to YearQuarter(
                year = 1990,
                quarter = 4,
                description = Localized("Q4 1990", LanguageCode.ENGLISH),
            ),
            IgdbReleaseDateFixtures.belowTheStoneTbdCategory6 to YearQuarter(
                year = 2023,
                quarter = 4,
                description = Localized("Q4 2023", LanguageCode.ENGLISH),
            ),
            IgdbReleaseDateFixtures.starWarsCategory7Tbd to Unknown(
                expected = null,
                description = Localized(value = "TBD", LanguageCode.ENGLISH),
            ),
            IgdbReleaseDateFixtures.portalStatus6 to YearMonthDay(2022, JUNE, 28),
        )
    }
}
