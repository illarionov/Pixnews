/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.rating.EsrbRating.MATURE_17PLUS
import ru.pixnews.domain.model.rating.PegiRating.PEGI_16
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameAgeRatingConverter.convert
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbAgeRatingFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.acbM
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.ceroC
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.classIndFourteen
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.esrbM
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.gracEighteen
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.pegiSixteen
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating.usk16
import ru.pixnews.igdbclient.model.AgeRating
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.AGERATING_CATEGORY_NULL
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.ESRB
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.PEGI
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.AGERATING_RATING_NULL
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.M
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.SIXTEEN
import ru.pixnews.igdbclient.model.Game

class IgdbGameAgeRatingConverterTest {
    @ParameterizedTest
    @MethodSource("ageRatingTestSource")
    fun `should convert platforms`(testData: Pair<List<AgeRating>, ru.pixnews.domain.model.rating.AgeRating?>) {
        val game = Game(age_ratings = testData.first)
        val result = convert(game)
        result shouldBe testData.second
    }

    @Test
    fun `should fail when required fields are not defined`() {
        listOf(
            AgeRating(
                category = AGERATING_CATEGORY_NULL,
                rating = SIXTEEN,
            ),
            AgeRating(
                category = PEGI,
                rating = AGERATING_RATING_NULL,
            ),
        ).forEach { ageRating ->
            val game = Game(age_ratings = listOf(ageRating))
            shouldThrow<IllegalArgumentException> { convert(game) }
        }
    }

    @Test
    fun `should convert rating with minimal fields`() {
        val esrbM = AgeRating(
            category = ESRB,
            rating = M,
        )
        val pegiSixteen = AgeRating(
            category = PEGI,
            rating = SIXTEEN,
        )

        val result = convert(Game(age_ratings = listOf(esrbM, pegiSixteen)))

        result shouldBe ru.pixnews.domain.model.rating.AgeRating(esrbRating = MATURE_17PLUS, pegiRating = PEGI_16)
    }

    companion object {
        @JvmStatic
        fun ageRatingTestSource(): List<Pair<List<AgeRating>, ru.pixnews.domain.model.rating.AgeRating?>> = listOf(
            listOf(
                IgdbAgeRatingFixtures.esrbM,
                IgdbAgeRatingFixtures.pegiSixteen,
                IgdbAgeRatingFixtures.ceroC,
                IgdbAgeRatingFixtures.usk16,
                IgdbAgeRatingFixtures.gracEighteen,
                IgdbAgeRatingFixtures.acbM,
                IgdbAgeRatingFixtures.classIndFourteen,
            ) to ru.pixnews.domain.model.rating.AgeRating(
                esrbRating = MATURE_17PLUS,
                pegiRating = PEGI_16,
            ),
            listOf(IgdbAgeRatingFixtures.esrbM) to ru.pixnews.domain.model.rating.AgeRating(esrbRating = MATURE_17PLUS),
            listOf(IgdbAgeRatingFixtures.pegiSixteen) to ru.pixnews.domain.model.rating.AgeRating(pegiRating = PEGI_16),
            listOf(IgdbAgeRatingFixtures.acbM) to null,
        )
    }
}
