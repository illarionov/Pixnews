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
internal val CommentsUnfilled: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "CommentsUnfilled",
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
        moveTo(22.0f, 23.0f)
        lineTo(20.05f, 16.3f)
        curveTo(20.367f, 15.617f, 20.604f, 14.917f, 20.763f, 14.2f)
        curveTo(20.921f, 13.483f, 21.0f, 12.75f, 21.0f, 12.0f)
        curveTo(21.0f, 10.617f, 20.737f, 9.317f, 20.212f, 8.1f)
        curveTo(19.687f, 6.883f, 18.975f, 5.825f, 18.075f, 4.925f)
        curveTo(17.175f, 4.025f, 16.117f, 3.312f, 14.9f, 2.787f)
        curveTo(13.683f, 2.262f, 12.383f, 2.0f, 11.0f, 2.0f)
        curveTo(9.617f, 2.0f, 8.317f, 2.262f, 7.1f, 2.787f)
        curveTo(5.883f, 3.312f, 4.825f, 4.025f, 3.925f, 4.925f)
        curveTo(3.025f, 5.825f, 2.313f, 6.883f, 1.788f, 8.1f)
        curveTo(1.263f, 9.317f, 1.0f, 10.617f, 1.0f, 12.0f)
        curveTo(1.0f, 13.383f, 1.263f, 14.683f, 1.788f, 15.9f)
        curveTo(2.313f, 17.117f, 3.025f, 18.175f, 3.925f, 19.075f)
        curveTo(4.825f, 19.975f, 5.883f, 20.687f, 7.1f, 21.212f)
        curveTo(8.317f, 21.737f, 9.617f, 22.0f, 11.0f, 22.0f)
        curveTo(11.75f, 22.0f, 12.483f, 21.921f, 13.2f, 21.762f)
        curveTo(13.917f, 21.604f, 14.617f, 21.367f, 15.3f, 21.05f)
        lineTo(22.0f, 23.0f)
        close()
        moveTo(19.05f, 20.05f)
        lineTo(15.85f, 19.1f)
        curveTo(15.617f, 19.033f, 15.379f, 19.008f, 15.138f, 19.025f)
        curveTo(14.896f, 19.042f, 14.667f, 19.1f, 14.45f, 19.2f)
        curveTo(13.917f, 19.467f, 13.358f, 19.667f, 12.775f, 19.8f)
        curveTo(12.192f, 19.933f, 11.6f, 20.0f, 11.0f, 20.0f)
        curveTo(8.767f, 20.0f, 6.875f, 19.225f, 5.325f, 17.675f)
        curveTo(3.775f, 16.125f, 3.0f, 14.233f, 3.0f, 12.0f)
        curveTo(3.0f, 9.767f, 3.775f, 7.875f, 5.325f, 6.325f)
        curveTo(6.875f, 4.775f, 8.767f, 4.0f, 11.0f, 4.0f)
        curveTo(13.233f, 4.0f, 15.125f, 4.775f, 16.675f, 6.325f)
        curveTo(18.225f, 7.875f, 19.0f, 9.767f, 19.0f, 12.0f)
        curveTo(19.0f, 12.6f, 18.933f, 13.192f, 18.8f, 13.775f)
        curveTo(18.667f, 14.358f, 18.467f, 14.917f, 18.2f, 15.45f)
        curveTo(18.083f, 15.667f, 18.021f, 15.896f, 18.012f, 16.137f)
        curveTo(18.004f, 16.379f, 18.033f, 16.617f, 18.1f, 16.85f)
        lineTo(19.05f, 20.05f)
        close()
    }.build().also { cache = it }
