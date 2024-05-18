/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity
import ru.pixnews.foundation.database.util.errorUnknownValue

internal object GameReleaseDateEntityTypeConverter {
    @TypeConverter
    fun fromDateType(type: GameReleaseDateEntity.Type?): Int = type?.databaseId ?: 0

    @TypeConverter
    fun toDateType(value: Int): GameReleaseDateEntity.Type? {
        return if (value != 0) {
            GameReleaseDateEntity.Type
                .entries
                .firstOrNull { it.databaseId == value }
                ?: errorUnknownValue(value, GameReleaseDateEntity.Type::class)
        } else {
            null
        }
    }
}
