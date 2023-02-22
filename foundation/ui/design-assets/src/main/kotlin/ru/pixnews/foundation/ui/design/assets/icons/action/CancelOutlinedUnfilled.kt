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
package ru.pixnews.foundation.ui.design.assets.icons.action

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val CancelOutlinedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "CancelOutlinedUnfilled400w0g24dp",
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
        moveTo(336.0f, 680.0f)
        lineTo(480.0f, 536.0f)
        lineTo(624.0f, 680.0f)
        lineTo(680.0f, 624.0f)
        lineTo(536.0f, 480.0f)
        lineTo(680.0f, 336.0f)
        lineTo(624.0f, 280.0f)
        lineTo(480.0f, 424.0f)
        lineTo(336.0f, 280.0f)
        lineTo(280.0f, 336.0f)
        lineTo(424.0f, 480.0f)
        lineTo(280.0f, 624.0f)
        lineTo(336.0f, 680.0f)
        close()
        moveTo(480.0f, 880.0f)
        quadTo(397.0f, 880.0f, 324.0f, 848.5f)
        quadTo(251.0f, 817.0f, 197.0f, 763.0f)
        quadTo(143.0f, 709.0f, 111.5f, 636.0f)
        quadTo(80.0f, 563.0f, 80.0f, 480.0f)
        quadTo(80.0f, 397.0f, 111.5f, 324.0f)
        quadTo(143.0f, 251.0f, 197.0f, 197.0f)
        quadTo(251.0f, 143.0f, 324.0f, 111.5f)
        quadTo(397.0f, 80.0f, 480.0f, 80.0f)
        quadTo(563.0f, 80.0f, 636.0f, 111.5f)
        quadTo(709.0f, 143.0f, 763.0f, 197.0f)
        quadTo(817.0f, 251.0f, 848.5f, 324.0f)
        quadTo(880.0f, 397.0f, 880.0f, 480.0f)
        quadTo(880.0f, 563.0f, 848.5f, 636.0f)
        quadTo(817.0f, 709.0f, 763.0f, 763.0f)
        quadTo(709.0f, 817.0f, 636.0f, 848.5f)
        quadTo(563.0f, 880.0f, 480.0f, 880.0f)
        close()
        moveTo(480.0f, 800.0f)
        quadTo(614.0f, 800.0f, 707.0f, 707.0f)
        quadTo(800.0f, 614.0f, 800.0f, 480.0f)
        quadTo(800.0f, 346.0f, 707.0f, 253.0f)
        quadTo(614.0f, 160.0f, 480.0f, 160.0f)
        quadTo(346.0f, 160.0f, 253.0f, 253.0f)
        quadTo(160.0f, 346.0f, 160.0f, 480.0f)
        quadTo(160.0f, 614.0f, 253.0f, 707.0f)
        quadTo(346.0f, 800.0f, 480.0f, 800.0f)
        close()
        moveTo(480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        quadTo(480.0f, 480.0f, 480.0f, 480.0f)
        close()
    }.build().also { cache = it }
