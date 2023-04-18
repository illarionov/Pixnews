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
package ru.pixnews.library.ui.tooling.layoutgrid

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.pixnews.library.ui.tooling.layoutgrid.ColumnsType.STRETCH

public sealed class DebugLayoutGrid {
    public data class Grid(
        val size: Dp = 4.dp,
        val color: Color = Color(alpha = 0.1f, red = 1f, green = 0f, blue = 0f),
    ) : DebugLayoutGrid()

    public data class Columns(
        val count: SeriesCount = SeriesCount.Auto,
        val color: Color = Color(alpha = 0.02f, red = 1f, green = 0f, blue = 0f),
        val type: ColumnsType = STRETCH,
        val width: Dp = 1.dp,
        val offset: Dp = 0.dp,
        val gutter: Dp = 3.dp,
    ) : DebugLayoutGrid()

    public data class Guideline(
        val value: DebugGuideline = DebugGuideline.Start(),
        val color: Color = Color(alpha = 0.98f, red = 1f, green = 0f, blue = 0f),
    ) : DebugLayoutGrid()

    public data class Rows(
        val count: SeriesCount = SeriesCount.Auto,
        val color: Color = Color(alpha = 0.02f, red = 1f, green = 0f, blue = 0f),
        val type: RowsType = RowsType.STRETCH,
        val height: Dp = 1.dp,
        val offset: Dp = 0.dp,
        val gutter: Dp = 3.dp,
    ) : DebugLayoutGrid()
}

@JvmInline
public value class SeriesCount(
    public val value: UInt,
) {
    public companion object {
        public val Auto: SeriesCount = SeriesCount(0U)
    }
}

public enum class ColumnsType {
    LEFT, RIGHT, CENTER, STRETCH
}

public enum class RowsType {
    TOP, BOTTOM, CENTER, STRETCH
}

public sealed class DebugGuideline {
    public data class Start(val offset: GuidelineOffset = 16.dp.asGuidelineOffset) : DebugGuideline()
    public data class End(val offset: GuidelineOffset = 16.dp.asGuidelineOffset) : DebugGuideline()
    public data class Top(val offset: GuidelineOffset = 16.dp.asGuidelineOffset) : DebugGuideline()
    public data class Bottom(val offset: GuidelineOffset = 16.dp.asGuidelineOffset) : DebugGuideline()
    public data class CenterHorizontal(val verticalOffset: Dp = 0.dp) : DebugGuideline()
    public data class CenterVertical(val horizontalOffset: Dp = 0.dp) : DebugGuideline()
}

public sealed class GuidelineOffset {
    public data class Dp(val value: androidx.compose.ui.unit.Dp) : GuidelineOffset()
    public data class Percent(val value: Float) : GuidelineOffset()
}

public inline val Dp.asGuidelineOffset: GuidelineOffset.Dp get() = GuidelineOffset.Dp(value = this)
public inline val Float.asGuidelineOffsetPercent: GuidelineOffset.Percent get() = GuidelineOffset.Percent(value = this)

public val UInt.asRowColumnCount: SeriesCount
    get() = SeriesCount(this)
