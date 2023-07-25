/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.distribution

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val GogDistributionPlatform: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "GogDistributionPlatform",
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
        pathFillType = PathFillType.EvenOdd,
    ) {
        moveTo(3.35f, 1.0f)
        curveTo(2.052f, 1.0f, 1.0f, 2.052f, 1.0f, 3.35f)
        verticalLineTo(20.65f)
        curveTo(1.0f, 21.948f, 2.052f, 23.0f, 3.35f, 23.0f)
        horizontalLineTo(20.65f)
        curveTo(21.948f, 23.0f, 23.0f, 21.948f, 23.0f, 20.65f)
        verticalLineTo(3.35f)
        curveTo(23.0f, 2.052f, 21.948f, 1.0f, 20.65f, 1.0f)
        horizontalLineTo(3.35f)
        close()
        moveTo(10.306f, 5.0f)
        curveTo(9.745f, 5.0f, 9.294f, 5.444f, 9.294f, 5.997f)
        verticalLineTo(9.334f)
        curveTo(9.294f, 9.599f, 9.401f, 9.852f, 9.59f, 10.039f)
        curveTo(9.78f, 10.226f, 10.038f, 10.331f, 10.306f, 10.331f)
        horizontalLineTo(13.694f)
        curveTo(13.962f, 10.331f, 14.22f, 10.226f, 14.41f, 10.039f)
        curveTo(14.599f, 9.852f, 14.706f, 9.599f, 14.706f, 9.334f)
        verticalLineTo(5.997f)
        curveTo(14.706f, 5.444f, 14.255f, 5.0f, 13.694f, 5.0f)
        horizontalLineTo(10.306f)
        close()
        moveTo(10.988f, 6.333f)
        horizontalLineTo(13.012f)
        curveTo(13.102f, 6.333f, 13.189f, 6.368f, 13.253f, 6.431f)
        curveTo(13.317f, 6.494f, 13.353f, 6.58f, 13.353f, 6.669f)
        verticalLineTo(8.663f)
        curveTo(13.353f, 8.752f, 13.317f, 8.837f, 13.253f, 8.9f)
        curveTo(13.189f, 8.963f, 13.102f, 8.998f, 13.012f, 8.998f)
        horizontalLineTo(10.988f)
        curveTo(10.898f, 8.998f, 10.811f, 8.963f, 10.747f, 8.9f)
        curveTo(10.683f, 8.837f, 10.647f, 8.752f, 10.647f, 8.663f)
        verticalLineTo(6.669f)
        curveTo(10.647f, 6.58f, 10.683f, 6.494f, 10.747f, 6.431f)
        curveTo(10.811f, 6.368f, 10.898f, 6.333f, 10.988f, 6.333f)
        close()
        moveTo(4.212f, 5.0f)
        curveTo(3.944f, 5.0f, 3.686f, 5.105f, 3.496f, 5.292f)
        curveTo(3.307f, 5.479f, 3.2f, 5.733f, 3.2f, 5.997f)
        verticalLineTo(9.334f)
        curveTo(3.2f, 9.887f, 3.651f, 10.331f, 4.212f, 10.331f)
        horizontalLineTo(6.588f)
        verticalLineTo(8.998f)
        horizontalLineTo(4.894f)
        curveTo(4.707f, 8.998f, 4.553f, 8.847f, 4.553f, 8.663f)
        verticalLineTo(6.669f)
        curveTo(4.553f, 6.485f, 4.707f, 6.333f, 4.894f, 6.333f)
        horizontalLineTo(6.918f)
        curveTo(7.008f, 6.333f, 7.095f, 6.368f, 7.159f, 6.431f)
        curveTo(7.223f, 6.494f, 7.259f, 6.58f, 7.259f, 6.669f)
        verticalLineTo(10.667f)
        curveTo(7.259f, 10.756f, 7.223f, 10.842f, 7.159f, 10.905f)
        curveTo(7.095f, 10.968f, 7.008f, 11.003f, 6.918f, 11.003f)
        horizontalLineTo(3.2f)
        verticalLineTo(12.336f)
        horizontalLineTo(7.6f)
        curveTo(8.15f, 12.336f, 8.612f, 11.881f, 8.612f, 11.328f)
        verticalLineTo(5.997f)
        curveTo(8.612f, 5.733f, 8.505f, 5.479f, 8.316f, 5.292f)
        curveTo(8.126f, 5.105f, 7.868f, 5.0f, 7.6f, 5.0f)
        horizontalLineTo(4.212f)
        close()
        moveTo(16.4f, 5.0f)
        curveTo(15.85f, 5.0f, 15.388f, 5.444f, 15.388f, 5.997f)
        verticalLineTo(9.334f)
        curveTo(15.388f, 9.887f, 15.85f, 10.331f, 16.4f, 10.331f)
        horizontalLineTo(18.765f)
        verticalLineTo(8.998f)
        horizontalLineTo(17.082f)
        curveTo(16.895f, 8.998f, 16.741f, 8.847f, 16.741f, 8.663f)
        verticalLineTo(6.669f)
        curveTo(16.741f, 6.485f, 16.895f, 6.333f, 17.082f, 6.333f)
        horizontalLineTo(19.106f)
        curveTo(19.293f, 6.333f, 19.447f, 6.485f, 19.447f, 6.669f)
        verticalLineTo(10.667f)
        curveTo(19.447f, 10.851f, 19.293f, 11.003f, 19.106f, 11.003f)
        horizontalLineTo(15.388f)
        verticalLineTo(12.336f)
        horizontalLineTo(19.788f)
        curveTo(20.349f, 12.336f, 20.8f, 11.881f, 20.8f, 11.328f)
        verticalLineTo(5.997f)
        curveTo(20.8f, 5.444f, 20.349f, 5.0f, 19.788f, 5.0f)
        horizontalLineTo(16.4f)
        close()
        moveTo(9.635f, 13.669f)
        curveTo(9.074f, 13.669f, 8.612f, 14.113f, 8.612f, 14.666f)
        verticalLineTo(18.003f)
        curveTo(8.612f, 18.556f, 9.074f, 19.0f, 9.635f, 19.0f)
        horizontalLineTo(13.012f)
        curveTo(13.573f, 19.0f, 14.035f, 18.556f, 14.035f, 18.003f)
        verticalLineTo(14.666f)
        curveTo(14.035f, 14.113f, 13.573f, 13.669f, 13.012f, 13.669f)
        horizontalLineTo(9.635f)
        close()
        moveTo(10.306f, 15.002f)
        horizontalLineTo(12.341f)
        curveTo(12.386f, 15.0f, 12.431f, 15.008f, 12.473f, 15.024f)
        curveTo(12.515f, 15.04f, 12.554f, 15.065f, 12.585f, 15.097f)
        curveTo(12.618f, 15.128f, 12.643f, 15.166f, 12.659f, 15.207f)
        curveTo(12.676f, 15.249f, 12.684f, 15.293f, 12.682f, 15.337f)
        verticalLineTo(17.331f)
        curveTo(12.682f, 17.42f, 12.646f, 17.506f, 12.582f, 17.569f)
        curveTo(12.518f, 17.632f, 12.431f, 17.667f, 12.341f, 17.667f)
        horizontalLineTo(10.306f)
        curveTo(10.119f, 17.667f, 9.965f, 17.515f, 9.965f, 17.331f)
        verticalLineTo(15.337f)
        curveTo(9.965f, 15.248f, 10.001f, 15.163f, 10.065f, 15.1f)
        curveTo(10.129f, 15.037f, 10.216f, 15.002f, 10.306f, 15.002f)
        close()
        moveTo(4.212f, 13.669f)
        curveTo(3.651f, 13.669f, 3.2f, 14.113f, 3.2f, 14.666f)
        verticalLineTo(18.003f)
        curveTo(3.2f, 18.556f, 3.651f, 19.0f, 4.212f, 19.0f)
        horizontalLineTo(7.941f)
        verticalLineTo(17.667f)
        horizontalLineTo(4.894f)
        curveTo(4.707f, 17.667f, 4.553f, 17.515f, 4.553f, 17.331f)
        verticalLineTo(15.337f)
        curveTo(4.553f, 15.153f, 4.707f, 15.002f, 4.894f, 15.002f)
        horizontalLineTo(7.941f)
        verticalLineTo(13.669f)
        horizontalLineTo(4.212f)
        close()
        moveTo(15.718f, 13.669f)
        curveTo(15.157f, 13.669f, 14.706f, 14.113f, 14.706f, 14.666f)
        verticalLineTo(19.0f)
        horizontalLineTo(16.059f)
        verticalLineTo(15.337f)
        curveTo(16.059f, 15.248f, 16.095f, 15.163f, 16.159f, 15.1f)
        curveTo(16.223f, 15.037f, 16.31f, 15.002f, 16.4f, 15.002f)
        horizontalLineTo(17.082f)
        verticalLineTo(19.0f)
        horizontalLineTo(18.435f)
        verticalLineTo(15.002f)
        horizontalLineTo(19.447f)
        verticalLineTo(19.0f)
        horizontalLineTo(20.8f)
        verticalLineTo(13.669f)
        horizontalLineTo(15.718f)
        close()
    }.build().also { cache = it }
