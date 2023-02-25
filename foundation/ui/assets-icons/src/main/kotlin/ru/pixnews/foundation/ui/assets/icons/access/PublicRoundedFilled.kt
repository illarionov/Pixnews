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
package ru.pixnews.foundation.ui.assets.icons.access

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val PublicRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "PublicRoundedFilled400w0g24dp",
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
        moveTo(480.0f, 880.0f)
        quadTo(397.0f, 880.0f, 324.0f, 848.5f)
        quadTo(251.0f, 817.0f, 197.0f, 763.0f)
        quadTo(143.0f, 709.0f, 111.5f, 636.0f)
        quadTo(80.0f, 563.0f, 80.0f, 480.0f)
        quadTo(80.0f, 397.0f, 111.5f, 324.0f)
        quadTo(143.0f, 251.0f, 197.0f, 197.0f)
        quadTo(251.0f, 143.0f, 324.0f, 111.5f)
        quadTo(397.0f, 80.0f, 480.0f, 80.0f)
        quadTo(563.0f, 80.0f, 636.0f, 111.5f)
        quadTo(709.0f, 143.0f, 763.0f, 197.0f)
        quadTo(817.0f, 251.0f, 848.5f, 324.0f)
        quadTo(880.0f, 397.0f, 880.0f, 480.0f)
        quadTo(880.0f, 563.0f, 848.5f, 636.0f)
        quadTo(817.0f, 709.0f, 763.0f, 763.0f)
        quadTo(709.0f, 817.0f, 636.0f, 848.5f)
        quadTo(563.0f, 880.0f, 480.0f, 880.0f)
        close()
        moveTo(437.0f, 819.0f)
        lineTo(437.0f, 737.0f)
        quadTo(402.0f, 737.0f, 378.0f, 711.0f)
        quadTo(354.0f, 685.0f, 354.0f, 650.0f)
        lineTo(354.0f, 606.0f)
        lineTo(149.0f, 401.0f)
        quadTo(144.0f, 421.0f, 142.0f, 440.5f)
        quadTo(140.0f, 460.0f, 140.0f, 480.0f)
        quadTo(140.0f, 610.0f, 224.5f, 707.0f)
        quadTo(309.0f, 804.0f, 437.0f, 819.0f)
        close()
        moveTo(731.0f, 711.0f)
        quadTo(753.0f, 687.0f, 769.5f, 660.0f)
        quadTo(786.0f, 633.0f, 797.5f, 603.5f)
        quadTo(809.0f, 574.0f, 814.5f, 543.0f)
        quadTo(820.0f, 512.0f, 820.0f, 480.0f)
        quadTo(820.0f, 374.0f, 762.0f, 287.5f)
        quadTo(704.0f, 201.0f, 607.0f, 161.0f)
        lineTo(607.0f, 179.0f)
        quadTo(607.0f, 214.0f, 583.0f, 240.0f)
        quadTo(559.0f, 266.0f, 524.0f, 266.0f)
        lineTo(437.0f, 266.0f)
        lineTo(437.0f, 353.0f)
        quadTo(437.0f, 370.0f, 423.5f, 381.0f)
        quadTo(410.0f, 392.0f, 393.0f, 392.0f)
        lineTo(310.0f, 392.0f)
        lineTo(310.0f, 480.0f)
        lineTo(568.0f, 480.0f)
        quadTo(585.0f, 480.0f, 596.0f, 493.0f)
        quadTo(607.0f, 506.0f, 607.0f, 523.0f)
        lineTo(607.0f, 650.0f)
        lineTo(650.0f, 650.0f)
        quadTo(679.0f, 650.0f, 701.0f, 667.0f)
        quadTo(723.0f, 684.0f, 731.0f, 711.0f)
        close()
    }.build().also { cache = it }
