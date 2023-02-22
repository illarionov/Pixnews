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
package ru.pixnews.foundation.ui.design.assets.icons.platform

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val MacosPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "MacosPlatform",
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
        moveTo(19.892f, 12.626f)
        curveTo(19.88f, 10.823f, 20.876f, 9.463f, 22.893f, 8.461f)
        curveTo(21.765f, 7.14f, 20.06f, 6.413f, 17.809f, 6.27f)
        curveTo(15.677f, 6.133f, 13.348f, 7.287f, 12.496f, 7.287f)
        curveTo(11.595f, 7.287f, 9.53f, 6.319f, 7.91f, 6.319f)
        curveTo(4.56f, 6.363f, 1.0f, 8.505f, 1.0f, 12.861f)
        curveTo(1.0f, 14.148f, 1.288f, 15.478f, 1.864f, 16.85f)
        curveTo(2.633f, 18.652f, 5.406f, 23.073f, 8.3f, 22.999f)
        curveTo(9.813f, 22.97f, 10.881f, 22.12f, 12.85f, 22.12f)
        curveTo(14.759f, 22.12f, 15.75f, 22.999f, 17.436f, 22.999f)
        curveTo(20.354f, 22.965f, 22.863f, 18.947f, 23.596f, 17.139f)
        curveTo(19.682f, 15.632f, 19.892f, 12.719f, 19.892f, 12.626f)
        close()
        moveTo(16.494f, 4.561f)
        curveTo(18.133f, 2.97f, 17.983f, 1.521f, 17.935f, 1.0f)
        curveTo(16.488f, 1.069f, 14.813f, 1.806f, 13.859f, 2.714f)
        curveTo(12.808f, 3.687f, 12.19f, 4.89f, 12.322f, 6.246f)
        curveTo(13.889f, 6.344f, 15.317f, 5.686f, 16.494f, 4.561f)
        close()
    }.build().also { cache = it }
