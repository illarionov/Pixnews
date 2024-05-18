/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.distribution

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val HumbleBumbleDistributionPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "HumbleBumbleDistributionPlatform",
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
        moveTo(17.404f, 18.729f)
        curveTo(14.302f, 18.729f, 19.077f, 1.142f, 19.077f, 1.142f)
        lineTo(15.88f, 1.139f)
        curveTo(15.88f, 1.139f, 14.572f, 5.277f, 13.708f, 9.877f)
        horizontalLineTo(10.953f)
        curveTo(11.025f, 8.931f, 11.059f, 7.974f, 11.044f, 7.023f)
        curveTo(10.92f, -0.549f, 6.484f, 0.853f, 4.499f, 2.591f)
        curveTo(2.611f, 4.243f, 1.03f, 7.382f, 1.0f, 9.8f)
        curveTo(1.301f, 9.785f, 2.489f, 9.78f, 2.489f, 9.78f)
        curveTo(2.489f, 9.78f, 3.477f, 5.272f, 6.579f, 5.272f)
        curveTo(9.682f, 5.272f, 4.898f, 22.86f, 4.898f, 22.86f)
        lineTo(8.097f, 22.862f)
        curveTo(8.097f, 22.862f, 9.75f, 18.154f, 10.572f, 12.896f)
        lineTo(13.203f, 12.88f)
        curveTo(13.05f, 14.241f, 13.001f, 15.744f, 13.023f, 17.139f)
        curveTo(13.148f, 24.711f, 17.566f, 23.086f, 19.551f, 21.349f)
        curveTo(21.537f, 19.611f, 23.018f, 15.941f, 23.0f, 14.179f)
        curveTo(23.002f, 14.177f, 21.492f, 14.191f, 21.474f, 14.191f)
        curveTo(21.479f, 14.33f, 20.506f, 18.729f, 17.404f, 18.729f)
        close()
    }.build().also { cache = it }
