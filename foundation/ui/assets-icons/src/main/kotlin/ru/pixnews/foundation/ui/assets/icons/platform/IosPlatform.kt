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
package ru.pixnews.foundation.ui.assets.icons.platform

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val IosPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "IosPlatform",
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
        moveTo(15.5f, 1.0f)
        horizontalLineTo(7.5f)
        curveTo(6.12f, 1.0f, 5.0f, 2.12f, 5.0f, 3.5f)
        verticalLineTo(20.5f)
        curveTo(5.0f, 21.88f, 6.12f, 23.0f, 7.5f, 23.0f)
        horizontalLineTo(15.5f)
        curveTo(16.88f, 23.0f, 18.0f, 21.88f, 18.0f, 20.5f)
        verticalLineTo(3.5f)
        curveTo(18.0f, 2.12f, 16.88f, 1.0f, 15.5f, 1.0f)
        close()
        moveTo(11.5f, 22.0f)
        curveTo(10.67f, 22.0f, 10.0f, 21.33f, 10.0f, 20.5f)
        curveTo(10.0f, 19.67f, 10.67f, 19.0f, 11.5f, 19.0f)
        curveTo(12.33f, 19.0f, 13.0f, 19.67f, 13.0f, 20.5f)
        curveTo(13.0f, 21.33f, 12.33f, 22.0f, 11.5f, 22.0f)
        close()
        moveTo(16.0f, 18.0f)
        horizontalLineTo(7.0f)
        verticalLineTo(4.0f)
        horizontalLineTo(16.0f)
        verticalLineTo(18.0f)
        close()
    }.build().also { cache = it }
