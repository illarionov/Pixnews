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
internal val DiscordLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "DiscordLink",
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
        moveTo(19.538f, 2.0f)
        curveTo(20.826f, 2.0f, 21.866f, 3.042f, 21.927f, 4.269f)
        verticalLineTo(24.0f)
        lineTo(19.473f, 21.916f)
        lineTo(18.126f, 20.69f)
        lineTo(16.656f, 19.408f)
        lineTo(17.27f, 21.43f)
        horizontalLineTo(4.402f)
        curveTo(3.116f, 21.43f, 2.073f, 20.453f, 2.073f, 19.16f)
        verticalLineTo(4.273f)
        curveTo(2.073f, 3.047f, 3.118f, 2.003f, 4.406f, 2.003f)
        horizontalLineTo(19.531f)
        lineTo(19.538f, 2.0f)
        close()
        moveTo(13.929f, 7.209f)
        horizontalLineTo(13.902f)
        lineTo(13.717f, 7.393f)
        curveTo(15.617f, 7.943f, 16.536f, 8.802f, 16.536f, 8.802f)
        curveTo(15.312f, 8.189f, 14.208f, 7.883f, 13.104f, 7.759f)
        curveTo(12.307f, 7.636f, 11.509f, 7.701f, 10.836f, 7.759f)
        horizontalLineTo(10.652f)
        curveTo(10.222f, 7.759f, 9.305f, 7.943f, 8.076f, 8.433f)
        curveTo(7.648f, 8.619f, 7.403f, 8.741f, 7.403f, 8.741f)
        curveTo(7.403f, 8.741f, 8.321f, 7.823f, 10.345f, 7.332f)
        lineTo(10.222f, 7.208f)
        curveTo(10.222f, 7.208f, 8.689f, 7.15f, 7.034f, 8.373f)
        curveTo(7.034f, 8.373f, 5.38f, 11.255f, 5.38f, 14.808f)
        curveTo(5.38f, 14.808f, 6.296f, 16.403f, 8.811f, 16.463f)
        curveTo(8.811f, 16.463f, 9.177f, 15.975f, 9.549f, 15.545f)
        curveTo(8.137f, 15.116f, 7.587f, 14.258f, 7.587f, 14.258f)
        curveTo(7.587f, 14.258f, 7.71f, 14.318f, 7.894f, 14.441f)
        horizontalLineTo(7.949f)
        curveTo(7.977f, 14.441f, 7.989f, 14.455f, 8.004f, 14.469f)
        verticalLineTo(14.474f)
        curveTo(8.019f, 14.489f, 8.032f, 14.502f, 8.059f, 14.502f)
        curveTo(8.362f, 14.626f, 8.664f, 14.749f, 8.912f, 14.868f)
        curveTo(9.441f, 15.095f, 9.995f, 15.26f, 10.562f, 15.359f)
        curveTo(11.414f, 15.483f, 12.391f, 15.543f, 13.504f, 15.359f)
        curveTo(14.054f, 15.236f, 14.604f, 15.115f, 15.154f, 14.869f)
        curveTo(15.512f, 14.686f, 15.952f, 14.502f, 16.435f, 14.193f)
        curveTo(16.435f, 14.193f, 15.885f, 15.052f, 14.413f, 15.481f)
        curveTo(14.716f, 15.908f, 15.142f, 16.397f, 15.142f, 16.397f)
        curveTo(17.657f, 16.342f, 18.635f, 14.747f, 18.69f, 14.815f)
        curveTo(18.69f, 11.267f, 17.026f, 8.38f, 17.026f, 8.38f)
        curveTo(15.527f, 7.267f, 14.125f, 7.225f, 13.877f, 7.225f)
        lineTo(13.929f, 7.207f)
        lineTo(13.929f, 7.209f)
        close()
        moveTo(14.083f, 11.255f)
        curveTo(14.728f, 11.255f, 15.248f, 11.805f, 15.248f, 12.478f)
        curveTo(15.248f, 13.157f, 14.725f, 13.707f, 14.083f, 13.707f)
        curveTo(13.442f, 13.707f, 12.919f, 13.157f, 12.919f, 12.484f)
        curveTo(12.921f, 11.806f, 13.444f, 11.257f, 14.083f, 11.257f)
        verticalLineTo(11.255f)
        close()
        moveTo(9.919f, 11.255f)
        curveTo(10.561f, 11.255f, 11.08f, 11.805f, 11.08f, 12.478f)
        curveTo(11.08f, 13.157f, 10.557f, 13.707f, 9.915f, 13.707f)
        curveTo(9.274f, 13.707f, 8.751f, 13.157f, 8.751f, 12.484f)
        curveTo(8.751f, 11.806f, 9.274f, 11.257f, 9.915f, 11.257f)
        lineTo(9.919f, 11.255f)
        close()
    }.build().also { cache = it }
