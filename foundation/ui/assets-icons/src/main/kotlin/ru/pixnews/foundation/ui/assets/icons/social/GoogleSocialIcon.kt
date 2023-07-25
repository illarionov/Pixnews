/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.social

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val GoogleSocialIcon: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "GoogleSocialIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f,
    ).path(
        fill = SolidColor(Color(0xff4285f4)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(23.439f, 12.224f)
        curveTo(23.439f, 11.241f, 23.36f, 10.524f, 23.187f, 9.78f)
        horizontalLineTo(11.959f)
        verticalLineTo(14.218f)
        horizontalLineTo(18.549f)
        curveTo(18.417f, 15.321f, 17.699f, 16.982f, 16.105f, 18.098f)
        lineTo(16.082f, 18.246f)
        lineTo(19.632f, 20.996f)
        lineTo(19.878f, 21.021f)
        curveTo(22.137f, 18.935f, 23.439f, 15.865f, 23.439f, 12.224f)
        close()
    }.path(
        fill = SolidColor(Color(0xff34a853)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(11.959f, 23.918f)
        curveTo(15.187f, 23.918f, 17.898f, 22.854f, 19.878f, 21.021f)
        lineTo(16.104f, 18.098f)
        curveTo(15.094f, 18.802f, 13.739f, 19.293f, 11.959f, 19.293f)
        curveTo(8.796f, 19.293f, 6.112f, 17.207f, 5.155f, 14.324f)
        lineTo(5.015f, 14.336f)
        lineTo(1.324f, 17.193f)
        lineTo(1.275f, 17.327f)
        curveTo(3.242f, 21.233f, 7.281f, 23.918f, 11.959f, 23.918f)
        close()
    }.path(
        fill = SolidColor(Color(0xfffbbc05)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(5.156f, 14.324f)
        curveTo(4.903f, 13.58f, 4.757f, 12.783f, 4.757f, 11.959f)
        curveTo(4.757f, 11.135f, 4.903f, 10.338f, 5.142f, 9.594f)
        lineTo(5.136f, 9.435f)
        lineTo(1.398f, 6.532f)
        lineTo(1.276f, 6.591f)
        curveTo(0.465f, 8.212f, 0.0f, 10.032f, 0.0f, 11.959f)
        curveTo(0.0f, 13.885f, 0.465f, 15.706f, 1.276f, 17.327f)
        lineTo(5.156f, 14.324f)
        close()
    }.path(
        fill = SolidColor(Color(0xffeb4335)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(11.959f, 4.624f)
        curveTo(14.204f, 4.624f, 15.719f, 5.594f, 16.583f, 6.405f)
        lineTo(19.958f, 3.109f)
        curveTo(17.885f, 1.183f, 15.187f, 0.0f, 11.959f, 0.0f)
        curveTo(7.281f, 0.0f, 3.242f, 2.684f, 1.275f, 6.591f)
        lineTo(5.142f, 9.594f)
        curveTo(6.112f, 6.71f, 8.796f, 4.624f, 11.959f, 4.624f)
        close()
    }.build().also { cache = it }
