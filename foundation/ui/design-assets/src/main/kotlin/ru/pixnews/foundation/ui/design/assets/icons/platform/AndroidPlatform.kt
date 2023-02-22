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
internal val AndroidPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "AndroidPlatform",
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
        moveTo(4.964f, 9.478f)
        verticalLineTo(15.166f)
        curveTo(4.964f, 15.923f, 4.342f, 16.527f, 3.549f, 16.527f)
        curveTo(2.762f, 16.527f, 2.0f, 15.918f, 2.0f, 15.166f)
        verticalLineTo(9.478f)
        curveTo(2.0f, 8.736f, 2.762f, 8.127f, 3.549f, 8.127f)
        curveTo(4.326f, 8.127f, 4.964f, 8.736f, 4.964f, 9.478f)
        close()
        moveTo(5.52f, 17.19f)
        curveTo(5.52f, 17.996f, 6.2f, 18.644f, 7.044f, 18.644f)
        horizontalLineTo(8.068f)
        lineTo(8.083f, 21.646f)
        curveTo(8.083f, 23.459f, 10.924f, 23.444f, 10.924f, 21.646f)
        verticalLineTo(18.644f)
        horizontalLineTo(12.839f)
        verticalLineTo(21.646f)
        curveTo(12.839f, 23.449f, 15.695f, 23.454f, 15.695f, 21.646f)
        verticalLineTo(18.644f)
        horizontalLineTo(16.734f)
        curveTo(17.568f, 18.644f, 18.247f, 17.996f, 18.247f, 17.19f)
        verticalLineTo(8.377f)
        horizontalLineTo(5.52f)
        verticalLineTo(17.19f)
        close()
        moveTo(18.283f, 7.901f)
        horizontalLineTo(5.464f)
        curveTo(5.464f, 5.798f, 6.781f, 3.971f, 8.737f, 3.018f)
        lineTo(7.754f, 1.283f)
        curveTo(7.61f, 1.043f, 7.975f, 0.89f, 8.099f, 1.097f)
        lineTo(9.097f, 2.846f)
        curveTo(10.893f, 2.084f, 12.957f, 2.124f, 14.671f, 2.846f)
        lineTo(15.664f, 1.102f)
        curveTo(15.792f, 0.89f, 16.153f, 1.048f, 16.009f, 1.288f)
        lineTo(15.026f, 3.018f)
        curveTo(16.966f, 3.971f, 18.283f, 5.798f, 18.283f, 7.901f)
        close()
        moveTo(9.498f, 5.174f)
        curveTo(9.498f, 4.894f, 9.262f, 4.658f, 8.958f, 4.658f)
        curveTo(8.665f, 4.658f, 8.433f, 4.894f, 8.433f, 5.174f)
        curveTo(8.433f, 5.454f, 8.67f, 5.69f, 8.958f, 5.69f)
        curveTo(9.262f, 5.69f, 9.498f, 5.454f, 9.498f, 5.174f)
        close()
        moveTo(15.335f, 5.174f)
        curveTo(15.335f, 4.894f, 15.098f, 4.658f, 14.81f, 4.658f)
        curveTo(14.506f, 4.658f, 14.269f, 4.894f, 14.269f, 5.174f)
        curveTo(14.269f, 5.454f, 14.506f, 5.69f, 14.81f, 5.69f)
        curveTo(15.098f, 5.69f, 15.335f, 5.454f, 15.335f, 5.174f)
        close()
        moveTo(20.213f, 8.127f)
        curveTo(19.436f, 8.127f, 18.798f, 8.721f, 18.798f, 9.478f)
        verticalLineTo(15.166f)
        curveTo(18.798f, 15.923f, 19.436f, 16.527f, 20.213f, 16.527f)
        curveTo(21.006f, 16.527f, 21.763f, 15.918f, 21.763f, 15.166f)
        verticalLineTo(9.478f)
        curveTo(21.763f, 8.721f, 21.001f, 8.127f, 20.213f, 8.127f)
        close()
    }.build().also { cache = it }
