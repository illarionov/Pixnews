/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")

package ru.pixnews.library.ui.tooling.debuglayout

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp

internal class GridLayer(
    private val gridSize: Dp,
    private val color: Color,
    private val strokeWidth: Dp,
) : DebugLayer {
    override fun ContentDrawScope.draw() {
        val strokeWidthPx = strokeWidth.toPx()
        val halfStrokeWidthPx = strokeWidthPx / 2f

        val verticalStep = Offset(x = gridSize.toPx(), y = 0f)
        var verticalStartOffset = Offset(x = -halfStrokeWidthPx, y = 0f)
        var verticalEndOffset = Offset(x = 0f, y = size.height - 1f)

        while (verticalStartOffset.x < size.width) {
            drawLine(
                color = color,
                strokeWidth = strokeWidthPx,
                start = verticalStartOffset,
                end = verticalEndOffset,
            )
            verticalStartOffset += verticalStep
            verticalEndOffset += verticalStep
        }

        val horizontalStep = Offset(x = 0f, y = gridSize.toPx())
        var horizontalStartOffset = Offset(x = 0f, y = -halfStrokeWidthPx)
        var horizontalEndOffset = Offset(x = size.width - 1f, y = 0f)

        while (horizontalStartOffset.y < size.height) {
            drawLine(
                color = color,
                strokeWidth = strokeWidthPx,
                start = horizontalStartOffset,
                end = horizontalEndOffset,
            )
            horizontalStartOffset += horizontalStep
            horizontalEndOffset += horizontalStep
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as GridLayer

        if (gridSize != other.gridSize) {
            return false
        }
        if (color != other.color) {
            return false
        }
        if (strokeWidth != other.strokeWidth) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = gridSize.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + strokeWidth.hashCode()
        return result
    }
}
