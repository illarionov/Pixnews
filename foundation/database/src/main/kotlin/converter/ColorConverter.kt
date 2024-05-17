/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.domain.model.util.Color
import ru.pixnews.foundation.database.model.ColorWrapper

internal object ColorConverter {
    @TypeConverter
    fun fromColor(value: ColorWrapper?): Int? = value?.color?.let {
        if (it != Color.Unspecified) {
            it.argbValue()
        } else {
            null
        }
    }

    @TypeConverter
    fun toColor(value: Int?): ColorWrapper = ColorWrapper(value?.let(::Color) ?: Color.Unspecified)
}
