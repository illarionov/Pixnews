/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.library.ui.tooling.debuglayout.ruler

import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerVerticalZeroPoint.Alignment.TOP

public data class DebugRulerVerticalZeroPoint(
    val alignment: Alignment = TOP,
    val offset: DebugRulerMeasureUnit = DebugRulerMeasureUnit.ZERO,
) {
    public enum class Alignment {
        TOP, CENTER, BOTTOM
    }

    public companion object {
        public val ZERO: DebugRulerVerticalZeroPoint = DebugRulerVerticalZeroPoint(TOP, DebugRulerMeasureUnit.ZERO)
    }
}
