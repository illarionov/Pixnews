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

import ru.pixnews.library.ui.tooling.debuglayout.DisplayMetrics
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType.MAJOR
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType.MINOR
import ru.pixnews.library.ui.tooling.debuglayout.ruler.ZeroOffset.Alignment.CENTER
import ru.pixnews.library.ui.tooling.debuglayout.ruler.ZeroOffset.Alignment.LEFT_TOP
import ru.pixnews.library.ui.tooling.debuglayout.ruler.ZeroOffset.Alignment.RIGHT_BOTTOM
import kotlin.math.floor
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerHorizontalZeroPoint.Alignment as HorizontalAlignment
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerVerticalZeroPoint.Alignment as VerticalAlignment

internal data class Tick(
    val position: Float,
    val type: TickType = MAJOR,
    val markerValue: Float = 0f,
) {
    internal enum class TickType {
        MINOR, MAJOR
    }
}

internal data class ZeroOffset(
    val alignment: Alignment,
    val offset: DebugRulerMeasureUnit,
) {
    enum class Alignment {
        LEFT_TOP, CENTER, RIGHT_BOTTOM
    }

    internal companion object {
        internal val ZERO = ZeroOffset(LEFT_TOP, DebugRulerMeasureUnit.ZERO)

        internal fun DebugRulerHorizontalZeroPoint.toCommonZeroOffset(): ZeroOffset = ZeroOffset(
            alignment = when (this.alignment) {
                HorizontalAlignment.LEFT -> LEFT_TOP
                HorizontalAlignment.CENTER -> CENTER
                HorizontalAlignment.RIGHT -> RIGHT_BOTTOM
            },
            offset = this.offset,
        )

        internal fun DebugRulerVerticalZeroPoint.toCommonZeroOffset(): ZeroOffset = ZeroOffset(
            alignment = when (this.alignment) {
                VerticalAlignment.TOP -> LEFT_TOP
                VerticalAlignment.CENTER -> CENTER
                VerticalAlignment.BOTTOM -> RIGHT_BOTTOM
            },
            offset = this.offset,
        )
    }
}

internal fun getTicksPositions(
    step: DebugRulerMeasureUnit,
    screenSize: Float,
    displayMetrics: DisplayMetrics,
    zeroOffset: ZeroOffset = ZeroOffset.ZERO,
    orientation: RulerOrientation,
): Sequence<Tick> {
    val stepValue = step.value.toFloat()
    val halfStepValue = stepValue / 2f
    val stepPx = step.toPx(displayMetrics, orientation)
    val halfStepPx = stepPx / 2f

    return sequence {
        var (position, markerValue) = calculateInitialPosition(
            step = step,
            screenSize = screenSize,
            displayMetrics = displayMetrics,
            zeroOffset = zeroOffset,
            orientation = orientation,
        )
        while (position <= screenSize) {
            yieldTick(
                position = position,
                type = MAJOR,
                markerValue = markerValue,
                screenSize = screenSize,
            )
            yieldTick(
                position = position + halfStepPx,
                type = MINOR,
                markerValue = markerValue + halfStepValue,
                screenSize = screenSize,
            )

            position += stepPx
            markerValue += stepValue
        }
    }
}

private suspend fun SequenceScope<Tick>.yieldTick(
    position: Float,
    type: TickType,
    markerValue: Float,
    screenSize: Float,
) {
    @Suppress("ConvertTwoComparisonsToRangeCheck")
    if (position >= 0 && position <= screenSize) {
        yield(Tick(position = position, type = type, markerValue = markerValue))
    }
}

private class InitialPosition(
    val offset: Float,
    val markerValue: Float,
) {
    operator fun component1() = offset
    operator fun component2() = markerValue
}

private fun calculateInitialPosition(
    step: DebugRulerMeasureUnit,
    screenSize: Float,
    displayMetrics: DisplayMetrics,
    zeroOffset: ZeroOffset,
    orientation: RulerOrientation,
): InitialPosition {
    val offset = zeroOffset.offset.toPx(displayMetrics, orientation)
    val zeroPoint = when (zeroOffset.alignment) {
        LEFT_TOP -> offset
        CENTER -> screenSize / 2f + offset
        RIGHT_BOTTOM -> screenSize - offset
    }

    val stepPx = step.toPx(displayMetrics, orientation)
    val valueAtScreenZero: Float = -zeroPoint / stepPx

    val intValue = floor(valueAtScreenZero)
    val startPoint = (intValue - valueAtScreenZero) * stepPx

    return InitialPosition(
        offset = startPoint + 0f,
        markerValue = intValue * step.value + 0f,
    )
}
