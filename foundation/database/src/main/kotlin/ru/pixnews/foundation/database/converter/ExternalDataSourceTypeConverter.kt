/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.domain.model.datasource.DataSourceType
import ru.pixnews.domain.model.datasource.DataSourceType.IGDB
import ru.pixnews.domain.model.datasource.DataSourceType.RAWG
import ru.pixnews.foundation.database.util.errorUnknownValue

internal object ExternalDataSourceTypeConverter {
    @TypeConverter
    fun fromExternalDataSourceType(value: DataSourceType?): Int = when (value) {
        null -> 0
        IGDB -> 1
        RAWG -> 2
    }

    @TypeConverter
    fun toExternalDataSourceType(value: Int): DataSourceType? {
        return when (value) {
            0 -> null
            1 -> IGDB
            2 -> RAWG
            else -> errorUnknownValue(value, DataSourceType::class)
        }
    }
}
