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
internal val DragHandleRounded400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "DragHandleRounded400w0g24dp",
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
        quadTo(183.0f, 440.0f, 171.5f, 428.5f)
        quadTo(160.0f, 417.0f, 160.0f, 400.0f)
        quadTo(160.0f, 383.0f, 171.5f, 371.5f)
        quadTo(183.0f, 360.0f, 200.0f, 360.0f)
        lineTo(760.0f, 360.0f)
        quadTo(777.0f, 360.0f, 788.5f, 371.5f)
        quadTo(800.0f, 383.0f, 800.0f, 400.0f)
        quadTo(800.0f, 417.0f, 788.5f, 428.5f)
        quadTo(777.0f, 440.0f, 760.0f, 440.0f)
        lineTo(200.0f, 440.0f)
        close()
        moveTo(200.0f, 600.0f)
        quadTo(183.0f, 600.0f, 171.5f, 588.5f)
        quadTo(160.0f, 577.0f, 160.0f, 560.0f)
        quadTo(160.0f, 543.0f, 171.5f, 531.5f)
        quadTo(183.0f, 520.0f, 200.0f, 520.0f)
        lineTo(760.0f, 520.0f)
        quadTo(777.0f, 520.0f, 788.5f, 531.5f)
        quadTo(800.0f, 543.0f, 800.0f, 560.0f)
        quadTo(800.0f, 577.0f, 788.5f, 588.5f)
        quadTo(777.0f, 600.0f, 760.0f, 600.0f)
        lineTo(200.0f, 600.0f)
        close()
    }.build().also { cache = it }
