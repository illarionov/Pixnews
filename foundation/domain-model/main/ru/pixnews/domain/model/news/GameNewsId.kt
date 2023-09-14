/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.news

@JvmInline
public value class GameNewsId(
    public val stringValue: String,
) {
    init {
        require(stringValue.isNotBlank())
    }
    override fun toString(): String = stringValue
}
