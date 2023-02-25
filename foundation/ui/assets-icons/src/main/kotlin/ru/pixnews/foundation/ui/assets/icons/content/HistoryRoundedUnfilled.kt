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
internal val HistoryRoundedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "HistoryRoundedUnfilled400w0g24dp",
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
        moveTo(520.0f, 464.0f)
        lineTo(620.0f, 564.0f)
        quadTo(631.0f, 575.0f, 631.0f, 592.0f)
        quadTo(631.0f, 609.0f, 620.0f, 620.0f)
        quadTo(609.0f, 631.0f, 592.0f, 631.0f)
        quadTo(575.0f, 631.0f, 564.0f, 620.0f)
        lineTo(452.0f, 508.0f)
        quadTo(446.0f, 502.0f, 443.0f, 494.5f)
        quadTo(440.0f, 487.0f, 440.0f, 479.0f)
        lineTo(440.0f, 320.0f)
        quadTo(440.0f, 303.0f, 451.5f, 291.5f)
        quadTo(463.0f, 280.0f, 480.0f, 280.0f)
        quadTo(497.0f, 280.0f, 508.5f, 291.5f)
        quadTo(520.0f, 303.0f, 520.0f, 320.0f)
        lineTo(520.0f, 464.0f)
        close()
        moveTo(480.0f, 840.0f)
        quadTo(359.0f, 840.0f, 263.0f, 768.5f)
        quadTo(167.0f, 697.0f, 134.0f, 582.0f)
        quadTo(129.0f, 564.0f, 137.5f, 548.0f)
        quadTo(146.0f, 532.0f, 164.0f, 528.0f)
        quadTo(181.0f, 524.0f, 194.5f, 535.5f)
        quadTo(208.0f, 547.0f, 213.0f, 564.0f)
        quadTo(239.0f, 652.0f, 313.5f, 706.0f)
        quadTo(388.0f, 760.0f, 480.0f, 760.0f)
        quadTo(597.0f, 760.0f, 678.5f, 678.5f)
        quadTo(760.0f, 597.0f, 760.0f, 480.0f)
        quadTo(760.0f, 363.0f, 678.5f, 281.5f)
        quadTo(597.0f, 200.0f, 480.0f, 200.0f)
        quadTo(411.0f, 200.0f, 351.0f, 232.0f)
        quadTo(291.0f, 264.0f, 250.0f, 320.0f)
        lineTo(320.0f, 320.0f)
        quadTo(337.0f, 320.0f, 348.5f, 331.5f)
        quadTo(360.0f, 343.0f, 360.0f, 360.0f)
        quadTo(360.0f, 377.0f, 348.5f, 388.5f)
        quadTo(337.0f, 400.0f, 320.0f, 400.0f)
        lineTo(160.0f, 400.0f)
        quadTo(143.0f, 400.0f, 131.5f, 388.5f)
        quadTo(120.0f, 377.0f, 120.0f, 360.0f)
        lineTo(120.0f, 200.0f)
        quadTo(120.0f, 183.0f, 131.5f, 171.5f)
        quadTo(143.0f, 160.0f, 160.0f, 160.0f)
        quadTo(177.0f, 160.0f, 188.5f, 171.5f)
        quadTo(200.0f, 183.0f, 200.0f, 200.0f)
        lineTo(200.0f, 254.0f)
        quadTo(251.0f, 190.0f, 324.5f, 155.0f)
        quadTo(398.0f, 120.0f, 480.0f, 120.0f)
        quadTo(555.0f, 120.0f, 620.5f, 148.5f)
        quadTo(686.0f, 177.0f, 734.5f, 225.5f)
        quadTo(783.0f, 274.0f, 811.5f, 339.5f)
        quadTo(840.0f, 405.0f, 840.0f, 480.0f)
        quadTo(840.0f, 555.0f, 811.5f, 620.5f)
        quadTo(783.0f, 686.0f, 734.5f, 734.5f)
        quadTo(686.0f, 783.0f, 620.5f, 811.5f)
        quadTo(555.0f, 840.0f, 480.0f, 840.0f)
        close()
    }.build().also { cache = it }
