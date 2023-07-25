/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.util

public data class CanvasSize(
    val width: UInt,
    val height: UInt,
) {
    init {
        require(width != 0U && height != 0U)
    }

    public fun aspectRatio(): Float {
        return width.toFloat() / height.toFloat()
    }
}
