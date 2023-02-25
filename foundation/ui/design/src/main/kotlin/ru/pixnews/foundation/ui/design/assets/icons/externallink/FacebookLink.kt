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
package ru.pixnews.foundation.ui.design.assets.icons.externallink

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val FacebookLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "FacebookLink",
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
        moveTo(23.0f, 12.066f)
        curveTo(23.0f, 5.992f, 18.075f, 1.066f, 12.0f, 1.066f)
        curveTo(5.925f, 1.066f, 1.0f, 5.992f, 1.0f, 12.066f)
        curveTo(1.0f, 17.557f, 5.022f, 22.108f, 10.281f, 22.933f)
        verticalLineTo(15.246f)
        horizontalLineTo(7.488f)
        verticalLineTo(12.066f)
        horizontalLineTo(10.281f)
        verticalLineTo(9.644f)
        curveTo(10.281f, 6.887f, 11.924f, 5.364f, 14.436f, 5.364f)
        curveTo(15.639f, 5.364f, 16.899f, 5.579f, 16.899f, 5.579f)
        verticalLineTo(8.286f)
        horizontalLineTo(15.511f)
        curveTo(14.144f, 8.286f, 13.718f, 9.134f, 13.718f, 10.004f)
        verticalLineTo(12.066f)
        horizontalLineTo(16.768f)
        lineTo(16.281f, 15.247f)
        horizontalLineTo(13.718f)
        verticalLineTo(22.934f)
        curveTo(18.978f, 22.108f, 23.0f, 17.556f, 23.0f, 12.066f)
        close()
    }.build().also { cache = it }
