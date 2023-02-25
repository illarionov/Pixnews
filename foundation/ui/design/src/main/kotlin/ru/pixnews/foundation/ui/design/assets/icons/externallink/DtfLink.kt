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
internal val DtfLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "DtfLink",
        defaultWidth = 56.dp,
        defaultHeight = 24.dp,
        viewportWidth = 56.0f,
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
        moveTo(13.906f, 4.0f)
        horizontalLineTo(4.0f)
        verticalLineTo(19.771f)
        horizontalLineTo(13.906f)
        curveTo(17.344f, 19.771f, 20.14f, 17.039f, 20.14f, 13.68f)
        verticalLineTo(10.091f)
        curveTo(20.14f, 6.732f, 17.344f, 4.0f, 13.906f, 4.0f)
        close()
        moveTo(16.08f, 13.68f)
        curveTo(16.08f, 14.851f, 15.106f, 15.805f, 13.906f, 15.805f)
        horizontalLineTo(8.057f)
        verticalLineTo(7.965f)
        horizontalLineTo(13.906f)
        curveTo(15.104f, 7.965f, 16.08f, 8.917f, 16.08f, 10.091f)
        verticalLineTo(13.68f)
        horizontalLineTo(16.08f)
        close()
        moveTo(21.285f, 7.965f)
        horizontalLineTo(26.952f)
        verticalLineTo(19.739f)
        horizontalLineTo(31.01f)
        verticalLineTo(7.965f)
        horizontalLineTo(36.677f)
        verticalLineTo(4.0f)
        horizontalLineTo(21.285f)
        verticalLineTo(7.965f)
        close()
        moveTo(52.0f, 7.965f)
        verticalLineTo(4.0f)
        horizontalLineTo(38.895f)
        verticalLineTo(19.739f)
        horizontalLineTo(42.953f)
        verticalLineTo(15.102f)
        horizontalLineTo(49.798f)
        verticalLineTo(11.138f)
        horizontalLineTo(42.953f)
        verticalLineTo(7.965f)
    }.build().also { cache = it }
