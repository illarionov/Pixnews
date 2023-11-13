/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.domain.model.rating.PegiRating
import ru.pixnews.domain.model.rating.PegiRating.PEGI_12
import ru.pixnews.domain.model.rating.PegiRating.PEGI_16
import ru.pixnews.domain.model.rating.PegiRating.PEGI_18
import ru.pixnews.domain.model.rating.PegiRating.PEGI_3
import ru.pixnews.domain.model.rating.PegiRating.PEGI_4
import ru.pixnews.domain.model.rating.PegiRating.PEGI_7
import ru.pixnews.foundation.database.util.errorUnknownValue

@Suppress("MagicNumber")
internal object PegiRatingConverter {
    @TypeConverter
    fun fromPegiRating(value: PegiRating?): Byte = when (value) {
        null -> 0
        PEGI_3 -> 3
        PEGI_4 -> 4
        PEGI_7 -> 7
        PEGI_12 -> 12
        PEGI_16 -> 16
        PEGI_18 -> 18
    }

    @TypeConverter
    fun toPegiRating(value: Byte): PegiRating? = when (value) {
        0.toByte() -> null
        3.toByte() -> PEGI_3
        4.toByte() -> PEGI_4
        7.toByte() -> PEGI_7
        12.toByte() -> PEGI_12
        16.toByte() -> PEGI_16
        18.toByte() -> PEGI_18
        else -> errorUnknownValue(value, PegiRating::class)
    }
}
