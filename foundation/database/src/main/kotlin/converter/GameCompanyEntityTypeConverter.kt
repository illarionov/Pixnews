/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.foundation.database.entity.GameCompanyEntity
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type
import ru.pixnews.foundation.database.util.errorUnknownValue

internal object GameCompanyEntityTypeConverter {
    @TypeConverter
    fun fromCompanyType(value: GameCompanyEntity.Type?): Int = value?.databaseId ?: 0

    @TypeConverter
    fun toCompanyType(value: Int): GameCompanyEntity.Type? = if (value != 0) {
        GameCompanyEntity.Type.entries.firstOrNull { it.databaseId == value }
            ?: errorUnknownValue(value, Type::class)
    } else {
        null
    }
}
