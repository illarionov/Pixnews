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
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val RefreshUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Refresh",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f,
    ).group(
        rotate = 0.0f,
        pivotX = 0.0f,
        pivotY = 0.0f,
        scaleX = 1.0f,
        scaleY = 1.0f,
        translationX = 0.0f,
        translationY = 0.0f,
        clipPathData = emptyList(),
    ) {
        path(
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
            moveTo(17.6498f, 6.34999f)
            curveTo(16.0198f, 4.71999f, 13.7098f, 3.77999f, 11.1698f, 4.03999f)
            curveTo(7.49978f, 4.40999f, 4.47978f, 7.38999f, 4.06978f, 11.06f)
            curveTo(3.51978f, 15.91f, 7.26978f, 20.0f, 11.9998f, 20.0f)
            curveTo(15.1898f, 20.0f, 17.9298f, 18.13f, 19.2098f, 15.44f)
            curveTo(19.5298f, 14.77f, 19.0498f, 14.0f, 18.3098f, 14.0f)
            curveTo(17.9398f, 14.0f, 17.5898f, 14.2f, 17.4298f, 14.53f)
            curveTo(16.2998f, 16.96f, 13.5898f, 18.5f, 10.6298f, 17.84f)
            curveTo(8.40978f, 17.35f, 6.61978f, 15.54f, 6.14978f, 13.32f)
            curveTo(5.30978f, 9.43999f, 8.25978f, 5.99999f, 11.9998f, 5.99999f)
            curveTo(13.6598f, 5.99999f, 15.1398f, 6.68999f, 16.2198f, 7.77999f)
            lineTo(14.7098f, 9.28999f)
            curveTo(14.0798f, 9.91999f, 14.5198f, 11.0f, 15.4098f, 11.0f)
            horizontalLineTo(18.9998f)
            curveTo(19.5498f, 11.0f, 19.9998f, 10.55f, 19.9998f, 9.99999f)
            verticalLineTo(6.40999f)
            curveTo(19.9998f, 5.51999f, 18.9198f, 5.06999f, 18.2898f, 5.69999f)
            lineTo(17.6498f, 6.34999f)
            close()
        }
    }.build().also { cache = it }
