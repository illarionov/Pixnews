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

import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerHorizontalZeroPoint.Alignment.LEFT

public data class DebugRulerHorizontalZeroPoint(
    val alignment: Alignment = LEFT,
    val offset: DebugRulerMeasureUnit = DebugRulerMeasureUnit.ZERO,
) {
    public enum class Alignment {
        LEFT, CENTER, RIGHT
    }

    public companion object {
        public val ZERO: DebugRulerHorizontalZeroPoint = DebugRulerHorizontalZeroPoint(LEFT, DebugRulerMeasureUnit.ZERO)
    }
}