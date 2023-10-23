/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
internal val ErrorOutlineUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "ErrorOutline400w0g24dp",
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
        moveTo(12.0f, 7.0f)
        curveTo(12.55f, 7.0f, 13.0f, 7.45f, 13.0f, 8.0f)
        verticalLineTo(12.0f)
        curveTo(13.0f, 12.55f, 12.55f, 13.0f, 12.0f, 13.0f)
        curveTo(11.45f, 13.0f, 11.0f, 12.55f, 11.0f, 12.0f)
        verticalLineTo(8.0f)
        curveTo(11.0f, 7.45f, 11.45f, 7.0f, 12.0f, 7.0f)
        close()
        moveTo(11.99f, 2.0f)
        curveTo(6.47f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
        curveTo(2.0f, 17.52f, 6.47f, 22.0f, 11.99f, 22.0f)
        curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f)
        curveTo(22.0f, 6.48f, 17.52f, 2.0f, 11.99f, 2.0f)
        close()
        moveTo(12.0f, 20.0f)
        curveTo(7.58f, 20.0f, 4.0f, 16.42f, 4.0f, 12.0f)
        curveTo(4.0f, 7.58f, 7.58f, 4.0f, 12.0f, 4.0f)
        curveTo(16.42f, 4.0f, 20.0f, 7.58f, 20.0f, 12.0f)
        curveTo(20.0f, 16.42f, 16.42f, 20.0f, 12.0f, 20.0f)
        close()
        moveTo(13.0f, 17.0f)
        horizontalLineTo(11.0f)
        verticalLineTo(15.0f)
        horizontalLineTo(13.0f)
        verticalLineTo(17.0f)
        close()

    }.build().also { cache = it }
