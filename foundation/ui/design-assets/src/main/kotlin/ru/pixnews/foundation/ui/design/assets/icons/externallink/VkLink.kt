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
internal val VkLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "VkLink",
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
        moveTo(15.377f, 1.0f)
        horizontalLineTo(8.623f)
        curveTo(2.459f, 1.0f, 1.0f, 2.459f, 1.0f, 8.623f)
        verticalLineTo(15.377f)
        curveTo(1.0f, 21.541f, 2.459f, 23.0f, 8.623f, 23.0f)
        horizontalLineTo(15.377f)
        curveTo(21.541f, 23.0f, 23.0f, 21.541f, 23.0f, 15.377f)
        verticalLineTo(8.623f)
        curveTo(23.0f, 2.459f, 21.525f, 1.0f, 15.377f, 1.0f)
        close()
        moveTo(18.761f, 16.696f)
        horizontalLineTo(17.163f)
        curveTo(16.558f, 16.696f, 16.371f, 16.215f, 15.283f, 15.113f)
        curveTo(14.337f, 14.196f, 13.918f, 14.073f, 13.685f, 14.073f)
        curveTo(13.358f, 14.073f, 13.265f, 14.166f, 13.265f, 14.616f)
        verticalLineTo(16.06f)
        curveTo(13.265f, 16.449f, 13.141f, 16.681f, 12.116f, 16.681f)
        curveTo(10.424f, 16.681f, 8.545f, 15.657f, 7.226f, 13.746f)
        curveTo(5.239f, 10.952f, 4.694f, 8.856f, 4.694f, 8.421f)
        curveTo(4.694f, 8.189f, 4.788f, 7.971f, 5.238f, 7.971f)
        horizontalLineTo(6.836f)
        curveTo(7.24f, 7.971f, 7.396f, 8.157f, 7.551f, 8.592f)
        curveTo(8.342f, 10.874f, 9.663f, 12.877f, 10.206f, 12.877f)
        curveTo(10.408f, 12.877f, 10.501f, 12.784f, 10.501f, 12.272f)
        verticalLineTo(9.911f)
        curveTo(10.439f, 8.824f, 9.864f, 8.731f, 9.864f, 8.343f)
        curveTo(9.864f, 8.156f, 10.02f, 7.97f, 10.267f, 7.97f)
        horizontalLineTo(12.783f)
        curveTo(13.125f, 7.97f, 13.248f, 8.156f, 13.248f, 8.56f)
        verticalLineTo(11.743f)
        curveTo(13.248f, 12.084f, 13.404f, 12.209f, 13.497f, 12.209f)
        curveTo(13.699f, 12.209f, 13.87f, 12.084f, 14.242f, 11.712f)
        curveTo(15.392f, 10.423f, 16.214f, 8.436f, 16.214f, 8.436f)
        curveTo(16.323f, 8.203f, 16.509f, 7.986f, 16.913f, 7.986f)
        horizontalLineTo(18.512f)
        curveTo(18.993f, 7.986f, 19.102f, 8.233f, 18.993f, 8.575f)
        curveTo(18.792f, 9.508f, 16.835f, 12.27f, 16.835f, 12.27f)
        curveTo(16.665f, 12.55f, 16.603f, 12.674f, 16.835f, 12.985f)
        curveTo(17.006f, 13.218f, 17.565f, 13.7f, 17.938f, 14.134f)
        curveTo(18.621f, 14.91f, 19.148f, 15.562f, 19.288f, 16.013f)
        curveTo(19.444f, 16.462f, 19.211f, 16.695f, 18.76f, 16.695f)
        lineTo(18.761f, 16.696f)
        close()
    }.build().also { cache = it }
