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
package ru.pixnews.foundation.ui.assets.icons.externallink

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val TwitterLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "TwitterLink",
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
        moveTo(23.953f, 4.324f)
        curveTo(23.055f, 4.719f, 22.103f, 4.98f, 21.128f, 5.099f)
        curveTo(22.154f, 4.482f, 22.922f, 3.515f, 23.291f, 2.376f)
        curveTo(22.34f, 2.931f, 21.286f, 3.335f, 20.164f, 3.56f)
        curveTo(19.424f, 2.768f, 18.443f, 2.243f, 17.374f, 2.066f)
        curveTo(16.304f, 1.889f, 15.207f, 2.07f, 14.251f, 2.581f)
        curveTo(13.295f, 3.092f, 12.535f, 3.904f, 12.088f, 4.892f)
        curveTo(11.641f, 5.879f, 11.533f, 6.986f, 11.78f, 8.042f)
        curveTo(7.69f, 7.849f, 4.067f, 5.884f, 1.64f, 2.916f)
        curveTo(1.199f, 3.665f, 0.969f, 4.521f, 0.974f, 5.391f)
        curveTo(0.974f, 7.101f, 1.844f, 8.604f, 3.162f, 9.487f)
        curveTo(2.381f, 9.462f, 1.617f, 9.25f, 0.934f, 8.87f)
        verticalLineTo(8.931f)
        curveTo(0.934f, 10.067f, 1.326f, 11.168f, 2.046f, 12.048f)
        curveTo(2.765f, 12.928f, 3.766f, 13.532f, 4.88f, 13.757f)
        curveTo(4.158f, 13.951f, 3.402f, 13.98f, 2.668f, 13.842f)
        curveTo(2.984f, 14.821f, 3.598f, 15.675f, 4.423f, 16.288f)
        curveTo(5.248f, 16.901f, 6.244f, 17.24f, 7.272f, 17.26f)
        curveTo(5.532f, 18.625f, 3.382f, 19.367f, 1.17f, 19.365f)
        curveTo(0.78f, 19.365f, 0.391f, 19.341f, 0.0f, 19.298f)
        curveTo(2.256f, 20.742f, 4.879f, 21.508f, 7.557f, 21.507f)
        curveTo(16.61f, 21.507f, 21.555f, 14.01f, 21.555f, 7.522f)
        curveTo(21.555f, 7.312f, 21.555f, 7.102f, 21.54f, 6.892f)
        curveTo(22.506f, 6.196f, 23.339f, 5.333f, 24.0f, 4.344f)
        lineTo(23.953f, 4.324f)
        close()
    }.build().also { cache = it }
