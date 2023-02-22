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
internal val XboxPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "XboxPlatform",
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
        moveTo(17.052f, 14.759f)
        curveTo(19.017f, 17.167f, 19.922f, 19.14f, 19.465f, 20.023f)
        curveTo(19.115f, 20.692f, 16.95f, 22.001f, 15.357f, 22.502f)
        curveTo(14.044f, 22.914f, 12.323f, 23.092f, 10.904f, 22.954f)
        curveTo(9.209f, 22.79f, 7.492f, 22.183f, 6.02f, 21.225f)
        curveTo(4.782f, 20.417f, 4.502f, 20.085f, 4.502f, 19.424f)
        curveTo(4.502f, 18.098f, 5.962f, 15.774f, 8.459f, 13.122f)
        curveTo(9.879f, 11.619f, 11.853f, 9.854f, 12.07f, 9.903f)
        curveTo(12.487f, 9.996f, 15.81f, 13.233f, 17.052f, 14.759f)
        close()
        moveTo(9.009f, 7.025f)
        curveTo(7.692f, 5.832f, 6.432f, 4.634f, 5.177f, 4.213f)
        curveTo(4.502f, 3.987f, 4.454f, 4.0f, 3.904f, 4.572f)
        curveTo(2.608f, 5.92f, 1.53f, 8.107f, 1.229f, 10.0f)
        curveTo(0.989f, 11.517f, 0.958f, 11.943f, 1.042f, 12.683f)
        curveTo(1.291f, 14.923f, 1.81f, 16.471f, 2.839f, 18.045f)
        curveTo(3.26f, 18.692f, 3.376f, 18.812f, 3.252f, 18.484f)
        curveTo(3.065f, 17.996f, 3.238f, 16.821f, 3.673f, 15.646f)
        curveTo(4.307f, 13.916f, 6.064f, 10.639f, 9.009f, 7.025f)
        close()
        moveTo(22.832f, 9.841f)
        curveTo(22.082f, 6.293f, 19.838f, 4.062f, 19.523f, 4.062f)
        curveTo(19.199f, 4.062f, 18.449f, 4.35f, 17.926f, 4.679f)
        curveTo(16.892f, 5.322f, 16.107f, 6.071f, 15.073f, 7.02f)
        curveTo(16.954f, 9.384f, 19.607f, 13.202f, 20.525f, 15.992f)
        curveTo(20.827f, 16.91f, 20.955f, 17.814f, 20.853f, 18.311f)
        curveTo(20.778f, 18.688f, 20.778f, 18.688f, 20.916f, 18.515f)
        curveTo(21.186f, 18.174f, 21.798f, 17.127f, 22.042f, 16.586f)
        curveTo(22.371f, 15.867f, 22.708f, 14.803f, 22.867f, 13.983f)
        curveTo(23.058f, 12.985f, 23.04f, 10.843f, 22.832f, 9.841f)
        close()
        moveTo(6.911f, 2.554f)
        curveTo(9.027f, 2.443f, 11.778f, 4.084f, 11.981f, 4.124f)
        curveTo(12.013f, 4.129f, 12.443f, 3.938f, 12.94f, 3.694f)
        curveTo(15.774f, 2.315f, 17.11f, 2.55f, 17.704f, 2.576f)
        curveTo(14.869f, 0.834f, 10.93f, 0.359f, 7.328f, 2.058f)
        curveTo(6.29f, 2.55f, 6.264f, 2.585f, 6.911f, 2.554f)
        close()
    }.build().also { cache = it }
