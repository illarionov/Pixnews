/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.util

import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
internal inline fun errorUnknownValue(
    value: Any?,
    dstClass: KClass<in Nothing>,
): Nothing = throw UnknownValueException(value, dstClass.simpleName)

internal class UnknownValueException(
    value: Any?,
    dstClass: String?,
) : IllegalStateException("Can not convert `$value` to `$dstClass`: unknown value")
