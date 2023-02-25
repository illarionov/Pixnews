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
internal val PlaystationPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "PlaystationPlatform",
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
        moveTo(23.025f, 17.843f)
        curveTo(22.569f, 18.546f, 21.46f, 19.045f, 21.46f, 19.045f)
        lineTo(13.189f, 22.688f)
        verticalLineTo(20.001f)
        lineTo(19.274f, 17.338f)
        curveTo(19.964f, 17.036f, 20.073f, 16.605f, 19.508f, 16.378f)
        curveTo(18.947f, 16.15f, 17.931f, 16.214f, 17.242f, 16.521f)
        lineTo(13.189f, 18.278f)
        verticalLineTo(15.487f)
        curveTo(14.124f, 15.101f, 15.088f, 14.814f, 16.241f, 14.655f)
        curveTo(17.891f, 14.433f, 19.907f, 14.685f, 21.492f, 15.422f)
        curveTo(23.275f, 16.115f, 23.476f, 17.14f, 23.025f, 17.843f)
        close()
        moveTo(13.975f, 13.265f)
        verticalLineTo(6.385f)
        curveTo(13.975f, 5.578f, 13.854f, 4.836f, 13.237f, 4.623f)
        curveTo(12.765f, 4.435f, 12.471f, 4.974f, 12.471f, 5.781f)
        verticalLineTo(23.0f)
        lineTo(8.688f, 21.525f)
        verticalLineTo(1.0f)
        curveTo(10.297f, 1.366f, 12.64f, 2.232f, 13.898f, 2.752f)
        curveTo(17.104f, 4.103f, 18.189f, 5.786f, 18.189f, 9.572f)
        curveTo(18.189f, 13.26f, 16.334f, 14.66f, 13.975f, 13.265f)
        close()
        moveTo(1.743f, 19.719f)
        curveTo(-0.088f, 19.085f, -0.394f, 17.764f, 0.441f, 17.006f)
        curveTo(1.211f, 16.303f, 2.526f, 15.774f, 2.526f, 15.774f)
        lineTo(7.95f, 13.408f)
        verticalLineTo(16.105f)
        lineTo(4.046f, 17.818f)
        curveTo(3.356f, 18.12f, 3.252f, 18.551f, 3.812f, 18.778f)
        curveTo(4.373f, 19.006f, 5.389f, 18.941f, 6.079f, 18.635f)
        lineTo(7.95f, 17.798f)
        verticalLineTo(20.213f)
        curveTo(5.869f, 20.674f, 3.861f, 20.575f, 1.743f, 19.719f)
        close()
    }.build().also { cache = it }
