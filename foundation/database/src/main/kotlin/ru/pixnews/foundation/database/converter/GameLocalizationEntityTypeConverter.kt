/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type
import ru.pixnews.foundation.database.entity.game.GameLocalizationEntity
import ru.pixnews.foundation.database.util.errorUnknownValue

internal object GameLocalizationEntityTypeConverter {
    @TypeConverter
    fun fromType(value: GameLocalizationEntity.Type?): Int = value?.databaseId ?: 0

    @TypeConverter
    fun toType(value: Int): GameLocalizationEntity.Type? = if (value != 0) {
        GameLocalizationEntity.Type.entries.firstOrNull { it.databaseId == value }
            ?: errorUnknownValue(value, Type::class)
    } else {
        null
    }
}
