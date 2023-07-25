/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.review

@JvmInline
public value class ReviewerRating(
    public val intValue: UInt,
) {
    init {
        require(intValue in 1U..10U)
    }

    override fun toString(): String = intValue.toString()
}
