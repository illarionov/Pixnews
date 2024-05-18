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
internal val FilterListRoundedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "FilterListRoundedUnfilled400w0g24dp",
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
        moveTo(440.0f, 720.0f)
        quadTo(423.0f, 720.0f, 411.5f, 708.5f)
        quadTo(400.0f, 697.0f, 400.0f, 680.0f)
        quadTo(400.0f, 663.0f, 411.5f, 651.5f)
        quadTo(423.0f, 640.0f, 440.0f, 640.0f)
        lineTo(520.0f, 640.0f)
        quadTo(537.0f, 640.0f, 548.5f, 651.5f)
        quadTo(560.0f, 663.0f, 560.0f, 680.0f)
        quadTo(560.0f, 697.0f, 548.5f, 708.5f)
        quadTo(537.0f, 720.0f, 520.0f, 720.0f)
        lineTo(440.0f, 720.0f)
        close()
        moveTo(160.0f, 320.0f)
        quadTo(143.0f, 320.0f, 131.5f, 308.5f)
        quadTo(120.0f, 297.0f, 120.0f, 280.0f)
        quadTo(120.0f, 263.0f, 131.5f, 251.5f)
        quadTo(143.0f, 240.0f, 160.0f, 240.0f)
        lineTo(800.0f, 240.0f)
        quadTo(817.0f, 240.0f, 828.5f, 251.5f)
        quadTo(840.0f, 263.0f, 840.0f, 280.0f)
        quadTo(840.0f, 297.0f, 828.5f, 308.5f)
        quadTo(817.0f, 320.0f, 800.0f, 320.0f)
        lineTo(160.0f, 320.0f)
        close()
        moveTo(280.0f, 520.0f)
        quadTo(263.0f, 520.0f, 251.5f, 508.5f)
        quadTo(240.0f, 497.0f, 240.0f, 480.0f)
        quadTo(240.0f, 463.0f, 251.5f, 451.5f)
        quadTo(263.0f, 440.0f, 280.0f, 440.0f)
        lineTo(680.0f, 440.0f)
        quadTo(697.0f, 440.0f, 708.5f, 451.5f)
        quadTo(720.0f, 463.0f, 720.0f, 480.0f)
        quadTo(720.0f, 497.0f, 708.5f, 508.5f)
        quadTo(697.0f, 520.0f, 680.0f, 520.0f)
        lineTo(280.0f, 520.0f)
        close()
    }.build().also { cache = it }
