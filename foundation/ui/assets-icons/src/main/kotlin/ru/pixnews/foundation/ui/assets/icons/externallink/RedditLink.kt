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
internal val RedditLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "RedditLink",
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
        moveTo(12.0f, 1.0f)
        curveTo(9.083f, 1.0f, 6.285f, 2.159f, 4.222f, 4.222f)
        curveTo(2.159f, 6.285f, 1.0f, 9.083f, 1.0f, 12.0f)
        curveTo(1.0f, 14.917f, 2.159f, 17.715f, 4.222f, 19.778f)
        curveTo(6.285f, 21.841f, 9.083f, 23.0f, 12.0f, 23.0f)
        curveTo(14.917f, 23.0f, 17.715f, 21.841f, 19.778f, 19.778f)
        curveTo(21.841f, 17.715f, 23.0f, 14.917f, 23.0f, 12.0f)
        curveTo(23.0f, 9.083f, 21.841f, 6.285f, 19.778f, 4.222f)
        curveTo(17.715f, 2.159f, 14.917f, 1.0f, 12.0f, 1.0f)
        verticalLineTo(1.0f)
        close()
        moveTo(16.593f, 5.349f)
        curveTo(17.223f, 5.349f, 17.738f, 5.863f, 17.738f, 6.494f)
        curveTo(17.735f, 6.791f, 17.617f, 7.075f, 17.407f, 7.287f)
        curveTo(17.198f, 7.498f, 16.916f, 7.62f, 16.618f, 7.627f)
        curveTo(16.321f, 7.634f, 16.033f, 7.524f, 15.814f, 7.322f)
        curveTo(15.596f, 7.121f, 15.465f, 6.842f, 15.448f, 6.545f)
        lineTo(13.068f, 6.043f)
        lineTo(12.335f, 9.478f)
        curveTo(14.007f, 9.542f, 15.525f, 10.058f, 16.619f, 10.842f)
        curveTo(16.901f, 10.559f, 17.288f, 10.392f, 17.726f, 10.392f)
        curveTo(18.613f, 10.392f, 19.333f, 11.113f, 19.333f, 12.0f)
        curveTo(19.333f, 12.656f, 18.935f, 13.222f, 18.407f, 13.479f)
        curveTo(18.434f, 13.637f, 18.447f, 13.797f, 18.446f, 13.956f)
        curveTo(18.446f, 16.426f, 15.577f, 18.42f, 12.026f, 18.42f)
        curveTo(8.474f, 18.42f, 5.605f, 16.426f, 5.605f, 13.956f)
        curveTo(5.605f, 13.788f, 5.619f, 13.621f, 5.645f, 13.467f)
        curveTo(5.361f, 13.341f, 5.12f, 13.135f, 4.951f, 12.875f)
        curveTo(4.782f, 12.614f, 4.692f, 12.311f, 4.692f, 12.0f)
        curveTo(4.692f, 11.113f, 5.413f, 10.392f, 6.3f, 10.392f)
        curveTo(6.725f, 10.392f, 7.123f, 10.572f, 7.407f, 10.841f)
        curveTo(8.513f, 10.032f, 10.045f, 9.531f, 11.755f, 9.478f)
        lineTo(12.566f, 5.645f)
        curveTo(12.586f, 5.571f, 12.632f, 5.507f, 12.695f, 5.464f)
        curveTo(12.761f, 5.426f, 12.838f, 5.412f, 12.913f, 5.426f)
        lineTo(15.577f, 5.991f)
        curveTo(15.667f, 5.798f, 15.81f, 5.635f, 15.991f, 5.521f)
        curveTo(16.17f, 5.407f, 16.379f, 5.347f, 16.593f, 5.349f)
        close()
        moveTo(9.479f, 12.0f)
        curveTo(8.848f, 12.0f, 8.333f, 12.515f, 8.333f, 13.146f)
        curveTo(8.333f, 13.776f, 8.848f, 14.29f, 9.479f, 14.29f)
        curveTo(10.109f, 14.29f, 10.623f, 13.776f, 10.623f, 13.145f)
        curveTo(10.623f, 12.514f, 10.109f, 12.0f, 9.478f, 12.0f)
        horizontalLineTo(9.479f)
        close()
        moveTo(14.521f, 12.0f)
        curveTo(13.891f, 12.0f, 13.377f, 12.514f, 13.377f, 13.146f)
        curveTo(13.377f, 13.776f, 13.891f, 14.29f, 14.522f, 14.29f)
        curveTo(15.152f, 14.29f, 15.667f, 13.776f, 15.667f, 13.145f)
        curveTo(15.667f, 12.515f, 15.151f, 12.0f, 14.521f, 12.0f)
        close()
        moveTo(9.51f, 15.658f)
        curveTo(9.431f, 15.657f, 9.355f, 15.688f, 9.299f, 15.744f)
        curveTo(9.243f, 15.8f, 9.212f, 15.877f, 9.212f, 15.956f)
        curveTo(9.212f, 16.035f, 9.243f, 16.111f, 9.299f, 16.168f)
        curveTo(10.07f, 16.94f, 11.576f, 17.005f, 12.013f, 17.005f)
        curveTo(12.45f, 17.005f, 13.942f, 16.954f, 14.727f, 16.168f)
        curveTo(14.781f, 16.112f, 14.813f, 16.038f, 14.818f, 15.961f)
        curveTo(14.822f, 15.883f, 14.8f, 15.806f, 14.754f, 15.744f)
        curveTo(14.697f, 15.688f, 14.621f, 15.656f, 14.541f, 15.656f)
        curveTo(14.461f, 15.656f, 14.385f, 15.688f, 14.328f, 15.744f)
        curveTo(13.827f, 16.232f, 12.785f, 16.413f, 12.026f, 16.413f)
        curveTo(11.267f, 16.413f, 10.212f, 16.233f, 9.723f, 15.744f)
        curveTo(9.695f, 15.716f, 9.662f, 15.694f, 9.625f, 15.679f)
        curveTo(9.589f, 15.664f, 9.55f, 15.656f, 9.51f, 15.657f)
        verticalLineTo(15.658f)
        close()
    }.build().also { cache = it }
