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
internal val WebPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "web",
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
        moveTo(11.989f, 1.0f)
        curveTo(5.917f, 1.0f, 1.0f, 5.928f, 1.0f, 12.0f)
        curveTo(1.0f, 18.072f, 5.917f, 23.0f, 11.989f, 23.0f)
        curveTo(18.072f, 23.0f, 23.0f, 18.072f, 23.0f, 12.0f)
        curveTo(23.0f, 5.928f, 18.072f, 1.0f, 11.989f, 1.0f)
        close()
        moveTo(19.612f, 7.6f)
        horizontalLineTo(16.367f)
        curveTo(16.015f, 6.225f, 15.509f, 4.905f, 14.849f, 3.684f)
        curveTo(16.873f, 4.377f, 18.556f, 5.785f, 19.612f, 7.6f)
        close()
        moveTo(12.0f, 3.244f)
        curveTo(12.913f, 4.564f, 13.628f, 6.027f, 14.101f, 7.6f)
        horizontalLineTo(9.899f)
        curveTo(10.372f, 6.027f, 11.087f, 4.564f, 12.0f, 3.244f)
        close()
        moveTo(3.486f, 14.2f)
        curveTo(3.31f, 13.496f, 3.2f, 12.759f, 3.2f, 12.0f)
        curveTo(3.2f, 11.241f, 3.31f, 10.504f, 3.486f, 9.8f)
        horizontalLineTo(7.204f)
        curveTo(7.116f, 10.526f, 7.05f, 11.252f, 7.05f, 12.0f)
        curveTo(7.05f, 12.748f, 7.116f, 13.474f, 7.204f, 14.2f)
        horizontalLineTo(3.486f)
        close()
        moveTo(4.388f, 16.4f)
        horizontalLineTo(7.633f)
        curveTo(7.985f, 17.775f, 8.491f, 19.095f, 9.151f, 20.316f)
        curveTo(7.127f, 19.623f, 5.444f, 18.226f, 4.388f, 16.4f)
        close()
        moveTo(7.633f, 7.6f)
        horizontalLineTo(4.388f)
        curveTo(5.444f, 5.774f, 7.127f, 4.377f, 9.151f, 3.684f)
        curveTo(8.491f, 4.905f, 7.985f, 6.225f, 7.633f, 7.6f)
        close()
        moveTo(12.0f, 20.756f)
        curveTo(11.087f, 19.436f, 10.372f, 17.973f, 9.899f, 16.4f)
        horizontalLineTo(14.101f)
        curveTo(13.628f, 17.973f, 12.913f, 19.436f, 12.0f, 20.756f)
        close()
        moveTo(14.574f, 14.2f)
        horizontalLineTo(9.426f)
        curveTo(9.327f, 13.474f, 9.25f, 12.748f, 9.25f, 12.0f)
        curveTo(9.25f, 11.252f, 9.327f, 10.515f, 9.426f, 9.8f)
        horizontalLineTo(14.574f)
        curveTo(14.673f, 10.515f, 14.75f, 11.252f, 14.75f, 12.0f)
        curveTo(14.75f, 12.748f, 14.673f, 13.474f, 14.574f, 14.2f)
        close()
        moveTo(14.849f, 20.316f)
        curveTo(15.509f, 19.095f, 16.015f, 17.775f, 16.367f, 16.4f)
        horizontalLineTo(19.612f)
        curveTo(18.556f, 18.215f, 16.873f, 19.623f, 14.849f, 20.316f)
        close()
        moveTo(16.796f, 14.2f)
        curveTo(16.884f, 13.474f, 16.95f, 12.748f, 16.95f, 12.0f)
        curveTo(16.95f, 11.252f, 16.884f, 10.526f, 16.796f, 9.8f)
        horizontalLineTo(20.514f)
        curveTo(20.69f, 10.504f, 20.8f, 11.241f, 20.8f, 12.0f)
        curveTo(20.8f, 12.759f, 20.69f, 13.496f, 20.514f, 14.2f)
        horizontalLineTo(16.796f)
        close()
    }.build().also { cache = it }
