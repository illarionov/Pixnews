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
package ru.pixnews.foundation.ui.design.assets.icons.social

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val ReplyRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "ReplyRoundedFilled400w0g24dp",
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
        moveTo(810.0f, 760.0f)
        quadTo(797.0f, 760.0f, 788.5f, 751.5f)
        quadTo(780.0f, 743.0f, 780.0f, 730.0f)
        lineTo(780.0f, 586.0f)
        quadTo(780.0f, 532.0f, 742.0f, 494.0f)
        quadTo(704.0f, 456.0f, 650.0f, 456.0f)
        lineTo(234.0f, 456.0f)
        lineTo(368.0f, 590.0f)
        quadTo(376.0f, 598.0f, 376.0f, 610.0f)
        quadTo(376.0f, 622.0f, 367.0f, 631.0f)
        quadTo(358.0f, 640.0f, 346.0f, 640.0f)
        quadTo(334.0f, 640.0f, 325.0f, 631.0f)
        lineTo(141.0f, 447.0f)
        quadTo(136.0f, 442.0f, 134.0f, 437.0f)
        quadTo(132.0f, 432.0f, 132.0f, 426.0f)
        quadTo(132.0f, 420.0f, 134.0f, 415.0f)
        quadTo(136.0f, 410.0f, 141.0f, 405.0f)
        lineTo(326.0f, 220.0f)
        quadTo(334.0f, 212.0f, 346.0f, 212.0f)
        quadTo(358.0f, 212.0f, 367.0f, 221.0f)
        quadTo(376.0f, 230.0f, 376.0f, 242.0f)
        quadTo(376.0f, 254.0f, 367.0f, 263.0f)
        lineTo(234.0f, 396.0f)
        lineTo(650.0f, 396.0f)
        quadTo(728.0f, 396.0f, 784.0f, 451.5f)
        quadTo(840.0f, 507.0f, 840.0f, 586.0f)
        lineTo(840.0f, 730.0f)
        quadTo(840.0f, 743.0f, 831.5f, 751.5f)
        quadTo(823.0f, 760.0f, 810.0f, 760.0f)
        close()
    }.build().also { cache = it }
