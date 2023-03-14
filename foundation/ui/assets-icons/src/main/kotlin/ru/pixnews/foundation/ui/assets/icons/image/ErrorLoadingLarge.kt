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
package ru.pixnews.foundation.ui.assets.icons.image

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val ErrorLoadingImagePlaceholderLarge: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "error_loading_large",
        defaultWidth = 161.dp,
        defaultHeight = 118.dp,
        viewportWidth = 161.0f,
        viewportHeight = 118.0f,
    ).path(
        fill = SolidColor(Color(0xff767680)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(48.83f, 59.5f)
        lineTo(80.0f, 5.67f)
        lineTo(111.17f, 59.5f)
        horizontalLineTo(48.83f)
        close()
        moveTo(58.61f, 53.83f)
        horizontalLineTo(101.39f)
        lineTo(80.0f, 17.0f)
        lineTo(58.61f, 53.83f)
        close()
        moveTo(80.0f, 51.0f)
        curveTo(80.8f, 51.0f, 81.48f, 50.73f, 82.02f, 50.18f)
        curveTo(82.56f, 49.64f, 82.83f, 48.97f, 82.83f, 48.17f)
        curveTo(82.83f, 47.36f, 82.56f, 46.69f, 82.02f, 46.15f)
        curveTo(81.48f, 45.61f, 80.8f, 45.33f, 80.0f, 45.33f)
        curveTo(79.2f, 45.33f, 78.52f, 45.61f, 77.98f, 46.15f)
        curveTo(77.44f, 46.69f, 77.17f, 47.36f, 77.17f, 48.17f)
        curveTo(77.17f, 48.97f, 77.44f, 49.64f, 77.98f, 50.18f)
        curveTo(78.52f, 50.73f, 79.2f, 51.0f, 80.0f, 51.0f)
        close()
        moveTo(77.17f, 42.5f)
        horizontalLineTo(82.83f)
        verticalLineTo(28.33f)
        horizontalLineTo(77.17f)
        verticalLineTo(42.5f)
        close()
    }.build().also { cache = it }
