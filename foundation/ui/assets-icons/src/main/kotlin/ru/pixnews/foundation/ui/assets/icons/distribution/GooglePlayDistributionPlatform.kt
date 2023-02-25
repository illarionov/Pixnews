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
package ru.pixnews.foundation.ui.assets.icons.distribution

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val GooglePlayDistributionPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "GooglePlayDistributionPlatform",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f,
    ).path(
        fill = SolidColor(Color(0xff000000)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(2.073f, 21.481f)
        verticalLineTo(2.519f)
        curveTo(2.073f, 1.786f, 2.388f, 1.279f, 3.016f, 1.0f)
        lineTo(14.016f, 12.0f)
        lineTo(3.016f, 23.0f)
        curveTo(2.388f, 22.686f, 2.073f, 22.179f, 2.073f, 21.481f)
        close()
        moveTo(17.526f, 15.509f)
        lineTo(5.478f, 22.424f)
        lineTo(14.959f, 12.943f)
        lineTo(17.526f, 15.509f)
        close()
        moveTo(21.245f, 10.691f)
        curveTo(21.699f, 11.04f, 21.926f, 11.476f, 21.926f, 12.0f)
        curveTo(21.926f, 12.524f, 21.716f, 12.96f, 21.297f, 13.309f)
        lineTo(18.73f, 14.776f)
        lineTo(15.902f, 12.0f)
        lineTo(18.73f, 9.224f)
        lineTo(21.245f, 10.691f)
        close()
        moveTo(5.478f, 1.576f)
        lineTo(17.526f, 8.49f)
        lineTo(14.959f, 11.057f)
        lineTo(5.478f, 1.576f)
        close()
    }.build().also { cache = it }
