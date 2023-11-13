/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.foundation.database.entity.game.GameTextEntity
import ru.pixnews.foundation.database.util.errorUnknownValue

internal object GameTextInfoEntityTypeConverter {
    @TypeConverter
    fun fromTextType(type: GameTextEntity.Type?): Int = type?.databaseId ?: 0

    @TypeConverter
    fun toTextType(value: Int): GameTextEntity.Type? {
        return if (value != 0) {
            GameTextEntity.Type
                .entries
                .firstOrNull { it.databaseId == value }
                ?: errorUnknownValue(value, GameTextEntity.Type::class)
        } else {
            null
        }
    }
}
