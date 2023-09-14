/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import java.util.Locale

internal fun requireField(
    fieldName: String,
    condition: Boolean,
) = require(condition) { fieldShouldBeRequestedError(fieldName) }

internal fun errorFieldNotRequested(
    fieldName: String,
): Nothing {
    throw IllegalArgumentException(fieldShouldBeRequestedError(fieldName))
}

internal fun fieldShouldBeRequestedError(fieldName: String) = String.format(
    Locale.ROOT,
    "%s field should be requested",
    fieldName,
)

internal fun requireFieldInitialized(fieldName: String, fieldValue: String) = fieldValue.also {
    requireField(fieldName, it.isNotEmpty())
}

internal fun requireFieldInitialized(fieldName: String, fieldValue: Long): Long = fieldValue.also {
    requireField(fieldName, it != 0L)
}
