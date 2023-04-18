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
package ru.pixnews.library.ui.tooling.debuglayout

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.pixnews.library.ui.tooling.debuglayout.guideline.DebugGuidelineOffset
import ru.pixnews.library.ui.tooling.debuglayout.guideline.asGuidelineOffset
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.RowsColumnsCount
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerMeasureUnit
import ru.pixnews.library.ui.tooling.debuglayout.ruler.rulerDp

public object DebugLayoutDefaults {
    public val colorPrimary: Color = Color(0x11018786)
    public val colorSecondary: Color = Color(0x116200EE)
    public val colorTertiary: Color = Color(0x11EE0290)
    public val colorGuidelines: Color = Color(alpha = 0.98f, red = 1f, green = 0f, blue = 0f)

    public object Grid {
        public val size: Dp = 4.dp
        public val color: Color = colorGuidelines.copy(alpha = 0.1f)
        public val strokeWidth: Dp = 0.5.dp
    }

    public object Column {
        public val color: Color = colorPrimary
        public val defaultColumns: RowsColumnsCount = RowsColumnsCount(3)
        public val width: Dp = 64.dp
        public val offset: Dp = 0.dp
        public val gutter: Dp = 16.dp
        public val margin: Dp = 16.dp
    }

    public object Row {
        public val color: Color = colorSecondary
        public val defaultRows: RowsColumnsCount = RowsColumnsCount(3)
        public val height: Dp = 64.dp
        public val offset: Dp = 0.dp
        public val gutter: Dp = 16.dp
        public val margin: Dp = 0.dp
    }

    public object Guideline {
        public val color: Color = colorGuidelines
        public val strokeWidth: Dp = 1.dp
        public val horizontalOffset: DebugGuidelineOffset = 16.dp.asGuidelineOffset
        public val verticalOffset: DebugGuidelineOffset = 16.dp.asGuidelineOffset
    }

    public object Ruler {
        public val step: DebugRulerMeasureUnit = 16.rulerDp()
        public val horizontalRulerHeight: Dp = 16.dp
        public val verticalRulerWidth: Dp = 16.dp
        public val tickColor: Color = Color(0xFF767680)
        public val minorTickSize: Dp = 1.5.dp
        public val majorTickSize: Dp = 4.dp
        public val tickStrokeWidth: Dp = 1.dp

        @Suppress("DEPRECATION")
        public val textStyle: TextStyle = TextStyle.Default.copy(
            color = tickColor,
            fontSize = 6.sp,
            lineHeight = 10.sp,
            textAlign = TextAlign.Center,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        )
        public val backgroundColor: Color = Color(0xBBFFFFFF)
        public val outlineColor: Color = Color(0xFF767680)
    }
}
