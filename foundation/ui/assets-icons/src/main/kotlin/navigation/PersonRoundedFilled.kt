/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val PersonRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "PersonRoundedFilled400w0g24dp",
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
        moveTo(480.0f, 480.0f)
        quadTo(414.0f, 480.0f, 367.0f, 433.0f)
        quadTo(320.0f, 386.0f, 320.0f, 320.0f)
        quadTo(320.0f, 254.0f, 367.0f, 207.0f)
        quadTo(414.0f, 160.0f, 480.0f, 160.0f)
        quadTo(546.0f, 160.0f, 593.0f, 207.0f)
        quadTo(640.0f, 254.0f, 640.0f, 320.0f)
        quadTo(640.0f, 386.0f, 593.0f, 433.0f)
        quadTo(546.0f, 480.0f, 480.0f, 480.0f)
        close()
        moveTo(240.0f, 800.0f)
        quadTo(207.0f, 800.0f, 183.5f, 776.5f)
        quadTo(160.0f, 753.0f, 160.0f, 720.0f)
        lineTo(160.0f, 688.0f)
        quadTo(160.0f, 654.0f, 177.5f, 625.5f)
        quadTo(195.0f, 597.0f, 224.0f, 582.0f)
        quadTo(286.0f, 551.0f, 350.0f, 535.5f)
        quadTo(414.0f, 520.0f, 480.0f, 520.0f)
        quadTo(546.0f, 520.0f, 610.0f, 535.5f)
        quadTo(674.0f, 551.0f, 736.0f, 582.0f)
        quadTo(765.0f, 597.0f, 782.5f, 625.5f)
        quadTo(800.0f, 654.0f, 800.0f, 688.0f)
        lineTo(800.0f, 720.0f)
        quadTo(800.0f, 753.0f, 776.5f, 776.5f)
        quadTo(753.0f, 800.0f, 720.0f, 800.0f)
        lineTo(240.0f, 800.0f)
        close()
    }.build().also { cache = it }
