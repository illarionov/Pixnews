/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.kotlin.utils

import java.util.Locale

public fun CharSequence.isWhitespaceOnly(): Boolean {
    return isNotEmpty() && isBlank()
}

public fun CharSequence.isNotWhitespaceOnly(): Boolean {
    return isEmpty() || !isBlank()
}

public fun String.capitalize(locale: Locale): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
}
