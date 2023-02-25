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
internal val NintendoSwitchPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "nintendo_switch",
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
        moveTo(13.994f, 23.0f)
        horizontalLineTo(17.364f)
        curveTo(20.458f, 23.0f, 23.0f, 20.458f, 23.0f, 17.364f)
        verticalLineTo(6.636f)
        curveTo(23.0f, 3.542f, 20.458f, 1.0f, 17.364f, 1.0f)
        horizontalLineTo(13.926f)
        curveTo(13.858f, 1.0f, 13.787f, 1.068f, 13.787f, 1.136f)
        verticalLineTo(22.864f)
        curveTo(13.787f, 22.932f, 13.855f, 23.0f, 13.994f, 23.0f)
        close()
        moveTo(18.188f, 10.901f)
        curveTo(19.426f, 10.901f, 20.386f, 11.932f, 20.386f, 13.099f)
        curveTo(20.386f, 14.338f, 19.355f, 15.298f, 18.188f, 15.298f)
        curveTo(16.949f, 15.298f, 15.989f, 14.338f, 15.989f, 13.099f)
        curveTo(15.917f, 11.864f, 16.949f, 10.901f, 18.188f, 10.901f)
        close()
        moveTo(11.449f, 1.0f)
        horizontalLineTo(6.636f)
        curveTo(3.542f, 1.0f, 1.0f, 3.542f, 1.0f, 6.636f)
        verticalLineTo(17.364f)
        curveTo(1.0f, 20.458f, 3.542f, 23.0f, 6.636f, 23.0f)
        horizontalLineTo(11.449f)
        curveTo(11.517f, 23.0f, 11.588f, 22.932f, 11.588f, 22.864f)
        verticalLineTo(1.136f)
        curveTo(11.588f, 1.068f, 11.52f, 1.0f, 11.449f, 1.0f)
        close()
        moveTo(9.869f, 21.213f)
        horizontalLineTo(6.636f)
        curveTo(6.13f, 21.214f, 5.629f, 21.115f, 5.162f, 20.922f)
        curveTo(4.695f, 20.729f, 4.27f, 20.445f, 3.912f, 20.088f)
        curveTo(3.555f, 19.73f, 3.271f, 19.305f, 3.078f, 18.838f)
        curveTo(2.885f, 18.371f, 2.786f, 17.87f, 2.787f, 17.364f)
        verticalLineTo(6.636f)
        curveTo(2.786f, 6.13f, 2.885f, 5.629f, 3.078f, 5.162f)
        curveTo(3.271f, 4.695f, 3.555f, 4.27f, 3.912f, 3.912f)
        curveTo(4.27f, 3.555f, 4.695f, 3.271f, 5.162f, 3.078f)
        curveTo(5.629f, 2.885f, 6.13f, 2.786f, 6.636f, 2.787f)
        horizontalLineTo(9.801f)
        lineTo(9.869f, 21.213f)
        close()
        moveTo(4.438f, 7.599f)
        curveTo(4.438f, 8.767f, 5.333f, 9.662f, 6.5f, 9.662f)
        curveTo(7.667f, 9.662f, 8.563f, 8.767f, 8.563f, 7.599f)
        curveTo(8.563f, 6.432f, 7.667f, 5.537f, 6.5f, 5.537f)
        curveTo(5.333f, 5.537f, 4.438f, 6.432f, 4.438f, 7.599f)
        close()
    }.build().also { cache = it }
