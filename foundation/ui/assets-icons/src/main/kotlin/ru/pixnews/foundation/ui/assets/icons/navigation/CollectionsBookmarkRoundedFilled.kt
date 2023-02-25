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
internal val CollectionsBookmarkRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "CollectionsBookmarkRoundedFilled400w0g24dp",
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
        moveTo(550.0f, 422.0f)
        lineTo(620.0f, 380.0f)
        lineTo(690.0f, 422.0f)
        quadTo(700.0f, 428.0f, 710.0f, 422.0f)
        quadTo(720.0f, 416.0f, 720.0f, 405.0f)
        lineTo(720.0f, 160.0f)
        lineTo(520.0f, 160.0f)
        lineTo(520.0f, 405.0f)
        quadTo(520.0f, 416.0f, 530.0f, 422.0f)
        quadTo(540.0f, 428.0f, 550.0f, 422.0f)
        close()
        moveTo(320.0f, 720.0f)
        quadTo(287.0f, 720.0f, 263.5f, 696.5f)
        quadTo(240.0f, 673.0f, 240.0f, 640.0f)
        lineTo(240.0f, 160.0f)
        quadTo(240.0f, 127.0f, 263.5f, 103.5f)
        quadTo(287.0f, 80.0f, 320.0f, 80.0f)
        lineTo(800.0f, 80.0f)
        quadTo(833.0f, 80.0f, 856.5f, 103.5f)
        quadTo(880.0f, 127.0f, 880.0f, 160.0f)
        lineTo(880.0f, 640.0f)
        quadTo(880.0f, 673.0f, 856.5f, 696.5f)
        quadTo(833.0f, 720.0f, 800.0f, 720.0f)
        lineTo(320.0f, 720.0f)
        close()
        moveTo(160.0f, 880.0f)
        quadTo(127.0f, 880.0f, 103.5f, 856.5f)
        quadTo(80.0f, 833.0f, 80.0f, 800.0f)
        lineTo(80.0f, 280.0f)
        quadTo(80.0f, 263.0f, 91.5f, 251.5f)
        quadTo(103.0f, 240.0f, 120.0f, 240.0f)
        quadTo(137.0f, 240.0f, 148.5f, 251.5f)
        quadTo(160.0f, 263.0f, 160.0f, 280.0f)
        lineTo(160.0f, 800.0f)
        quadTo(160.0f, 800.0f, 160.0f, 800.0f)
        quadTo(160.0f, 800.0f, 160.0f, 800.0f)
        lineTo(680.0f, 800.0f)
        quadTo(697.0f, 800.0f, 708.5f, 811.5f)
        quadTo(720.0f, 823.0f, 720.0f, 840.0f)
        quadTo(720.0f, 857.0f, 708.5f, 868.5f)
        quadTo(697.0f, 880.0f, 680.0f, 880.0f)
        lineTo(160.0f, 880.0f)
        close()
    }.build().also { cache = it }
