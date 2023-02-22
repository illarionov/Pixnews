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
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val AppStoreDistributionPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "AppStoreDistributionPlatform",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f,
    ).group(
        rotate = 0.0f,
        pivotX = 0.0f,
        pivotY = 0.0f,
        scaleX = 1.0f,
        scaleY = 1.0f,
        translationX = 0.0f,
        translationY = 0.0f,
        clipPathData = emptyList(),
    ) {
        path(
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
            moveTo(8.809f, 14.853f)
            lineTo(14.919f, 3.816f)
            curveTo(15.003f, 3.664f, 15.087f, 3.514f, 15.163f, 3.357f)
            curveTo(15.232f, 3.215f, 15.29f, 3.072f, 15.328f, 2.917f)
            curveTo(15.408f, 2.591f, 15.386f, 2.251f, 15.262f, 1.94f)
            curveTo(15.141f, 1.634f, 14.923f, 1.376f, 14.642f, 1.205f)
            curveTo(14.366f, 1.038f, 14.04f, 0.97f, 13.72f, 1.012f)
            curveTo(13.4f, 1.055f, 13.107f, 1.206f, 12.876f, 1.442f)
            curveTo(12.766f, 1.552f, 12.676f, 1.677f, 12.593f, 1.81f)
            curveTo(12.501f, 1.956f, 12.418f, 2.108f, 12.334f, 2.26f)
            lineTo(11.948f, 2.957f)
            lineTo(11.561f, 2.259f)
            curveTo(11.477f, 2.108f, 11.394f, 1.956f, 11.302f, 1.81f)
            curveTo(11.221f, 1.678f, 11.126f, 1.554f, 11.019f, 1.441f)
            curveTo(10.794f, 1.208f, 10.496f, 1.057f, 10.175f, 1.012f)
            curveTo(9.855f, 0.97f, 9.53f, 1.038f, 9.254f, 1.205f)
            curveTo(8.973f, 1.376f, 8.755f, 1.634f, 8.634f, 1.94f)
            curveTo(8.509f, 2.251f, 8.487f, 2.591f, 8.568f, 2.917f)
            curveTo(8.606f, 3.072f, 8.664f, 3.215f, 8.732f, 3.357f)
            curveTo(8.808f, 3.514f, 8.892f, 3.664f, 8.976f, 3.816f)
            lineTo(10.224f, 6.07f)
            lineTo(5.362f, 14.852f)
            horizontalLineTo(2.029f)
            curveTo(1.862f, 14.852f, 1.694f, 14.852f, 1.527f, 14.862f)
            curveTo(1.375f, 14.871f, 1.227f, 14.89f, 1.079f, 14.933f)
            curveTo(0.769f, 15.023f, 0.497f, 15.213f, 0.301f, 15.481f)
            curveTo(0.106f, 15.748f, 0.0f, 16.074f, 0.0f, 16.409f)
            curveTo(0.0f, 16.744f, 0.106f, 17.07f, 0.3f, 17.337f)
            curveTo(0.497f, 17.605f, 0.768f, 17.794f, 1.079f, 17.885f)
            curveTo(1.227f, 17.928f, 1.375f, 17.947f, 1.527f, 17.956f)
            curveTo(1.694f, 17.966f, 1.862f, 17.966f, 2.03f, 17.966f)
            horizontalLineTo(15.127f)
            curveTo(15.144f, 17.929f, 15.186f, 17.836f, 15.227f, 17.696f)
            curveTo(15.642f, 16.28f, 14.611f, 14.852f, 13.192f, 14.852f)
            lineTo(8.809f, 14.853f)
            close()
            moveTo(3.113f, 18.475f)
            lineTo(2.321f, 19.975f)
            curveTo(2.239f, 20.131f, 2.156f, 20.285f, 2.082f, 20.446f)
            curveTo(2.013f, 20.591f, 1.96f, 20.743f, 1.922f, 20.898f)
            curveTo(1.842f, 21.233f, 1.864f, 21.582f, 1.986f, 21.901f)
            curveTo(2.107f, 22.219f, 2.32f, 22.484f, 2.593f, 22.656f)
            curveTo(2.865f, 22.828f, 3.182f, 22.898f, 3.494f, 22.853f)
            curveTo(3.808f, 22.809f, 4.094f, 22.655f, 4.32f, 22.413f)
            curveTo(4.428f, 22.298f, 4.516f, 22.171f, 4.598f, 22.035f)
            curveTo(4.688f, 21.885f, 4.769f, 21.729f, 4.851f, 21.573f)
            lineTo(6.0f, 19.397f)
            curveTo(5.91f, 19.247f, 5.053f, 17.927f, 3.113f, 18.475f)
            close()
            moveTo(23.699f, 15.469f)
            curveTo(23.506f, 15.208f, 23.232f, 15.019f, 22.92f, 14.929f)
            curveTo(22.774f, 14.889f, 22.624f, 14.865f, 22.472f, 14.858f)
            curveTo(22.304f, 14.848f, 22.137f, 14.848f, 21.969f, 14.848f)
            horizontalLineTo(18.648f)
            lineTo(14.258f, 7.033f)
            curveTo(13.682f, 7.638f, 13.306f, 8.406f, 13.182f, 9.231f)
            curveTo(13.021f, 10.264f, 13.214f, 11.321f, 13.728f, 12.231f)
            lineTo(19.002f, 21.624f)
            curveTo(19.086f, 21.774f, 19.169f, 21.924f, 19.261f, 22.068f)
            curveTo(19.345f, 22.198f, 19.435f, 22.321f, 19.544f, 22.432f)
            curveTo(19.775f, 22.664f, 20.068f, 22.812f, 20.389f, 22.855f)
            curveTo(20.709f, 22.897f, 21.032f, 22.831f, 21.311f, 22.665f)
            curveTo(21.592f, 22.498f, 21.81f, 22.243f, 21.932f, 21.939f)
            curveTo(22.057f, 21.632f, 22.078f, 21.297f, 21.998f, 20.975f)
            curveTo(21.959f, 20.825f, 21.903f, 20.68f, 21.833f, 20.541f)
            curveTo(21.758f, 20.386f, 21.673f, 20.238f, 21.589f, 20.088f)
            lineTo(20.373f, 17.922f)
            horizontalLineTo(21.969f)
            curveTo(22.137f, 17.922f, 22.304f, 17.922f, 22.472f, 17.913f)
            curveTo(22.624f, 17.904f, 22.772f, 17.885f, 22.92f, 17.843f)
            curveTo(23.233f, 17.754f, 23.507f, 17.564f, 23.7f, 17.302f)
            curveTo(23.896f, 17.037f, 24.001f, 16.716f, 24.0f, 16.386f)
            curveTo(24.001f, 16.057f, 23.896f, 15.736f, 23.7f, 15.47f)
            lineTo(23.699f, 15.469f)
            close()
        }
    }
        .build().also { cache = it }
