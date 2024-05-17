/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.failure

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null

internal val baselineSignalWifi0BarVector: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "baseline-signal-wifi-0-bar",
        defaultWidth = 145.dp,
        defaultHeight = 102.dp,
        viewportWidth = 145.0f,
        viewportHeight = 102.0f,
    ).path(
        fill = SolidColor(Color(0xff4356b9)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(72.5f, 12.0f)
        curveTo(92.48f, 12.0f, 111.44f, 18.48f, 126.98f, 30.42f)
        lineTo(72.5f, 85.02f)
        lineTo(18.02f, 30.42f)
        curveTo(33.56f, 18.48f, 52.52f, 12.0f, 72.5f, 12.0f)
        close()
        moveTo(72.5f, 0.0f)
        curveTo(44.36f, 0.0f, 18.92f, 11.4f, 0.5f, 29.88f)
        lineTo(72.5f, 102.0f)
        lineTo(144.5f, 29.88f)
        curveTo(126.08f, 11.4f, 100.64f, 0.0f, 72.5f, 0.0f)
        close()
    }.build().also { cache = it }
