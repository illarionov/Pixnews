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
internal val YoutubeLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "YoutubeLink",
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
        moveTo(23.498f, 6.186f)
        curveTo(23.362f, 5.675f, 23.095f, 5.209f, 22.723f, 4.834f)
        curveTo(22.35f, 4.459f, 21.886f, 4.189f, 21.376f, 4.05f)
        curveTo(19.505f, 3.545f, 12.0f, 3.545f, 12.0f, 3.545f)
        curveTo(12.0f, 3.545f, 4.495f, 3.545f, 2.623f, 4.05f)
        curveTo(2.113f, 4.189f, 1.649f, 4.46f, 1.277f, 4.835f)
        curveTo(0.905f, 5.209f, 0.638f, 5.675f, 0.502f, 6.186f)
        curveTo(0.0f, 8.07f, 0.0f, 12.0f, 0.0f, 12.0f)
        curveTo(0.0f, 12.0f, 0.0f, 15.93f, 0.502f, 17.814f)
        curveTo(0.638f, 18.325f, 0.905f, 18.791f, 1.277f, 19.166f)
        curveTo(1.65f, 19.541f, 2.114f, 19.811f, 2.624f, 19.95f)
        curveTo(4.495f, 20.455f, 12.0f, 20.455f, 12.0f, 20.455f)
        curveTo(12.0f, 20.455f, 19.505f, 20.455f, 21.377f, 19.95f)
        curveTo(21.887f, 19.811f, 22.351f, 19.541f, 22.724f, 19.166f)
        curveTo(23.096f, 18.791f, 23.364f, 18.325f, 23.499f, 17.814f)
        curveTo(24.0f, 15.93f, 24.0f, 12.0f, 24.0f, 12.0f)
        curveTo(24.0f, 12.0f, 24.0f, 8.07f, 23.498f, 6.186f)
        close()
        moveTo(9.545f, 15.568f)
        verticalLineTo(8.432f)
        lineTo(15.818f, 12.0f)
        lineTo(9.545f, 15.568f)
        close()
    }.build().also { cache = it }
