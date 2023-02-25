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
package ru.pixnews.foundation.ui.design.assets.icons.externallink

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val InstagramLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "InstagramLink",
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
        moveTo(12.0f, 1.0f)
        curveTo(9.012f, 1.0f, 8.639f, 1.014f, 7.465f, 1.066f)
        curveTo(6.294f, 1.121f, 5.496f, 1.305f, 4.795f, 1.577f)
        curveTo(4.072f, 1.858f, 3.458f, 2.235f, 2.846f, 2.846f)
        curveTo(2.235f, 3.458f, 1.857f, 4.071f, 1.577f, 4.795f)
        curveTo(1.305f, 5.496f, 1.12f, 6.294f, 1.066f, 7.465f)
        curveTo(1.011f, 8.639f, 1.0f, 9.012f, 1.0f, 12.0f)
        curveTo(1.0f, 14.988f, 1.014f, 15.361f, 1.066f, 16.535f)
        curveTo(1.121f, 17.705f, 1.305f, 18.504f, 1.577f, 19.205f)
        curveTo(1.854f, 19.939f, 2.287f, 20.604f, 2.846f, 21.154f)
        curveTo(3.395f, 21.714f, 4.061f, 22.147f, 4.795f, 22.423f)
        curveTo(5.497f, 22.694f, 6.295f, 22.88f, 7.465f, 22.934f)
        curveTo(8.639f, 22.989f, 9.012f, 23.0f, 12.0f, 23.0f)
        curveTo(14.988f, 23.0f, 15.361f, 22.986f, 16.535f, 22.934f)
        curveTo(17.705f, 22.879f, 18.504f, 22.694f, 19.205f, 22.423f)
        curveTo(19.939f, 22.146f, 20.604f, 21.713f, 21.154f, 21.154f)
        curveTo(21.714f, 20.605f, 22.147f, 19.94f, 22.423f, 19.205f)
        curveTo(22.694f, 18.504f, 22.88f, 17.705f, 22.934f, 16.535f)
        curveTo(22.989f, 15.361f, 23.0f, 14.988f, 23.0f, 12.0f)
        curveTo(23.0f, 9.012f, 22.986f, 8.639f, 22.934f, 7.465f)
        curveTo(22.879f, 6.295f, 22.694f, 5.495f, 22.423f, 4.795f)
        curveTo(22.146f, 4.061f, 21.713f, 3.396f, 21.154f, 2.846f)
        curveTo(20.605f, 2.286f, 19.94f, 1.853f, 19.205f, 1.577f)
        curveTo(18.504f, 1.305f, 17.705f, 1.12f, 16.535f, 1.066f)
        curveTo(15.361f, 1.011f, 14.988f, 1.0f, 12.0f, 1.0f)
        close()
        moveTo(12.0f, 2.98f)
        curveTo(14.936f, 2.98f, 15.286f, 2.995f, 16.446f, 3.045f)
        curveTo(17.518f, 3.095f, 18.1f, 3.273f, 18.487f, 3.425f)
        curveTo(19.002f, 3.624f, 19.367f, 3.863f, 19.754f, 4.247f)
        curveTo(20.138f, 4.632f, 20.376f, 4.998f, 20.575f, 5.513f)
        curveTo(20.726f, 5.9f, 20.905f, 6.482f, 20.954f, 7.554f)
        curveTo(21.006f, 8.715f, 21.018f, 9.063f, 21.018f, 12.0f)
        curveTo(21.018f, 14.937f, 21.004f, 15.286f, 20.95f, 16.446f)
        curveTo(20.894f, 17.518f, 20.716f, 18.1f, 20.564f, 18.487f)
        curveTo(20.385f, 18.965f, 20.104f, 19.397f, 19.74f, 19.754f)
        curveTo(19.385f, 20.118f, 18.953f, 20.399f, 18.475f, 20.575f)
        curveTo(18.09f, 20.726f, 17.499f, 20.905f, 16.427f, 20.954f)
        curveTo(15.259f, 21.006f, 14.915f, 21.018f, 11.972f, 21.018f)
        curveTo(9.029f, 21.018f, 8.685f, 21.004f, 7.518f, 20.95f)
        curveTo(6.445f, 20.894f, 5.854f, 20.716f, 5.469f, 20.564f)
        curveTo(4.991f, 20.388f, 4.559f, 20.106f, 4.205f, 19.74f)
        curveTo(3.837f, 19.387f, 3.554f, 18.955f, 3.38f, 18.475f)
        curveTo(3.228f, 18.09f, 3.051f, 17.499f, 2.995f, 16.427f)
        curveTo(2.953f, 15.272f, 2.939f, 14.915f, 2.939f, 11.986f)
        curveTo(2.939f, 9.057f, 2.953f, 8.699f, 2.995f, 7.53f)
        curveTo(3.051f, 6.458f, 3.228f, 5.867f, 3.38f, 5.483f)
        curveTo(3.572f, 4.96f, 3.819f, 4.602f, 4.205f, 4.217f)
        curveTo(4.589f, 3.832f, 4.947f, 3.585f, 5.469f, 3.393f)
        curveTo(5.854f, 3.241f, 6.432f, 3.063f, 7.505f, 3.007f)
        curveTo(8.673f, 2.966f, 9.017f, 2.953f, 11.959f, 2.953f)
        lineTo(12.0f, 2.98f)
        close()
        moveTo(12.0f, 6.352f)
        curveTo(11.258f, 6.352f, 10.524f, 6.498f, 9.838f, 6.781f)
        curveTo(9.153f, 7.065f, 8.53f, 7.481f, 8.006f, 8.006f)
        curveTo(7.481f, 8.53f, 7.065f, 9.153f, 6.781f, 9.838f)
        curveTo(6.498f, 10.524f, 6.352f, 11.258f, 6.352f, 12.0f)
        curveTo(6.352f, 12.742f, 6.498f, 13.476f, 6.781f, 14.162f)
        curveTo(7.065f, 14.847f, 7.481f, 15.47f, 8.006f, 15.994f)
        curveTo(8.53f, 16.519f, 9.153f, 16.935f, 9.838f, 17.219f)
        curveTo(10.524f, 17.502f, 11.258f, 17.649f, 12.0f, 17.649f)
        curveTo(13.498f, 17.649f, 14.935f, 17.053f, 15.994f, 15.994f)
        curveTo(17.053f, 14.935f, 17.649f, 13.498f, 17.649f, 12.0f)
        curveTo(17.649f, 10.502f, 17.053f, 9.065f, 15.994f, 8.006f)
        curveTo(14.935f, 6.947f, 13.498f, 6.352f, 12.0f, 6.352f)
        close()
        moveTo(12.0f, 15.667f)
        curveTo(9.974f, 15.667f, 8.333f, 14.026f, 8.333f, 12.0f)
        curveTo(8.333f, 9.974f, 9.974f, 8.333f, 12.0f, 8.333f)
        curveTo(14.026f, 8.333f, 15.667f, 9.974f, 15.667f, 12.0f)
        curveTo(15.667f, 14.026f, 14.026f, 15.667f, 12.0f, 15.667f)
        close()
        moveTo(19.192f, 6.129f)
        curveTo(19.179f, 6.47f, 19.035f, 6.794f, 18.789f, 7.031f)
        curveTo(18.542f, 7.268f, 18.214f, 7.4f, 17.872f, 7.4f)
        curveTo(17.53f, 7.4f, 17.202f, 7.268f, 16.956f, 7.031f)
        curveTo(16.709f, 6.794f, 16.565f, 6.47f, 16.552f, 6.129f)
        curveTo(16.552f, 5.779f, 16.691f, 5.443f, 16.939f, 5.195f)
        curveTo(17.186f, 4.948f, 17.522f, 4.809f, 17.872f, 4.809f)
        curveTo(18.222f, 4.809f, 18.558f, 4.948f, 18.806f, 5.195f)
        curveTo(19.053f, 5.443f, 19.192f, 5.779f, 19.192f, 6.129f)
        close()
    }.build().also { cache = it }
