/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
internal val Warning: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "warning",
        defaultWidth = 48.dp,
        defaultHeight = 48.dp,
        viewportWidth = 48.0f,
        viewportHeight = 48.0f,
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
        moveTo(2.0f, 42.0f)
        lineTo(24.0f, 4.0f)
        lineTo(46.0f, 42.0f)
        horizontalLineTo(2.0f)
        close()
        moveTo(8.9f, 38.0f)
        horizontalLineTo(39.1f)
        lineTo(24.0f, 12.0f)
        lineTo(8.9f, 38.0f)
        close()
        moveTo(24.0f, 36.0f)
        curveTo(24.567f, 36.0f, 25.042f, 35.808f, 25.426f, 35.424f)
        curveTo(25.809f, 35.041f, 26.0f, 34.567f, 26.0f, 34.0f)
        curveTo(26.0f, 33.433f, 25.809f, 32.959f, 25.426f, 32.576f)
        curveTo(25.042f, 32.192f, 24.567f, 32.0f, 24.0f, 32.0f)
        curveTo(23.433f, 32.0f, 22.959f, 32.192f, 22.576f, 32.576f)
        curveTo(22.192f, 32.959f, 22.0f, 33.433f, 22.0f, 34.0f)
        curveTo(22.0f, 34.567f, 22.192f, 35.041f, 22.576f, 35.424f)
        curveTo(22.959f, 35.808f, 23.433f, 36.0f, 24.0f, 36.0f)
        close()
        moveTo(22.0f, 30.0f)
        horizontalLineTo(26.0f)
        verticalLineTo(20.0f)
        horizontalLineTo(22.0f)
        verticalLineTo(30.0f)
        close()
    }.build().also { cache = it }
