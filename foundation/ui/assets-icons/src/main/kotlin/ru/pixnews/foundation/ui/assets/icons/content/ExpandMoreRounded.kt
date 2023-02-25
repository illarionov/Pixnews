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
internal val ExpandMoreRoundedUnfilled400w0g24dp: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "ExpandMoreRoundedUnfilled400w0g24dp",
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
        moveTo(480.0f, 598.0f)
        quadTo(472.0f, 598.0f, 465.0f, 595.5f)
        quadTo(458.0f, 593.0f, 452.0f, 587.0f)
        lineTo(267.0f, 402.0f)
        quadTo(256.0f, 391.0f, 256.5f, 374.5f)
        quadTo(257.0f, 358.0f, 268.0f, 347.0f)
        quadTo(279.0f, 336.0f, 296.0f, 336.0f)
        quadTo(313.0f, 336.0f, 324.0f, 347.0f)
        lineTo(480.0f, 503.0f)
        lineTo(637.0f, 346.0f)
        quadTo(648.0f, 335.0f, 664.5f, 335.5f)
        quadTo(681.0f, 336.0f, 692.0f, 347.0f)
        quadTo(703.0f, 358.0f, 703.0f, 375.0f)
        quadTo(703.0f, 392.0f, 692.0f, 403.0f)
        lineTo(508.0f, 587.0f)
        quadTo(502.0f, 593.0f, 495.0f, 595.5f)
        quadTo(488.0f, 598.0f, 480.0f, 598.0f)
        close()
    }.build().also { cache = it }
