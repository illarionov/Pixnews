/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.library.internationalization.language.LanguageCode

internal object LanguageCodeConverter {
    @TypeConverter
    fun fromLanguageCode(value: LanguageCodeWrapper?): String? = value?.code?.toString()

    @TypeConverter
    fun toLanguageCode(value: String?): LanguageCodeWrapper? = value?.let { LanguageCodeWrapper(LanguageCode.from(it)) }
}
