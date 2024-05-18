/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.assertion

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.toPixelMap
import assertk.Assert
import assertk.all
import assertk.assertions.support.appendName

public fun Assert<ImageBitmap>.hasPixels(
    expectedColor: Color,
    tolerance: Float = 0.001f,
): Unit = transform { it.toPixelMap() }.given { pixelMap: PixelMap ->
    for (y in 0 until pixelMap.height) {
        for (x in 0 until pixelMap.width) {
            assertThat(
                actual = pixelMap[x, y],
                name = appendName("(x:$x, y:$y)"),
            ).isCloseTo(expectedColor, tolerance)
        }
    }
}

/**
 * Asserts that the colors at specific pixels in the vertices of bitmap is [expectedColor].
 */
public fun Assert<ImageBitmap>.hasPixelsOfVertices(
    expectedColor: Color,
): Unit = transform(transform = ImageBitmap::toPixelMap).all {
    transform("top left pixel") { it[0, 0] }.isCloseTo(expectedColor)
    transform("bottom left pixel") { it[0, it.height - 1] }.isCloseTo(expectedColor)
    transform("top right pixel") { it[it.width - 1, 0] }.isCloseTo(expectedColor)
    transform("bottom right pixel") { it[it.width - 1, it.height - 1] }.isCloseTo(expectedColor)
}
