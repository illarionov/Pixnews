/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.domain.model.rating.EsrbRating
import ru.pixnews.domain.model.rating.EsrbRating.ADULTS_ONLY_18PLUS
import ru.pixnews.domain.model.rating.EsrbRating.EVERYONE
import ru.pixnews.domain.model.rating.EsrbRating.EVERYONE_10PLUS
import ru.pixnews.domain.model.rating.EsrbRating.MATURE_17PLUS
import ru.pixnews.domain.model.rating.EsrbRating.RATING_PENDING
import ru.pixnews.domain.model.rating.EsrbRating.RATING_PENDING_LIKELY_MATURE_17PLUS
import ru.pixnews.domain.model.rating.EsrbRating.TEEN
import ru.pixnews.foundation.database.util.errorUnknownValue

@Suppress("MagicNumber")
internal object EsrbRatingConverter {
    @TypeConverter
    fun fromEsrbRating(value: EsrbRating?): Byte = when (value) {
        null -> 0
        EVERYONE -> 1
        EVERYONE_10PLUS -> 10
        TEEN -> 12
        MATURE_17PLUS -> 17
        ADULTS_ONLY_18PLUS -> 18
        RATING_PENDING -> 100
        RATING_PENDING_LIKELY_MATURE_17PLUS -> 117
    }

    @TypeConverter
    fun toEsrbRating(value: Byte): EsrbRating? = when (value) {
        0.toByte() -> null
        1.toByte() -> EVERYONE
        10.toByte() -> EVERYONE_10PLUS
        12.toByte() -> TEEN
        17.toByte() -> MATURE_17PLUS
        18.toByte() -> ADULTS_ONLY_18PLUS
        100.toByte() -> RATING_PENDING
        117.toByte() -> RATING_PENDING_LIKELY_MATURE_17PLUS
        else -> errorUnknownValue(value, EsrbRating::class)
    }
}
