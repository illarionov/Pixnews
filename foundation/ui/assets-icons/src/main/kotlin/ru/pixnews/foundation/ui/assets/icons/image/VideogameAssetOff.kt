/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.image

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val VideogameAssetOff: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "videogame_asset_off",
        defaultWidth = 48.dp,
        defaultHeight = 48.dp,
        viewportWidth = 48.0f,
        viewportHeight = 48.0f,
    ).path(
        fill = SolidColor(Color(0xff767680)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(44.0f, 32.0f)
        curveTo(44.0f, 32.867f, 43.767f, 33.633f, 43.3f, 34.3f)
        curveTo(42.833f, 34.967f, 42.217f, 35.45f, 41.45f, 35.75f)
        lineTo(37.7f, 32.0f)
        horizontalLineTo(40.0f)
        verticalLineTo(16.0f)
        horizontalLineTo(21.7f)
        lineTo(17.7f, 12.0f)
        horizontalLineTo(40.0f)
        curveTo(41.1f, 12.0f, 42.042f, 12.392f, 42.826f, 13.176f)
        curveTo(43.609f, 13.959f, 44.0f, 14.9f, 44.0f, 16.0f)
        verticalLineTo(32.0f)
        close()
        moveTo(35.0f, 24.0f)
        curveTo(34.167f, 24.0f, 33.459f, 23.708f, 32.876f, 23.124f)
        curveTo(32.292f, 22.541f, 32.0f, 21.833f, 32.0f, 21.0f)
        curveTo(32.0f, 20.167f, 32.292f, 19.459f, 32.876f, 18.876f)
        curveTo(33.459f, 18.292f, 34.167f, 18.0f, 35.0f, 18.0f)
        curveTo(35.833f, 18.0f, 36.541f, 18.292f, 37.124f, 18.876f)
        curveTo(37.708f, 19.459f, 38.0f, 20.167f, 38.0f, 21.0f)
        curveTo(38.0f, 21.833f, 37.708f, 22.541f, 37.124f, 23.124f)
        curveTo(36.541f, 23.708f, 35.833f, 24.0f, 35.0f, 24.0f)
        close()
        moveTo(14.0f, 30.0f)
        verticalLineTo(26.0f)
        horizontalLineTo(10.0f)
        verticalLineTo(22.0f)
        horizontalLineTo(14.0f)
        verticalLineTo(18.0f)
        horizontalLineTo(18.0f)
        verticalLineTo(22.0f)
        horizontalLineTo(22.0f)
        verticalLineTo(26.0f)
        horizontalLineTo(18.0f)
        verticalLineTo(30.0f)
        horizontalLineTo(14.0f)
        close()
        moveTo(8.0f, 36.0f)
        curveTo(6.9f, 36.0f, 5.959f, 35.609f, 5.176f, 34.826f)
        curveTo(4.392f, 34.042f, 4.0f, 33.1f, 4.0f, 32.0f)
        verticalLineTo(16.0f)
        curveTo(4.0f, 14.867f, 4.4f, 13.908f, 5.2f, 13.124f)
        curveTo(6.0f, 12.341f, 6.967f, 11.95f, 8.1f, 11.95f)
        horizontalLineTo(11.95f)
        lineTo(16.0f, 16.0f)
        horizontalLineTo(8.0f)
        verticalLineTo(32.0f)
        horizontalLineTo(26.3f)
        lineTo(2.75f, 8.45f)
        lineTo(5.6f, 5.6f)
        lineTo(42.4f, 42.4f)
        lineTo(39.55f, 45.25f)
        lineTo(30.3f, 36.0f)
        horizontalLineTo(8.0f)
        close()
    }.build().also { cache = it }
