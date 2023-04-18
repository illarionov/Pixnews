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

package ru.pixnews.library.ui.tooling.layoutgrid

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.pixnews.library.ui.tooling.layoutgrid.DebugGuideline.Bottom
import ru.pixnews.library.ui.tooling.layoutgrid.DebugGuideline.CenterHorizontal
import ru.pixnews.library.ui.tooling.layoutgrid.DebugGuideline.CenterVertical
import ru.pixnews.library.ui.tooling.layoutgrid.DebugGuideline.End
import ru.pixnews.library.ui.tooling.layoutgrid.DebugGuideline.Start
import ru.pixnews.library.ui.tooling.layoutgrid.DebugGuideline.Top
import ru.pixnews.library.ui.tooling.layoutgrid.GuidelineOffset.Percent

@Stable
public fun Modifier.debugLayoutGrid(
    size: Dp = 4.dp,
    color: Color = Color(alpha = 0.1f, red = 1f, green = 0f, blue = 0f),
    strokeWidth: Dp = 0.5.dp,
): Modifier = this.then(DrawDebugLayoutGridModifier(size, color, strokeWidth))

@Stable
public fun Modifier.debugGuideline(
    type: DebugGuideline = DebugGuideline.Start(),
    color: Color = Color(alpha = 0.98f, red = 1f, green = 0f, blue = 0f),
    strokeWidth: Dp = 1.dp,
): Modifier = this.then(DrawDebugGuidelineModifier(type, color, strokeWidth))

private class DrawDebugLayoutGridModifier(
    private val gridSize: Dp,
    private val color: Color,
    private val strokeWidth: Dp,
) : DrawModifier {
    override fun ContentDrawScope.draw() {
        drawContent()

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

        other as DrawDebugLayoutGridModifier

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

private class DrawDebugGuidelineModifier(
    private val guideline: DebugGuideline,
    private val color: Color,
    private val strokeWidth: Dp,
) : DrawModifier {
    override fun ContentDrawScope.draw() {
        drawContent()

        val halfStrokeWidth: Float = strokeWidth.toPx() / 2f
        when (guideline) {
            is Top -> drawHorizontalGuideline(
                offsetToPx(guideline.offset, true) - halfStrokeWidth,
            )

            is Bottom -> drawHorizontalGuideline(
                size.height - halfStrokeWidth - offsetToPx(guideline.offset, true),
            )

            is Start -> drawVerticalGuideline(
                offsetToPx(guideline.offset, false) - halfStrokeWidth,
            )

            is End -> drawVerticalGuideline(
                size.width - halfStrokeWidth - offsetToPx(guideline.offset, false),
            )

            is CenterHorizontal -> drawVerticalGuideline(
                size.width / 2f - halfStrokeWidth + guideline.verticalOffset.toPx(),
            )

            is CenterVertical -> drawHorizontalGuideline(
                size.height / 2f - halfStrokeWidth + guideline.horizontalOffset.toPx(),
            )
        }
    }

    private fun ContentDrawScope.drawHorizontalGuideline(topOffset: Float) {
        drawLine(
            color = color,
            strokeWidth = strokeWidth.toPx(),
            start = Offset(x = 0f, y = topOffset),
            end = Offset(x = size.width - 1f, y = topOffset),
        )
    }

    private fun ContentDrawScope.drawVerticalGuideline(startOffset: Float) {
        drawLine(
            color = color,
            strokeWidth = strokeWidth.toPx(),
            start = Offset(x = startOffset, y = 0f),
            end = Offset(x = startOffset, y = size.height - 1f),
        )
    }

    private fun ContentDrawScope.offsetToPx(offset: GuidelineOffset, isHorizontalGuideline: Boolean): Float {
        return when (offset) {
            is GuidelineOffset.Dp -> offset.value.toPx()
            is Percent -> offset.value * if (isHorizontalGuideline) size.height else size.width
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as DrawDebugGuidelineModifier

        if (guideline != other.guideline) {
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
        var result = guideline.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + strokeWidth.hashCode()
        return result
    }
}
