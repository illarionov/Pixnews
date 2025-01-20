/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.AgeRatingCategoryEnum.AGERATING_CATEGORY_NULL
import at.released.igdbclient.model.AgeRatingCategoryEnum.ESRB
import at.released.igdbclient.model.AgeRatingCategoryEnum.PEGI
import at.released.igdbclient.model.AgeRatingRatingEnum
import at.released.igdbclient.model.Game
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating
import ru.pixnews.domain.model.rating.PegiRating
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested

internal object IgdbGameAgeRatingConverter : IgdbGameFieldConverter<AgeRating?> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.age_ratings.category,
        from.age_ratings.rating,
    )

    override fun convert(game: Game): AgeRating? = with(game.age_ratings) {
        if (this.find { it.category == AGERATING_CATEGORY_NULL } != null) {
            errorFieldNotRequested("age_ratings.category")
        }

        val esrbRating: EsrbRating? = firstNotNullOfOrNull { igdbRating ->
            if (igdbRating.category == ESRB) {
                igdbRating.rating.toEsrbRating()
            } else {
                null
            }
        }
        val pegiRating: PegiRating? = firstNotNullOfOrNull { igdbRating ->
            if (igdbRating.category == PEGI) {
                igdbRating.rating.toPegiRating()
            } else {
                null
            }
        }

        return if (esrbRating != null || pegiRating != null) {
            AgeRating(
                esrbRating = esrbRating,
                pegiRating = pegiRating,
            )
        } else {
            null
        }
    }

    private fun AgeRatingRatingEnum.toEsrbRating(): EsrbRating? = when (this) {
        AgeRatingRatingEnum.AGERATING_RATING_NULL -> errorFieldNotRequested("rating.rating")
        AgeRatingRatingEnum.E, AgeRatingRatingEnum.EC -> EsrbRating.EVERYONE
        AgeRatingRatingEnum.E10 -> EsrbRating.EVERYONE_10PLUS
        AgeRatingRatingEnum.T -> EsrbRating.TEEN
        AgeRatingRatingEnum.M -> EsrbRating.MATURE_17PLUS
        AgeRatingRatingEnum.AO -> EsrbRating.ADULTS_ONLY_18PLUS
        AgeRatingRatingEnum.RP -> EsrbRating.RATING_PENDING
        else -> null
    }

    private fun AgeRatingRatingEnum.toPegiRating(): PegiRating? = when (this) {
        AgeRatingRatingEnum.AGERATING_RATING_NULL -> errorFieldNotRequested("rating.rating")
        AgeRatingRatingEnum.THREE -> PegiRating.PEGI_3
        AgeRatingRatingEnum.SEVEN -> PegiRating.PEGI_7
        AgeRatingRatingEnum.TWELVE -> PegiRating.PEGI_12
        AgeRatingRatingEnum.SIXTEEN -> PegiRating.PEGI_16
        AgeRatingRatingEnum.EIGHTEEN -> PegiRating.PEGI_18
        else -> null
    }
}
