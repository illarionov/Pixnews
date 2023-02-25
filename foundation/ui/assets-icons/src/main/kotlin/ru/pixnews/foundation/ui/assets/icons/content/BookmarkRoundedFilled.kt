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
package ru.pixnews.foundation.ui.assets.icons.content

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val BookmarkRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "BookmarkRoundedFilled400w0g24dp",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    ).path(
        fill = SolidColor(Color.Black),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(256.0f, 816.0f)
        quadTo(236.0f, 824.0f, 218.0f, 812.5f)
        quadTo(200.0f, 801.0f, 200.0f, 779.0f)
        lineTo(200.0f, 200.0f)
        quadTo(200.0f, 167.0f, 223.5f, 143.5f)
        quadTo(247.0f, 120.0f, 280.0f, 120.0f)
        lineTo(680.0f, 120.0f)
        quadTo(713.0f, 120.0f, 736.5f, 143.5f)
        quadTo(760.0f, 167.0f, 760.0f, 200.0f)
        lineTo(760.0f, 779.0f)
        quadTo(760.0f, 801.0f, 742.0f, 812.5f)
        quadTo(724.0f, 824.0f, 704.0f, 816.0f)
        lineTo(480.0f, 720.0f)
        lineTo(256.0f, 816.0f)
        close()
    }.build().also { cache = it }
