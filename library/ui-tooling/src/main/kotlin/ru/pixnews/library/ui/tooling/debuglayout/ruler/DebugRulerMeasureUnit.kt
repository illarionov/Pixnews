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
package ru.pixnews.library.ui.tooling.debuglayout.ruler

import ru.pixnews.library.ui.tooling.debuglayout.DisplayMetrics
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerMeasureUnit.Unit.DP
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerMeasureUnit.Unit.INCHES
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerMeasureUnit.Unit.MM
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerMeasureUnit.Unit.PX

public data class DebugRulerMeasureUnit(
    val value: Int,
    val units: Unit,
) {
    override fun toString(): String = "$value.$units"
    public enum class Unit {
        /**
         * Device-independent pixels (dp)
         */
        DP,

        /**
         * Pixels (Px)
         */
        PX,

        /**
         * Millimeters
         */
        MM,

        /**
         * Inches
         */
        INCHES,
        ;
    }

    public companion object {
        public val ZERO: DebugRulerMeasureUnit = DebugRulerMeasureUnit(0, PX)
    }
}

public fun Int.rulerPx(): DebugRulerMeasureUnit = DebugRulerMeasureUnit(this, PX)

public fun Int.rulerMm(): DebugRulerMeasureUnit = DebugRulerMeasureUnit(this, MM)

public fun Int.rulerDp(): DebugRulerMeasureUnit = DebugRulerMeasureUnit(this, DP)

public fun Int.rulerInches(): DebugRulerMeasureUnit = DebugRulerMeasureUnit(this, INCHES)

internal enum class RulerOrientation {
    HORIZONTAL, VERTICAL
}

@Suppress("MagicNumber", "FLOAT_IN_ACCURATE_CALCULATIONS")
internal fun DebugRulerMeasureUnit.toPx(displayMetrics: DisplayMetrics, orientation: RulerOrientation) = when (units) {
    DP -> value * displayMetrics.density
    PX -> value.toFloat()
    MM -> value * displayMetrics.exactDpi(orientation) / 25.4f
    INCHES -> value * displayMetrics.exactDpi(orientation)
}

private fun DisplayMetrics.exactDpi(
    orientation: RulerOrientation,
) = if (orientation == RulerOrientation.VERTICAL) ydpi else xdpi
