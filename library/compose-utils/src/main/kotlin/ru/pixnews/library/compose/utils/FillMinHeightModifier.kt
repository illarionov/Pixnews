/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.compose.utils

import androidx.annotation.FloatRange
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

/**
 * Like [Modifier.fillMaxHeight][androidx.compose.foundation.layout.fillMaxHeight] but only sets the minimum height
 * without limiting the maximum.
 *
 * @param fraction The fraction of the maximum height to use, between 0 and 1, inclusive.
 * @return
 */
public fun Modifier.fillMinHeight(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Modifier = this
    .then(FillMinHeightModifier(fraction))

private class FillMinHeightModifier(
    @FloatRange(from = 0.0, to = 1.0)
    private val fraction: Float = 0f,
) : LayoutModifier {
    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val minHeight = if (constraints.hasBoundedHeight) {
            (constraints.maxHeight * fraction).roundToInt()
                .coerceIn(constraints.minHeight, constraints.maxHeight)
        } else {
            constraints.minHeight
        }
        val placeable = measurable.measure(
            if (constraints.minHeight == minHeight) {
                constraints
            } else {
                constraints.copy(minHeight = minHeight)
            },
        )

        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as FillMinHeightModifier

        if (fraction != other.fraction) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return fraction.hashCode()
    }
}
