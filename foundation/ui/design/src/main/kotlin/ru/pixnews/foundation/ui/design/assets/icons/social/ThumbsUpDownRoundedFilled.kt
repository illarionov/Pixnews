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
package ru.pixnews.foundation.ui.design.assets.icons.social

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val ThumbsUpDownRoundedFilled400w0g24dpx: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "ThumbsUpDownRoundedFilled400w0g24dpx",
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
        moveTo(60.0f, 560.0f)
        quadTo(36.0f, 560.0f, 18.0f, 542.0f)
        quadTo(0.0f, 524.0f, 0.0f, 500.0f)
        lineTo(0.0f, 234.0f)
        quadTo(0.0f, 222.0f, 5.0f, 210.5f)
        quadTo(10.0f, 199.0f, 18.0f, 191.0f)
        lineTo(179.0f, 30.0f)
        quadTo(187.0f, 22.0f, 200.5f, 20.0f)
        quadTo(214.0f, 18.0f, 225.0f, 23.0f)
        quadTo(236.0f, 28.0f, 241.5f, 40.0f)
        quadTo(247.0f, 52.0f, 245.0f, 63.0f)
        lineTo(214.0f, 200.0f)
        lineTo(420.0f, 200.0f)
        quadTo(456.0f, 200.0f, 475.5f, 233.0f)
        quadTo(495.0f, 266.0f, 481.0f, 299.0f)
        lineTo(386.0f, 523.0f)
        quadTo(379.0f, 540.0f, 364.0f, 550.0f)
        quadTo(349.0f, 560.0f, 331.0f, 560.0f)
        lineTo(60.0f, 560.0f)
        close()
        moveTo(735.0f, 938.0f)
        quadTo(724.0f, 933.0f, 718.5f, 920.5f)
        quadTo(713.0f, 908.0f, 715.0f, 897.0f)
        lineTo(746.0f, 760.0f)
        lineTo(540.0f, 760.0f)
        quadTo(504.0f, 760.0f, 484.5f, 727.0f)
        quadTo(465.0f, 694.0f, 479.0f, 661.0f)
        lineTo(574.0f, 437.0f)
        quadTo(581.0f, 420.0f, 596.0f, 410.0f)
        quadTo(611.0f, 400.0f, 629.0f, 400.0f)
        lineTo(900.0f, 400.0f)
        quadTo(924.0f, 400.0f, 942.0f, 418.0f)
        quadTo(960.0f, 436.0f, 960.0f, 460.0f)
        lineTo(960.0f, 726.0f)
        quadTo(960.0f, 738.0f, 955.0f, 749.5f)
        quadTo(950.0f, 761.0f, 942.0f, 769.0f)
        lineTo(781.0f, 930.0f)
        quadTo(773.0f, 938.0f, 759.5f, 940.5f)
        quadTo(746.0f, 943.0f, 735.0f, 938.0f)
        close()
    }.build().also { cache = it }
