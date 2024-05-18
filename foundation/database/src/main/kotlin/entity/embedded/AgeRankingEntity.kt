/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.embedded

import androidx.room.Entity
import androidx.room.Ignore
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating
import ru.pixnews.domain.model.rating.PegiRating

@Entity
public data class AgeRankingEntity(
    val esrbRating: EsrbRating?,
    val pegiRating: PegiRating?,
) {
    @Suppress("NULLABLE_PROPERTY_TYPE")
    @Ignore
    val ageRanking: AgeRating? = if (esrbRating != null || pegiRating != null) {
        AgeRating(esrbRating, pegiRating)
    } else {
        null
    }
}
