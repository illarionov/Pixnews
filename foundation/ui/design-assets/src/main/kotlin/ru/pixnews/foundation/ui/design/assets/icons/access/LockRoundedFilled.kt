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
package ru.pixnews.foundation.ui.design.assets.icons.access

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val LockRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "LockRoundedFilled400w0g24dp",
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
        moveTo(220.0f, 880.0f)
        quadTo(195.25f, 880.0f, 177.63f, 862.38f)
        quadTo(160.0f, 844.75f, 160.0f, 820.0f)
        lineTo(160.0f, 386.0f)
        quadTo(160.0f, 361.25f, 177.63f, 343.63f)
        quadTo(195.25f, 326.0f, 220.0f, 326.0f)
        lineTo(290.0f, 326.0f)
        lineTo(290.0f, 230.0f)
        quadTo(290.0f, 151.15f, 345.61f, 95.57f)
        quadTo(401.21f, 40.0f, 480.11f, 40.0f)
        quadTo(559.0f, 40.0f, 614.5f, 95.57f)
        quadTo(670.0f, 151.15f, 670.0f, 230.0f)
        lineTo(670.0f, 326.0f)
        lineTo(740.0f, 326.0f)
        quadTo(764.75f, 326.0f, 782.38f, 343.63f)
        quadTo(800.0f, 361.25f, 800.0f, 386.0f)
        lineTo(800.0f, 820.0f)
        quadTo(800.0f, 844.75f, 782.38f, 862.38f)
        quadTo(764.75f, 880.0f, 740.0f, 880.0f)
        lineTo(220.0f, 880.0f)
        close()
        moveTo(480.17f, 680.0f)
        quadTo(512.0f, 680.0f, 534.5f, 657.97f)
        quadTo(557.0f, 635.94f, 557.0f, 605.0f)
        quadTo(557.0f, 575.0f, 534.33f, 550.5f)
        quadTo(511.66f, 526.0f, 479.83f, 526.0f)
        quadTo(448.0f, 526.0f, 425.5f, 550.5f)
        quadTo(403.0f, 575.0f, 403.0f, 605.5f)
        quadTo(403.0f, 636.0f, 425.67f, 658.0f)
        quadTo(448.34f, 680.0f, 480.17f, 680.0f)
        close()
        moveTo(350.0f, 326.0f)
        lineTo(610.0f, 326.0f)
        lineTo(610.0f, 230.0f)
        quadTo(610.0f, 175.83f, 572.12f, 137.92f)
        quadTo(534.23f, 100.0f, 480.12f, 100.0f)
        quadTo(426.0f, 100.0f, 388.0f, 137.92f)
        quadTo(350.0f, 175.83f, 350.0f, 230.0f)
        lineTo(350.0f, 326.0f)
        close()
    }.build().also { cache = it }
