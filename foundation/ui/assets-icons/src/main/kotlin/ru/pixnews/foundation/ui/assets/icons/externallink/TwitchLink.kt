/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
internal val TwitchLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "TwitchLink",
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
        moveTo(11.606f, 6.321f)
        horizontalLineTo(13.178f)
        verticalLineTo(11.036f)
        horizontalLineTo(11.605f)
        lineTo(11.606f, 6.321f)
        close()
        moveTo(15.928f, 6.321f)
        horizontalLineTo(17.499f)
        verticalLineTo(11.036f)
        horizontalLineTo(15.928f)
        verticalLineTo(6.321f)
        close()
        moveTo(6.499f, 2.0f)
        lineTo(2.57f, 5.929f)
        verticalLineTo(20.071f)
        horizontalLineTo(7.285f)
        verticalLineTo(24.0f)
        lineTo(11.214f, 20.071f)
        horizontalLineTo(14.356f)
        lineTo(21.428f, 13.0f)
        verticalLineTo(2.0f)
        horizontalLineTo(6.499f)
        close()
        moveTo(19.856f, 12.214f)
        lineTo(16.714f, 15.357f)
        horizontalLineTo(13.57f)
        lineTo(10.82f, 18.107f)
        verticalLineTo(15.357f)
        horizontalLineTo(7.285f)
        verticalLineTo(3.571f)
        horizontalLineTo(19.856f)
        verticalLineTo(12.214f)
        close()
    }.build().also { cache = it }
