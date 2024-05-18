/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.internationalization.country

/**
 * Two-letters ISO 3166-1 country code
 */
@JvmInline
public value class CountryCode(
    public val iso31661Code: String,
) {
    init {
        require(iso31661Code.length == 2)
        require(iso31661Code.all { it in 'A'..'Z' })
    }
    override fun toString(): String = iso31661Code

    public companion object
}
