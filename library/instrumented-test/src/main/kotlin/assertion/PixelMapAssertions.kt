/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.assertion

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import assertk.Assert

/**
 * Asserts that the color at a specific pixel in the bitmap at ([x], [y]) is [expected].
 */
public fun Assert<PixelMap>.hasPixelColor(
    expected: Color,
    x: Int,
    y: Int,
    tolerance: Float = 0.01f,
): Unit = transform { it[x, y] }.isCloseTo(expected, tolerance)
