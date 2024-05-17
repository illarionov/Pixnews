/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.externallink

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val IgdbLink: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "IgdbLink",
        defaultWidth = 48.dp,
        defaultHeight = 24.dp,
        viewportWidth = 48.0f,
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
        moveTo(1.031f, 1.0f)
        lineTo(46.969f, 1.0f)
        verticalLineTo(22.991f)
        lineTo(46.216f, 22.885f)
        curveTo(46.057f, 22.862f, 45.876f, 22.836f, 45.674f, 22.807f)
        curveTo(42.596f, 22.365f, 34.56f, 21.21f, 24.675f, 21.21f)
        curveTo(14.126f, 21.21f, 3.876f, 22.48f, 1.81f, 22.858f)
        lineTo(1.031f, 23.0f)
        verticalLineTo(1.0f)
        close()
        moveTo(2.352f, 2.321f)
        verticalLineTo(21.431f)
        curveTo(5.595f, 20.944f, 15.013f, 19.889f, 24.675f, 19.889f)
        curveTo(34.438f, 19.889f, 42.416f, 21.005f, 45.648f, 21.469f)
        verticalLineTo(2.321f)
        lineTo(2.352f, 2.321f)
        close()
    }
        .path(
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
            moveTo(6.132f, 15.659f)
            verticalLineTo(6.115f)
            horizontalLineTo(8.156f)
            verticalLineTo(15.659f)
            horizontalLineTo(6.132f)
            close()
        }
        .path(
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
            moveTo(18.043f, 6.346f)
            curveTo(18.432f, 6.518f, 18.922f, 6.794f, 19.132f, 6.959f)
            lineTo(19.513f, 7.258f)
            curveTo(19.226f, 7.623f, 19.521f, 7.222f, 18.253f, 8.78f)
            curveTo(18.253f, 8.78f, 18.253f, 8.78f, 17.804f, 8.508f)
            curveTo(16.647f, 7.81f, 16.1f, 8.002f, 15.771f, 7.959f)
            curveTo(14.626f, 7.959f, 13.752f, 8.543f, 13.241f, 9.65f)
            curveTo(12.245f, 11.807f, 13.933f, 14.179f, 16.238f, 13.863f)
            curveTo(17.45f, 13.697f, 17.55f, 13.513f, 17.769f, 13.401f)
            curveTo(17.769f, 13.096f, 17.807f, 11.984f, 17.807f, 11.984f)
            horizontalLineTo(15.732f)
            verticalLineTo(10.198f)
            horizontalLineTo(19.872f)
            verticalLineTo(14.38f)
            lineTo(19.145f, 14.88f)
            curveTo(18.734f, 15.164f, 17.88f, 15.518f, 17.176f, 15.698f)
            curveTo(16.118f, 15.969f, 14.959f, 15.841f, 14.959f, 15.841f)
            curveTo(13.879f, 15.626f, 12.856f, 15.271f, 12.131f, 14.555f)
            curveTo(11.487f, 13.919f, 11.072f, 13.06f, 11.072f, 13.06f)
            curveTo(10.615f, 12.079f, 10.54f, 10.331f, 10.91f, 9.283f)
            curveTo(11.461f, 7.722f, 12.738f, 6.538f, 14.367f, 6.077f)
            curveTo(15.329f, 5.805f, 17.116f, 5.935f, 18.043f, 6.346f)
            close()
        }
        .path(
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
            moveTo(24.563f, 13.757f)
            horizontalLineTo(26.801f)
            curveTo(27.721f, 13.477f, 27.83f, 13.461f, 28.4f, 12.891f)
            curveTo(28.88f, 12.211f, 28.966f, 12.266f, 29.059f, 11.013f)
            curveTo(29.109f, 10.349f, 28.878f, 9.55f, 28.452f, 9.029f)
            curveTo(27.959f, 8.499f, 27.931f, 8.485f, 27.582f, 8.283f)
            curveTo(26.997f, 8.052f, 26.955f, 8.043f, 26.536f, 7.965f)
            curveTo(26.182f, 7.965f, 24.589f, 7.965f, 24.589f, 7.965f)
            lineTo(24.563f, 13.757f)
            close()
            moveTo(22.391f, 15.702f)
            lineTo(22.391f, 6.082f)
            horizontalLineTo(26.61f)
            curveTo(27.307f, 6.082f, 27.745f, 6.227f, 28.16f, 6.368f)
            curveTo(28.575f, 6.509f, 29.064f, 6.771f, 29.539f, 7.159f)
            curveTo(30.017f, 7.542f, 30.385f, 7.827f, 30.838f, 8.733f)
            curveTo(31.225f, 9.773f, 31.225f, 10.176f, 31.225f, 10.972f)
            curveTo(31.225f, 12.517f, 30.357f, 13.841f, 29.426f, 14.676f)
            curveTo(28.866f, 15.076f, 28.4f, 15.542f, 26.801f, 15.702f)
            horizontalLineTo(22.391f)
            close()
        }
        .path(
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
            moveTo(33.645f, 15.596f)
            verticalLineTo(6.082f)
            horizontalLineTo(38.695f)
            curveTo(39.49f, 6.082f, 41.115f, 6.578f, 41.253f, 8.56f)
            curveTo(41.302f, 9.009f, 41.144f, 10.05f, 40.121f, 10.626f)
            curveTo(40.667f, 10.856f, 41.76f, 11.665f, 41.76f, 13.051f)
            curveTo(41.893f, 13.846f, 41.586f, 15.468f, 39.295f, 15.596f)
            horizontalLineTo(33.645f)
            close()
            moveTo(35.724f, 7.947f)
            verticalLineTo(9.946f)
            horizontalLineTo(38.015f)
            curveTo(38.836f, 9.946f, 39.121f, 9.271f, 39.161f, 8.933f)
            curveTo(39.161f, 8.341f, 38.553f, 7.947f, 38.349f, 7.947f)
            horizontalLineTo(35.724f)
            close()
            moveTo(35.724f, 13.743f)
            verticalLineTo(11.825f)
            horizontalLineTo(38.975f)
            curveTo(39.161f, 11.825f, 39.76f, 12.156f, 39.76f, 12.845f)
            curveTo(39.76f, 13.242f, 39.345f, 13.743f, 38.695f, 13.743f)
            horizontalLineTo(35.724f)
            close()
        }.build().also { cache = it }
