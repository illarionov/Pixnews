/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating
import ru.pixnews.domain.model.rating.PegiRating
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.AGERATING_CATEGORY_NULL
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.ESRB
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.PEGI
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum
import ru.pixnews.igdbclient.model.AgeRating as IgdbAgeRating

internal fun List<IgdbAgeRating>.toAgeRating(): AgeRating? {
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
