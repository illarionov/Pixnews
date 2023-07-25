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
internal val ArrowInsertRoundedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "ArrowInsertRoundedUnfilled400w0g24dp",
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
        moveTo(320.0f, 336.0f)
        lineTo(320.0f, 640.0f)
        quadTo(320.0f, 657.0f, 308.5f, 668.5f)
        quadTo(297.0f, 680.0f, 280.0f, 680.0f)
        quadTo(263.0f, 680.0f, 251.5f, 668.5f)
        quadTo(240.0f, 657.0f, 240.0f, 640.0f)
        lineTo(240.0f, 240.0f)
        quadTo(240.0f, 223.0f, 251.5f, 211.5f)
        quadTo(263.0f, 200.0f, 280.0f, 200.0f)
        lineTo(680.0f, 200.0f)
        quadTo(697.0f, 200.0f, 708.5f, 211.5f)
        quadTo(720.0f, 223.0f, 720.0f, 240.0f)
        quadTo(720.0f, 257.0f, 708.5f, 268.5f)
        quadTo(697.0f, 280.0f, 680.0f, 280.0f)
        lineTo(376.0f, 280.0f)
        lineTo(732.0f, 636.0f)
        quadTo(743.0f, 647.0f, 743.0f, 664.0f)
        quadTo(743.0f, 681.0f, 732.0f, 692.0f)
        quadTo(721.0f, 703.0f, 704.0f, 703.0f)
        quadTo(687.0f, 703.0f, 676.0f, 692.0f)
        lineTo(320.0f, 336.0f)
        close()
    }.build().also { cache = it }
