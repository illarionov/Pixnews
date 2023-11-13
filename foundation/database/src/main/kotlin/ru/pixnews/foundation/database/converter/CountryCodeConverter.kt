/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.foundation.database.model.CountryCodeWrapper
import ru.pixnews.library.internationalization.country.CountryCode

internal object CountryCodeConverter {
    @TypeConverter
    fun fromCountryCode(code: CountryCodeWrapper?): String? = code?.countryCode?.iso31661Code

    @TypeConverter
    fun toCountryCode(value: String?): CountryCodeWrapper? = value?.let { CountryCodeWrapper(CountryCode(it)) }
}
