/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.url

@JvmInline
public value class Url(
    public val raw: String,
) {
    init {
        require(raw.isNotBlank())
    }

    override fun toString(): String = raw
}
