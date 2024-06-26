/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
internal val WindowsPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "WindowsPlatform",
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
        moveTo(1.0f, 4.892f)
        lineTo(10.016f, 3.706f)
        verticalLineTo(12.022f)
        horizontalLineTo(1.0f)
        verticalLineTo(4.892f)
        close()
        moveTo(1.0f, 20.108f)
        lineTo(10.016f, 21.294f)
        verticalLineTo(13.081f)
        horizontalLineTo(1.0f)
        verticalLineTo(20.108f)
        close()
        moveTo(11.008f, 21.42f)
        lineTo(23.0f, 23.0f)
        verticalLineTo(13.081f)
        horizontalLineTo(11.008f)
        verticalLineTo(21.42f)
        close()
        moveTo(11.008f, 3.58f)
        verticalLineTo(12.022f)
        horizontalLineTo(23.0f)
        verticalLineTo(2.0f)
        lineTo(11.008f, 3.58f)
        close()
    }.build().also { cache = it }
