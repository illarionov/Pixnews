/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.assertion

import androidx.compose.ui.graphics.Color
import assertk.Assert
import assertk.assertions.isZero
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show

public fun Assert<Color>.isTransparent(): Unit = prop(Color::alpha).isZero()

public fun Assert<Color>.isCloseTo(
    expected: Color,
    tolerance: Float = 0.01f,
): Unit = given { actual ->
    @Suppress("ComplexCondition")
    if (
        actual.red.isCloseTo(expected.red, tolerance) &&
        actual.green.isCloseTo(expected.green, tolerance) &&
        actual.blue.isCloseTo(expected.blue, tolerance) &&
        actual.alpha.isCloseTo(expected.alpha, tolerance)
    ) {
        return
    }
    expected("${show(actual)} to be close to ${show(expected)} with delta of ${show(tolerance)}, but was not")
}

private fun Float.isCloseTo(value: Float, delta: Float): Boolean =
    this >= value.minus(delta) && this <= value.plus(delta)
