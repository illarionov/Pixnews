/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.foundation.ui.design.assets.icons.content

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val RateReviewRoundedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "RateReviewRoundedUnfilled400w0g24dp",
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
        moveTo(420.0f, 560.0f)
        lineTo(680.0f, 560.0f)
        quadTo(697.0f, 560.0f, 708.5f, 548.5f)
        quadTo(720.0f, 537.0f, 720.0f, 520.0f)
        quadTo(720.0f, 503.0f, 708.5f, 491.5f)
        quadTo(697.0f, 480.0f, 680.0f, 480.0f)
        lineTo(500.0f, 480.0f)
        lineTo(420.0f, 560.0f)
        close()
        moveTo(260.0f, 560.0f)
        lineTo(322.0f, 560.0f)
        quadTo(330.0f, 560.0f, 337.0f, 556.5f)
        quadTo(344.0f, 553.0f, 350.0f, 548.0f)
        lineTo(574.0f, 326.0f)
        quadTo(580.0f, 320.0f, 580.0f, 311.0f)
        quadTo(580.0f, 302.0f, 574.0f, 296.0f)
        lineTo(504.0f, 226.0f)
        quadTo(498.0f, 220.0f, 489.0f, 220.0f)
        quadTo(480.0f, 220.0f, 474.0f, 226.0f)
        lineTo(252.0f, 450.0f)
        quadTo(247.0f, 456.0f, 243.5f, 463.0f)
        quadTo(240.0f, 470.0f, 240.0f, 478.0f)
        lineTo(240.0f, 540.0f)
        quadTo(240.0f, 548.0f, 246.0f, 554.0f)
        quadTo(252.0f, 560.0f, 260.0f, 560.0f)
        close()
        moveTo(80.0f, 783.0f)
        lineTo(80.0f, 160.0f)
        quadTo(80.0f, 127.0f, 103.5f, 103.5f)
        quadTo(127.0f, 80.0f, 160.0f, 80.0f)
        lineTo(800.0f, 80.0f)
        quadTo(833.0f, 80.0f, 856.5f, 103.5f)
        quadTo(880.0f, 127.0f, 880.0f, 160.0f)
        lineTo(880.0f, 640.0f)
        quadTo(880.0f, 673.0f, 856.5f, 696.5f)
        quadTo(833.0f, 720.0f, 800.0f, 720.0f)
        lineTo(240.0f, 720.0f)
        lineTo(148.0f, 812.0f)
        quadTo(129.0f, 831.0f, 104.5f, 820.5f)
        quadTo(80.0f, 810.0f, 80.0f, 783.0f)
        close()
        moveTo(160.0f, 687.0f)
        lineTo(207.0f, 640.0f)
        lineTo(800.0f, 640.0f)
        quadTo(800.0f, 640.0f, 800.0f, 640.0f)
        quadTo(800.0f, 640.0f, 800.0f, 640.0f)
        lineTo(800.0f, 160.0f)
        quadTo(800.0f, 160.0f, 800.0f, 160.0f)
        quadTo(800.0f, 160.0f, 800.0f, 160.0f)
        lineTo(160.0f, 160.0f)
        quadTo(160.0f, 160.0f, 160.0f, 160.0f)
        quadTo(160.0f, 160.0f, 160.0f, 160.0f)
        lineTo(160.0f, 687.0f)
        close()
        moveTo(160.0f, 160.0f)
        quadTo(160.0f, 160.0f, 160.0f, 160.0f)
        quadTo(160.0f, 160.0f, 160.0f, 160.0f)
        lineTo(160.0f, 160.0f)
        quadTo(160.0f, 160.0f, 160.0f, 160.0f)
        quadTo(160.0f, 160.0f, 160.0f, 160.0f)
        lineTo(160.0f, 640.0f)
        quadTo(160.0f, 640.0f, 160.0f, 640.0f)
        quadTo(160.0f, 640.0f, 160.0f, 640.0f)
        lineTo(160.0f, 640.0f)
        lineTo(160.0f, 687.0f)
        lineTo(160.0f, 160.0f)
        close()
    }.build().also { cache = it }
