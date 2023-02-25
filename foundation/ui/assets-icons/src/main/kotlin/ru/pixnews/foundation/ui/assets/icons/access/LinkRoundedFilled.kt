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
internal val LinkRoundedFilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "LinkRoundedFilled400w0g24dp",
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
        moveTo(280.0f, 680.0f)
        quadTo(195.0f, 680.0f, 137.5f, 622.5f)
        quadTo(80.0f, 565.0f, 80.0f, 480.0f)
        quadTo(80.0f, 395.0f, 137.5f, 337.5f)
        quadTo(195.0f, 280.0f, 280.0f, 280.0f)
        lineTo(420.0f, 280.0f)
        quadTo(432.75f, 280.0f, 441.38f, 288.67f)
        quadTo(450.0f, 297.35f, 450.0f, 310.17f)
        quadTo(450.0f, 323.0f, 441.38f, 331.5f)
        quadTo(432.75f, 340.0f, 420.0f, 340.0f)
        lineTo(280.0f, 340.0f)
        quadTo(220.0f, 340.0f, 180.0f, 380.0f)
        quadTo(140.0f, 420.0f, 140.0f, 480.0f)
        quadTo(140.0f, 540.0f, 180.0f, 580.0f)
        quadTo(220.0f, 620.0f, 280.0f, 620.0f)
        lineTo(420.0f, 620.0f)
        quadTo(432.75f, 620.0f, 441.38f, 628.67f)
        quadTo(450.0f, 637.35f, 450.0f, 650.17f)
        quadTo(450.0f, 663.0f, 441.38f, 671.5f)
        quadTo(432.75f, 680.0f, 420.0f, 680.0f)
        lineTo(280.0f, 680.0f)
        close()
        moveTo(355.0f, 510.0f)
        quadTo(342.25f, 510.0f, 333.63f, 501.33f)
        quadTo(325.0f, 492.65f, 325.0f, 479.83f)
        quadTo(325.0f, 467.0f, 333.63f, 458.5f)
        quadTo(342.25f, 450.0f, 355.0f, 450.0f)
        lineTo(605.0f, 450.0f)
        quadTo(617.75f, 450.0f, 626.38f, 458.67f)
        quadTo(635.0f, 467.35f, 635.0f, 480.17f)
        quadTo(635.0f, 493.0f, 626.38f, 501.5f)
        quadTo(617.75f, 510.0f, 605.0f, 510.0f)
        lineTo(355.0f, 510.0f)
        close()
        moveTo(540.0f, 680.0f)
        quadTo(527.25f, 680.0f, 518.63f, 671.33f)
        quadTo(510.0f, 662.65f, 510.0f, 649.83f)
        quadTo(510.0f, 637.0f, 518.63f, 628.5f)
        quadTo(527.25f, 620.0f, 540.0f, 620.0f)
        lineTo(680.0f, 620.0f)
        quadTo(740.0f, 620.0f, 780.0f, 580.0f)
        quadTo(820.0f, 540.0f, 820.0f, 480.0f)
        quadTo(820.0f, 420.0f, 780.0f, 380.0f)
        quadTo(740.0f, 340.0f, 680.0f, 340.0f)
        lineTo(540.0f, 340.0f)
        quadTo(527.25f, 340.0f, 518.63f, 331.33f)
        quadTo(510.0f, 322.65f, 510.0f, 309.83f)
        quadTo(510.0f, 297.0f, 518.63f, 288.5f)
        quadTo(527.25f, 280.0f, 540.0f, 280.0f)
        lineTo(680.0f, 280.0f)
        quadTo(765.0f, 280.0f, 822.5f, 337.5f)
        quadTo(880.0f, 395.0f, 880.0f, 480.0f)
        quadTo(880.0f, 565.0f, 822.5f, 622.5f)
        quadTo(765.0f, 680.0f, 680.0f, 680.0f)
        lineTo(540.0f, 680.0f)
        close()
    }.build().also { cache = it }
