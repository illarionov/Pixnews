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

package ru.pixnews.library.ui.tooling.debuglayout.ruler

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.pixnews.library.ui.tooling.debuglayout.DebugLayer
import ru.pixnews.library.ui.tooling.debuglayout.DebugLayoutDefaults.Ruler
import ru.pixnews.library.ui.tooling.debuglayout.DisplayMetrics
import ru.pixnews.library.ui.tooling.debuglayout.ruler.RulerOrientation.VERTICAL
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType.MAJOR
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType.MINOR
import ru.pixnews.library.ui.tooling.debuglayout.ruler.ZeroOffset.Companion.toCommonZeroOffset
import kotlin.math.ceil
import kotlin.math.roundToInt

internal class VerticalRulerLayer(
    private val textMeasurer: TextMeasurer,
    private val displayMetrics: DisplayMetrics,
    private val step: DebugRulerMeasureUnit = Ruler.step,
    private val zeroOffset: DebugRulerVerticalZeroPoint = DebugRulerVerticalZeroPoint.ZERO,
    private val width: Dp = Ruler.verticalRulerWidth,
    private val ticksColor: Color = Ruler.tickColor,
    textStyle: TextStyle = Ruler.textStyle,
    private val backgroundColor: Color = Ruler.backgroundColor,
    private val outlineColor: Color = Ruler.outlineColor,
) : DebugLayer {
    private val minHeight = 24.dp
    private val outlineStrokeWidth = 0.5.dp
    private val minorTickWidth = Ruler.minorTickSize
    private val majorTickWidth = Ruler.majorTickSize
    private val strokeWidth = Ruler.tickStrokeWidth
    private val textStyleCenterMarker = textStyle.copy(
        textAlign = TextAlign.Left,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Top,
            trim = LineHeightStyle.Trim.None,
        ),
    )

    override fun ContentDrawScope.draw() {
        if (size.width < width.toPx() || size.height < minHeight.toPx()) {
            return
        }
        VerticalRulerDrawer(this).draw()
    }

    private inner class VerticalRulerDrawer(
        private val drawScope: ContentDrawScope,
    ) {
        val rulerSize: Size
        val displayMetrics: DisplayMetrics
        val minorTickWidthPx: Float
        val majorTickWidthPx: Float
        val strokeWidthPx: Float
        val textXOffset: Float
        val textConstraints: Constraints

        init {
            with(drawScope) {
                rulerSize = Size(width = width.toPx(), height = this.size.height)
                displayMetrics = this@VerticalRulerLayer.displayMetrics.withDensity(density)
                minorTickWidthPx = minorTickWidth.toPx()
                majorTickWidthPx = majorTickWidth.toPx()
                strokeWidthPx = strokeWidth.toPx()
                textXOffset = rulerSize.width - majorTickWidthPx - 3.dp.toPx()
                textConstraints = Constraints(
                    maxWidth = ceil(step.toPx(displayMetrics, VERTICAL)).roundToInt(),
                    maxHeight = ceil(textStyleCenterMarker.lineHeight.toPx()).toInt(),
                )
            }
        }

        fun draw() = with(drawScope) {
            drawBackground()
            drawTicksWithMarkers()
            drawOutline()
        }

        private fun ContentDrawScope.drawBackground() = drawRect(color = backgroundColor, size = rulerSize)

        private fun ContentDrawScope.drawOutline() = drawLine(
            outlineColor,
            start = Offset(rulerSize.width - 1, 0f),
            end = Offset(rulerSize.width - 1, rulerSize.height),
            strokeWidth = outlineStrokeWidth.toPx(),
        )

        private fun ContentDrawScope.drawTicksWithMarkers() {
            getTicksPositions(
                step = step,
                screenSize = rulerSize.height,
                displayMetrics = displayMetrics,
                zeroOffset = zeroOffset.toCommonZeroOffset(),
                VERTICAL,
            ).forEach { (position, type, markerValue) ->
                drawTick(position, type)
                if (type == MAJOR) {
                    drawMarker(position, markerValue)
                }
            }
        }

        private fun DrawScope.drawTick(position: Float, type: TickType) {
            val width = when (type) {
                MINOR -> minorTickWidthPx
                MAJOR -> majorTickWidthPx
            }
            drawLine(
                color = ticksColor,
                start = Offset(
                    x = rulerSize.width - width,
                    y = position,
                ),
                end = Offset(
                    x = rulerSize.width,
                    y = position,
                ),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round,
            )
        }

        private fun DrawScope.drawMarker(position: Float, value: Float) {
            val measuredText = textMeasurer.measure(
                    AnnotatedString(markerTextFormatter.format(value)),
                    maxLines = 1,
                    constraints = textConstraints,
                    style = textStyleCenterMarker,
                )

            val halfWidth = measuredText.size.width / 2f
            val halfHeight = measuredText.size.height / 2f
            withTransform(
                transformBlock = {
                    translate(
                        left = textXOffset - halfHeight,
                        top = position - halfWidth,
                    )
                    @Suppress("MagicNumber")
                    rotate(-90f, Offset(halfWidth, halfHeight))
                },
            ) {
                drawText(
                    textLayoutResult = measuredText,
                    color = textStyleCenterMarker.color,
                )
            }
        }
    }
}
