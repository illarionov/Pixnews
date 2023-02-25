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
package ru.pixnews.foundation.ui.assets.icons.content

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val GridViewRoundedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "GridViewRoundedUnfilled400w0g24dp",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    ).path(
        fill = SolidColor(Color.Black),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(200.0f, 440.0f)
        quadTo(167.0f, 440.0f, 143.5f, 416.5f)
        quadTo(120.0f, 393.0f, 120.0f, 360.0f)
        lineTo(120.0f, 200.0f)
        quadTo(120.0f, 167.0f, 143.5f, 143.5f)
        quadTo(167.0f, 120.0f, 200.0f, 120.0f)
        lineTo(360.0f, 120.0f)
        quadTo(393.0f, 120.0f, 416.5f, 143.5f)
        quadTo(440.0f, 167.0f, 440.0f, 200.0f)
        lineTo(440.0f, 360.0f)
        quadTo(440.0f, 393.0f, 416.5f, 416.5f)
        quadTo(393.0f, 440.0f, 360.0f, 440.0f)
        lineTo(200.0f, 440.0f)
        close()
        moveTo(200.0f, 840.0f)
        quadTo(167.0f, 840.0f, 143.5f, 816.5f)
        quadTo(120.0f, 793.0f, 120.0f, 760.0f)
        lineTo(120.0f, 600.0f)
        quadTo(120.0f, 567.0f, 143.5f, 543.5f)
        quadTo(167.0f, 520.0f, 200.0f, 520.0f)
        lineTo(360.0f, 520.0f)
        quadTo(393.0f, 520.0f, 416.5f, 543.5f)
        quadTo(440.0f, 567.0f, 440.0f, 600.0f)
        lineTo(440.0f, 760.0f)
        quadTo(440.0f, 793.0f, 416.5f, 816.5f)
        quadTo(393.0f, 840.0f, 360.0f, 840.0f)
        lineTo(200.0f, 840.0f)
        close()
        moveTo(600.0f, 440.0f)
        quadTo(567.0f, 440.0f, 543.5f, 416.5f)
        quadTo(520.0f, 393.0f, 520.0f, 360.0f)
        lineTo(520.0f, 200.0f)
        quadTo(520.0f, 167.0f, 543.5f, 143.5f)
        quadTo(567.0f, 120.0f, 600.0f, 120.0f)
        lineTo(760.0f, 120.0f)
        quadTo(793.0f, 120.0f, 816.5f, 143.5f)
        quadTo(840.0f, 167.0f, 840.0f, 200.0f)
        lineTo(840.0f, 360.0f)
        quadTo(840.0f, 393.0f, 816.5f, 416.5f)
        quadTo(793.0f, 440.0f, 760.0f, 440.0f)
        lineTo(600.0f, 440.0f)
        close()
        moveTo(600.0f, 840.0f)
        quadTo(567.0f, 840.0f, 543.5f, 816.5f)
        quadTo(520.0f, 793.0f, 520.0f, 760.0f)
        lineTo(520.0f, 600.0f)
        quadTo(520.0f, 567.0f, 543.5f, 543.5f)
        quadTo(567.0f, 520.0f, 600.0f, 520.0f)
        lineTo(760.0f, 520.0f)
        quadTo(793.0f, 520.0f, 816.5f, 543.5f)
        quadTo(840.0f, 567.0f, 840.0f, 600.0f)
        lineTo(840.0f, 760.0f)
        quadTo(840.0f, 793.0f, 816.5f, 816.5f)
        quadTo(793.0f, 840.0f, 760.0f, 840.0f)
        lineTo(600.0f, 840.0f)
        close()
        moveTo(200.0f, 360.0f)
        lineTo(360.0f, 360.0f)
        lineTo(360.0f, 200.0f)
        lineTo(200.0f, 200.0f)
        lineTo(200.0f, 360.0f)
        close()
        moveTo(600.0f, 360.0f)
        lineTo(760.0f, 360.0f)
        lineTo(760.0f, 200.0f)
        lineTo(600.0f, 200.0f)
        lineTo(600.0f, 360.0f)
        close()
        moveTo(600.0f, 760.0f)
        lineTo(760.0f, 760.0f)
        lineTo(760.0f, 600.0f)
        lineTo(600.0f, 600.0f)
        lineTo(600.0f, 760.0f)
        close()
        moveTo(200.0f, 760.0f)
        lineTo(360.0f, 760.0f)
        lineTo(360.0f, 600.0f)
        lineTo(200.0f, 600.0f)
        lineTo(200.0f, 760.0f)
        close()
        moveTo(600.0f, 360.0f)
        lineTo(600.0f, 360.0f)
        lineTo(600.0f, 360.0f)
        lineTo(600.0f, 360.0f)
        lineTo(600.0f, 360.0f)
        close()
        moveTo(600.0f, 600.0f)
        lineTo(600.0f, 600.0f)
        lineTo(600.0f, 600.0f)
        lineTo(600.0f, 600.0f)
        lineTo(600.0f, 600.0f)
        close()
        moveTo(360.0f, 600.0f)
        lineTo(360.0f, 600.0f)
        lineTo(360.0f, 600.0f)
        lineTo(360.0f, 600.0f)
        lineTo(360.0f, 600.0f)
        close()
        moveTo(360.0f, 360.0f)
        lineTo(360.0f, 360.0f)
        lineTo(360.0f, 360.0f)
        lineTo(360.0f, 360.0f)
        lineTo(360.0f, 360.0f)
        close()
    }.build().also { cache = it }
