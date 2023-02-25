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
package ru.pixnews.foundation.ui.design.assets.icons.distribution

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val SteamDistributionPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "SteamDistributionPlatform",
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
        moveTo(10.095f, 16.935f)
        curveTo(10.473f, 16.027f, 10.045f, 14.986f, 9.132f, 14.608f)
        lineTo(7.683f, 14.009f)
        curveTo(8.243f, 13.797f, 8.877f, 13.788f, 9.471f, 14.033f)
        curveTo(10.07f, 14.283f, 10.532f, 14.75f, 10.782f, 15.344f)
        curveTo(11.028f, 15.943f, 11.028f, 16.601f, 10.777f, 17.195f)
        curveTo(10.262f, 18.428f, 8.842f, 19.013f, 7.605f, 18.497f)
        curveTo(7.035f, 18.261f, 6.603f, 17.829f, 6.358f, 17.309f)
        lineTo(7.757f, 17.888f)
        curveTo(8.671f, 18.271f, 9.717f, 17.844f, 10.095f, 16.935f)
        close()
        moveTo(20.643f, 1.0f)
        horizontalLineTo(3.357f)
        curveTo(2.056f, 1.0f, 1.0f, 2.056f, 1.0f, 3.357f)
        verticalLineTo(11.249f)
        lineTo(6.726f, 13.611f)
        curveTo(7.315f, 13.208f, 8.012f, 13.017f, 8.725f, 13.056f)
        lineTo(11.445f, 9.117f)
        verticalLineTo(9.063f)
        curveTo(11.445f, 6.696f, 13.375f, 4.767f, 15.747f, 4.767f)
        curveTo(18.119f, 4.767f, 20.049f, 6.696f, 20.049f, 9.063f)
        curveTo(20.049f, 11.479f, 18.04f, 13.419f, 15.649f, 13.36f)
        lineTo(11.769f, 16.125f)
        curveTo(11.848f, 18.016f, 10.34f, 19.504f, 8.543f, 19.504f)
        curveTo(6.981f, 19.504f, 5.67f, 18.389f, 5.375f, 16.916f)
        lineTo(1.0f, 15.104f)
        verticalLineTo(20.643f)
        curveTo(1.0f, 21.944f, 2.056f, 23.0f, 3.357f, 23.0f)
        horizontalLineTo(20.643f)
        curveTo(21.944f, 23.0f, 23.0f, 21.944f, 23.0f, 20.643f)
        verticalLineTo(3.357f)
        curveTo(23.0f, 2.056f, 21.944f, 1.0f, 20.643f, 1.0f)
        close()
        moveTo(15.747f, 11.926f)
        curveTo(14.166f, 11.926f, 12.879f, 10.645f, 12.879f, 9.063f)
        curveTo(12.879f, 7.482f, 14.166f, 6.2f, 15.747f, 6.2f)
        curveTo(17.328f, 6.2f, 18.615f, 7.487f, 18.615f, 9.063f)
        curveTo(18.615f, 10.64f, 17.328f, 11.926f, 15.747f, 11.926f)
        close()
        moveTo(15.752f, 11.209f)
        curveTo(16.94f, 11.209f, 17.908f, 10.247f, 17.908f, 9.058f)
        curveTo(17.908f, 7.87f, 16.945f, 6.908f, 15.752f, 6.908f)
        curveTo(14.563f, 6.908f, 13.596f, 7.87f, 13.596f, 9.058f)
        curveTo(13.596f, 10.247f, 14.563f, 11.209f, 15.752f, 11.209f)
        close()
    }.build().also { cache = it }
