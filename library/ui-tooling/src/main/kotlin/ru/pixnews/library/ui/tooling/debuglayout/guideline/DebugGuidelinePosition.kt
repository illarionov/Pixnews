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
package ru.pixnews.library.ui.tooling.debuglayout.guideline

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.pixnews.library.ui.tooling.debuglayout.DebugLayoutDefaults.Guideline
import ru.pixnews.library.ui.tooling.debuglayout.guideline.DebugGuidelineOffset.Percent

public sealed class DebugGuidelinePosition {
    public data class Start(
        val offset: DebugGuidelineOffset = Guideline.horizontalOffset,
    ) : DebugGuidelinePosition()

    public data class End(
        val offset: DebugGuidelineOffset = Guideline.horizontalOffset,
    ) : DebugGuidelinePosition()

    public data class Top(
        val offset: DebugGuidelineOffset = Guideline.verticalOffset,
    ) : DebugGuidelinePosition()

    public data class Bottom(
        val offset: DebugGuidelineOffset = Guideline.verticalOffset,
    ) : DebugGuidelinePosition()

    public data class CenterHorizontal(
        val verticalOffset: Dp = 0.dp,
    ) : DebugGuidelinePosition()

    public data class CenterVertical(
        val horizontalOffset: Dp = 0.dp,
    ) : DebugGuidelinePosition()
}

public sealed class DebugGuidelineOffset {
    public data class Dp(val value: androidx.compose.ui.unit.Dp) : DebugGuidelineOffset()
    public data class Percent(val value: Float) : DebugGuidelineOffset()
}

public inline val Dp.asGuidelineOffset: DebugGuidelineOffset.Dp get() = DebugGuidelineOffset.Dp(value = this)
public inline val Float.asGuidelineOffsetPercent: Percent
    get() = Percent(value = this)
