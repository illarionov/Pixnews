/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.Instant.Companion

internal object InstantConverter {
    @TypeConverter
    fun fromInstant(value: Instant?): Long? = value?.epochSeconds

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let(Companion::fromEpochSeconds)
}
