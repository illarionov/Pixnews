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
internal val RepostsRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "RepostsRoundedFilled400w0g24dp",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f,
    ).path(
        fill = SolidColor(Color(0xff1c1b1f)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(22.0f, 17.0f)
        lineTo(18.0f, 21.0f)
        lineTo(14.0f, 17.0f)
        lineTo(15.45f, 15.6f)
        lineTo(17.0f, 17.15f)
        verticalLineTo(7.0f)
        lineTo(13.0f, 7.0f)
        lineTo(13.0f, 5.0f)
        lineTo(19.0f, 5.0f)
        lineTo(19.0f, 17.15f)
        lineTo(20.55f, 15.6f)
        lineTo(22.0f, 17.0f)
        close()
        moveTo(11.0f, 19.0f)
        horizontalLineTo(5.0f)
        lineTo(5.0f, 6.85f)
        lineTo(3.45f, 8.4f)
        lineTo(2.0f, 7.0f)
        lineTo(6.0f, 3.0f)
        lineTo(10.0f, 7.0f)
        lineTo(8.55f, 8.4f)
        lineTo(7.0f, 6.85f)
        lineTo(7.0f, 17.0f)
        horizontalLineTo(11.0f)
        verticalLineTo(19.0f)
        close()
    }.build().also { cache = it }
