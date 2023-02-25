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
package ru.pixnews.foundation.ui.assets.icons.content

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val SynRoundedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "SynRoundedUnfilled400w0g24dp",
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
        moveTo(615.0f, 770.0f)
        quadTo(594.0f, 780.0f, 577.0f, 768.5f)
        quadTo(560.0f, 757.0f, 560.0f, 731.0f)
        quadTo(560.0f, 721.0f, 566.5f, 711.5f)
        quadTo(573.0f, 702.0f, 583.0f, 697.0f)
        quadTo(646.0f, 667.0f, 683.0f, 608.0f)
        quadTo(720.0f, 549.0f, 720.0f, 478.0f)
        quadTo(720.0f, 433.0f, 703.0f, 390.5f)
        quadTo(686.0f, 348.0f, 650.0f, 312.0f)
        lineTo(640.0f, 302.0f)
        lineTo(640.0f, 360.0f)
        quadTo(640.0f, 377.0f, 628.5f, 388.5f)
        quadTo(617.0f, 400.0f, 600.0f, 400.0f)
        quadTo(583.0f, 400.0f, 571.5f, 388.5f)
        quadTo(560.0f, 377.0f, 560.0f, 360.0f)
        lineTo(560.0f, 200.0f)
        quadTo(560.0f, 183.0f, 571.5f, 171.5f)
        quadTo(583.0f, 160.0f, 600.0f, 160.0f)
        lineTo(760.0f, 160.0f)
        quadTo(777.0f, 160.0f, 788.5f, 171.5f)
        quadTo(800.0f, 183.0f, 800.0f, 200.0f)
        quadTo(800.0f, 217.0f, 788.5f, 228.5f)
        quadTo(777.0f, 240.0f, 760.0f, 240.0f)
        lineTo(690.0f, 240.0f)
        lineTo(706.0f, 254.0f)
        quadTo(755.0f, 303.0f, 777.5f, 360.5f)
        quadTo(800.0f, 418.0f, 800.0f, 478.0f)
        quadTo(800.0f, 574.0f, 750.0f, 652.5f)
        quadTo(700.0f, 731.0f, 615.0f, 770.0f)
        close()
        moveTo(200.0f, 800.0f)
        quadTo(183.0f, 800.0f, 171.5f, 788.5f)
        quadTo(160.0f, 777.0f, 160.0f, 760.0f)
        quadTo(160.0f, 743.0f, 171.5f, 731.5f)
        quadTo(183.0f, 720.0f, 200.0f, 720.0f)
        lineTo(270.0f, 720.0f)
        lineTo(254.0f, 706.0f)
        quadTo(205.0f, 657.0f, 182.5f, 599.5f)
        quadTo(160.0f, 542.0f, 160.0f, 482.0f)
        quadTo(160.0f, 386.0f, 210.0f, 307.5f)
        quadTo(260.0f, 229.0f, 345.0f, 190.0f)
        quadTo(366.0f, 180.0f, 383.0f, 191.5f)
        quadTo(400.0f, 203.0f, 400.0f, 229.0f)
        quadTo(400.0f, 239.0f, 393.5f, 248.5f)
        quadTo(387.0f, 258.0f, 377.0f, 263.0f)
        quadTo(314.0f, 293.0f, 277.0f, 352.0f)
        quadTo(240.0f, 411.0f, 240.0f, 482.0f)
        quadTo(240.0f, 527.0f, 257.0f, 569.5f)
        quadTo(274.0f, 612.0f, 310.0f, 648.0f)
        lineTo(320.0f, 658.0f)
        lineTo(320.0f, 600.0f)
        quadTo(320.0f, 583.0f, 331.5f, 571.5f)
        quadTo(343.0f, 560.0f, 360.0f, 560.0f)
        quadTo(377.0f, 560.0f, 388.5f, 571.5f)
        quadTo(400.0f, 583.0f, 400.0f, 600.0f)
        lineTo(400.0f, 760.0f)
        quadTo(400.0f, 777.0f, 388.5f, 788.5f)
        quadTo(377.0f, 800.0f, 360.0f, 800.0f)
        lineTo(200.0f, 800.0f)
        close()
    }.build().also { cache = it }
