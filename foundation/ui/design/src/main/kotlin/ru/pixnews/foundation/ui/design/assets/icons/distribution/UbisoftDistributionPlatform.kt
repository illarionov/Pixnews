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
package ru.pixnews.foundation.ui.design.assets.icons.distribution

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val UbisoftDistributionPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "UbisoftDistributionPlatform",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f,
    ).path(
        fill = SolidColor(Color(0xff000000)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(22.195f, 11.989f)
        curveTo(21.956f, 0.721f, 6.972f, -3.483f, 1.198f, 7.081f)
        curveTo(1.457f, 7.27f, 1.804f, 7.518f, 2.063f, 7.697f)
        curveTo(1.637f, 8.589f, 1.336f, 9.535f, 1.168f, 10.509f)
        curveTo(1.057f, 11.135f, 1.0f, 11.77f, 1.0f, 12.407f)
        curveTo(1.0f, 18.25f, 5.75f, 23.0f, 11.602f, 23.0f)
        curveTo(17.455f, 23.0f, 22.195f, 18.261f, 22.195f, 12.407f)
        verticalLineTo(11.99f)
        verticalLineTo(11.989f)
        close()
        moveTo(3.614f, 13.877f)
        curveTo(3.475f, 15.019f, 3.564f, 15.377f, 3.564f, 15.516f)
        lineTo(3.306f, 15.606f)
        curveTo(3.207f, 15.417f, 2.967f, 14.752f, 2.859f, 13.857f)
        curveTo(2.58f, 10.449f, 4.905f, 7.38f, 8.453f, 6.802f)
        curveTo(11.702f, 6.326f, 14.812f, 8.342f, 15.537f, 11.164f)
        lineTo(15.278f, 11.254f)
        curveTo(15.199f, 11.174f, 15.069f, 10.946f, 14.573f, 10.449f)
        curveTo(10.648f, 6.525f, 4.487f, 8.322f, 3.613f, 13.877f)
        horizontalLineTo(3.614f)
        close()
        moveTo(13.699f, 15.785f)
        curveTo(13.433f, 16.169f, 13.079f, 16.482f, 12.665f, 16.698f)
        curveTo(12.252f, 16.915f, 11.792f, 17.028f, 11.325f, 17.027f)
        curveTo(10.945f, 17.028f, 10.569f, 16.953f, 10.217f, 16.808f)
        curveTo(9.866f, 16.663f, 9.547f, 16.45f, 9.279f, 16.181f)
        curveTo(9.01f, 15.913f, 8.797f, 15.594f, 8.652f, 15.242f)
        curveTo(8.507f, 14.891f, 8.432f, 14.515f, 8.433f, 14.135f)
        curveTo(8.434f, 13.405f, 8.712f, 12.702f, 9.209f, 12.168f)
        curveTo(9.707f, 11.633f, 10.388f, 11.307f, 11.116f, 11.253f)
        curveTo(12.049f, 11.213f, 12.924f, 11.72f, 13.331f, 12.534f)
        curveTo(13.55f, 12.973f, 13.627f, 13.468f, 13.553f, 13.953f)
        curveTo(13.478f, 14.437f, 13.255f, 14.887f, 12.913f, 15.238f)
        curveTo(13.182f, 15.427f, 13.441f, 15.605f, 13.698f, 15.784f)
        lineTo(13.699f, 15.785f)
        close()
        moveTo(19.731f, 15.895f)
        curveTo(18.201f, 19.362f, 15.05f, 21.18f, 11.692f, 21.131f)
        curveTo(5.244f, 20.813f, 3.365f, 13.37f, 7.677f, 10.687f)
        lineTo(7.866f, 10.876f)
        curveTo(7.797f, 10.975f, 7.538f, 11.174f, 7.141f, 11.96f)
        curveTo(6.674f, 12.914f, 6.525f, 13.867f, 6.585f, 14.464f)
        curveTo(6.923f, 19.662f, 14.206f, 20.725f, 16.709f, 15.577f)
        curveTo(19.899f, 8.532f, 11.434f, 1.367f, 3.921f, 6.843f)
        lineTo(3.752f, 6.674f)
        curveTo(5.73f, 3.574f, 9.595f, 2.192f, 13.281f, 3.116f)
        curveTo(18.916f, 4.537f, 21.946f, 10.41f, 19.73f, 15.895f)
        horizontalLineTo(19.731f)
        close()
    }.build().also { cache = it }
