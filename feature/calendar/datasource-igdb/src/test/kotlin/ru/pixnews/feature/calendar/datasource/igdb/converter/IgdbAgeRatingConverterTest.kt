/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating.MATURE_17PLUS
import ru.pixnews.domain.model.rating.PegiRating.PEGI_16
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbAgeRatingFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.acbM
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.ceroC
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.classIndFourteen
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.esrbM
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.gracEighteen
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.pegiSixteen
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.usk16
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum
import ru.pixnews.igdbclient.model.AgeRating as IgdbAgeRating

class IgdbAgeRatingConverterTest {
    @ParameterizedTest
    @MethodSource("ageRatingTestSource")
    fun `toAgeRating() should convert platforms`(testData: Pair<List<IgdbAgeRating>, AgeRating?>) {
        val result = testData.first.toAgeRating()
        result shouldBe testData.second
    }

    @Test
    fun `toAgeRating() should fail when required fields are not defined`() {
        listOf(
            IgdbAgeRating(
                category = AgeRatingCategoryEnum.AGERATING_CATEGORY_NULL,
                rating = AgeRatingRatingEnum.SIXTEEN,
            ),
            IgdbAgeRating(
                category = AgeRatingCategoryEnum.PEGI,
                rating = AgeRatingRatingEnum.AGERATING_RATING_NULL,
            ),
        ).forEach {
            shouldThrow<IllegalArgumentException> { listOf(it).toAgeRating() }
        }
    }

    @Test
    fun `toAgeRating() should convert rating with minimal fields`() {
        val esrbM = IgdbAgeRating(
            category = AgeRatingCategoryEnum.ESRB,
            rating = AgeRatingRatingEnum.M,
        )
        val pegiSixteen = IgdbAgeRating(
            category = AgeRatingCategoryEnum.PEGI,
            rating = AgeRatingRatingEnum.SIXTEEN,
        )

        val result = listOf(esrbM, pegiSixteen).toAgeRating()

        result shouldBe AgeRating(esrbRating = MATURE_17PLUS, pegiRating = PEGI_16)
    }

    companion object {
        @JvmStatic
        fun ageRatingTestSource(): List<Pair<List<IgdbAgeRating>, AgeRating?>> = listOf(
            listOf(
                IgdbAgeRatingFixtures.esrbM,
                IgdbAgeRatingFixtures.pegiSixteen,
                IgdbAgeRatingFixtures.ceroC,
                IgdbAgeRatingFixtures.usk16,
                IgdbAgeRatingFixtures.gracEighteen,
                IgdbAgeRatingFixtures.acbM,
                IgdbAgeRatingFixtures.classIndFourteen,
            ) to AgeRating(
                esrbRating = MATURE_17PLUS,
                pegiRating = PEGI_16,
            ),
            listOf(IgdbAgeRatingFixtures.esrbM) to AgeRating(esrbRating = MATURE_17PLUS),
            listOf(IgdbAgeRatingFixtures.pegiSixteen) to AgeRating(pegiRating = PEGI_16),
            listOf(IgdbAgeRatingFixtures.acbM) to null,
        )
    }
}
