/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
