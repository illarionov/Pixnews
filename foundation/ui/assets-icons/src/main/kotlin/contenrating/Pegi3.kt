/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.contenrating

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val Pegi3: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Pegi3",
        defaultWidth = 119.dp,
        defaultHeight = 144.dp,
        viewportWidth = 119.0f,
        viewportHeight = 144.0f,
    ).group(
        rotate = 0.0f,
        pivotX = 0.0f,
        pivotY = 0.0f,
        scaleX = 1.0f,
        scaleY = 1.0f,
        translationX = 0.0f,
        translationY = 0.0f,
        clipPathData = emptyList(),
    ) {
        path(
            fill = SolidColor(Color(0xffa5c400)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(118.03f, 0.0f)
            horizontalLineTo(0.0f)
            verticalLineTo(118.03f)
            horizontalLineTo(118.03f)
            verticalLineTo(0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(50.99f, 49.57f)
            horizontalLineTo(55.01f)
            curveTo(61.38f, 49.57f, 62.56f, 46.26f, 62.56f, 40.48f)
            curveTo(62.56f, 33.28f, 61.15f, 30.92f, 57.6f, 30.92f)
            curveTo(54.65f, 30.92f, 53.0f, 33.4f, 53.0f, 39.18f)
            verticalLineTo(42.49f)
            horizontalLineTo(35.65f)
            curveTo(35.65f, 24.9f, 41.2f, 15.1f, 58.9f, 15.1f)
            curveTo(72.48f, 15.1f, 80.98f, 22.89f, 80.98f, 38.95f)
            curveTo(80.98f, 49.57f, 77.67f, 54.05f, 72.48f, 56.06f)
            curveTo(78.61f, 59.48f, 82.16f, 63.14f, 82.16f, 75.77f)
            curveTo(82.16f, 92.53f, 75.9f, 102.69f, 57.96f, 102.69f)
            curveTo(37.06f, 102.69f, 34.35f, 89.11f, 34.47f, 71.17f)
            horizontalLineTo(52.65f)
            verticalLineTo(78.96f)
            curveTo(52.65f, 83.68f, 54.53f, 85.69f, 57.25f, 85.69f)
            curveTo(60.79f, 85.69f, 63.03f, 83.21f, 63.03f, 75.3f)
            curveTo(63.03f, 66.57f, 61.15f, 63.5f, 54.53f, 63.5f)
            horizontalLineTo(50.99f)
            verticalLineTo(49.57f)
            horizontalLineTo(50.99f)
            close()
        }
        path(
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
            moveTo(0.0f, 144.0f)
            horizontalLineTo(118.03f)
            verticalLineTo(0.0f)
            horizontalLineTo(0.0f)
            verticalLineTo(144.0f)
            close()
            moveTo(115.67f, 115.67f)
            horizontalLineTo(2.36f)
            verticalLineTo(2.36f)
            horizontalLineTo(115.67f)
            verticalLineTo(115.67f)
            horizontalLineTo(115.67f)
            close()
        }
        path(
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
            moveTo(105.71f, 4.72f)
            verticalLineTo(5.6f)
            horizontalLineTo(103.84f)
            verticalLineTo(11.51f)
            horizontalLineTo(102.74f)
            verticalLineTo(5.6f)
            horizontalLineTo(100.87f)
            verticalLineTo(4.72f)
            horizontalLineTo(105.71f)
            close()
            moveTo(113.31f, 11.51f)
            horizontalLineTo(112.22f)
            verticalLineTo(6.16f)
            horizontalLineTo(112.18f)
            lineTo(110.3f, 11.51f)
            horizontalLineTo(109.4f)
            lineTo(107.62f, 6.16f)
            horizontalLineTo(107.59f)
            verticalLineTo(11.51f)
            horizontalLineTo(106.49f)
            verticalLineTo(4.72f)
            horizontalLineTo(108.23f)
            lineTo(109.92f, 9.77f)
            horizontalLineTo(109.96f)
            lineTo(111.6f, 4.72f)
            horizontalLineTo(113.31f)
            verticalLineTo(11.51f)
            horizontalLineTo(113.31f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(11.34f, 126.2f)
            horizontalLineTo(11.38f)
            lineTo(13.3f, 135.15f)
            horizontalLineTo(15.18f)
            lineTo(17.78f, 124.19f)
            horizontalLineTo(15.98f)
            lineTo(14.28f, 133.15f)
            horizontalLineTo(14.24f)
            lineTo(12.42f, 124.19f)
            horizontalLineTo(10.4f)
            lineTo(8.54f, 133.15f)
            horizontalLineTo(8.5f)
            lineTo(6.74f, 124.19f)
            horizontalLineTo(4.94f)
            lineTo(7.5f, 135.15f)
            horizontalLineTo(9.5f)
            lineTo(11.34f, 126.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(24.59f, 126.2f)
            horizontalLineTo(24.64f)
            lineTo(26.56f, 135.15f)
            horizontalLineTo(28.43f)
            lineTo(31.03f, 124.19f)
            horizontalLineTo(29.23f)
            lineTo(27.54f, 133.15f)
            horizontalLineTo(27.49f)
            lineTo(25.67f, 124.19f)
            horizontalLineTo(23.65f)
            lineTo(21.79f, 133.15f)
            horizontalLineTo(21.75f)
            lineTo(19.99f, 124.19f)
            horizontalLineTo(18.19f)
            lineTo(20.75f, 135.15f)
            horizontalLineTo(22.75f)
            lineTo(24.59f, 126.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(37.84f, 126.2f)
            horizontalLineTo(37.88f)
            lineTo(39.81f, 135.15f)
            horizontalLineTo(41.69f)
            lineTo(44.28f, 124.19f)
            horizontalLineTo(42.49f)
            lineTo(40.79f, 133.15f)
            horizontalLineTo(40.75f)
            lineTo(38.93f, 124.19f)
            horizontalLineTo(36.9f)
            lineTo(35.04f, 133.15f)
            horizontalLineTo(35.0f)
            lineTo(33.24f, 124.19f)
            horizontalLineTo(31.44f)
            lineTo(34.0f, 135.15f)
            horizontalLineTo(36.0f)
            lineTo(37.84f, 126.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(47.13f, 132.99f)
            horizontalLineTo(45.25f)
            verticalLineTo(135.15f)
            horizontalLineTo(47.13f)
            verticalLineTo(132.99f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(51.38f, 129.67f)
            curveTo(51.38f, 127.18f, 51.69f, 125.38f, 53.59f, 125.38f)
            curveTo(55.19f, 125.38f, 55.59f, 127.06f, 55.59f, 129.47f)
            curveTo(55.59f, 132.29f, 55.19f, 133.96f, 53.59f, 133.96f)
            curveTo(51.75f, 133.96f, 51.38f, 131.8f, 51.38f, 129.67f)
            close()
            moveTo(49.77f, 138.75f)
            horizontalLineTo(51.48f)
            verticalLineTo(133.9f)
            horizontalLineTo(51.53f)
            curveTo(51.92f, 134.84f, 52.84f, 135.44f, 53.82f, 135.44f)
            curveTo(56.66f, 135.44f, 57.39f, 131.96f, 57.39f, 129.47f)
            curveTo(57.39f, 126.22f, 56.41f, 123.91f, 53.82f, 123.91f)
            curveTo(52.63f, 123.91f, 51.67f, 124.73f, 51.44f, 125.54f)
            horizontalLineTo(51.4f)
            verticalLineTo(124.19f)
            horizontalLineTo(49.77f)
            verticalLineTo(138.75f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(60.73f, 128.52f)
            verticalLineTo(128.12f)
            curveTo(60.73f, 127.47f, 60.93f, 125.26f, 62.73f, 125.26f)
            curveTo(64.25f, 125.26f, 64.63f, 126.71f, 64.63f, 127.93f)
            verticalLineTo(128.52f)
            horizontalLineTo(60.73f)
            close()
            moveTo(66.43f, 129.88f)
            verticalLineTo(129.18f)
            curveTo(66.43f, 126.83f, 66.12f, 123.91f, 62.77f, 123.91f)
            curveTo(59.4f, 123.91f, 58.87f, 127.07f, 58.87f, 129.82f)
            curveTo(58.87f, 133.58f, 59.99f, 135.44f, 62.67f, 135.44f)
            curveTo(65.65f, 135.44f, 66.35f, 132.72f, 66.35f, 131.55f)
            horizontalLineTo(64.63f)
            curveTo(64.63f, 132.37f, 64.41f, 134.09f, 62.77f, 134.09f)
            curveTo(60.67f, 134.09f, 60.67f, 131.55f, 60.67f, 129.88f)
            horizontalLineTo(66.43f)
            verticalLineTo(129.88f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(71.7f, 125.38f)
            curveTo(73.55f, 125.38f, 73.91f, 127.54f, 73.91f, 129.67f)
            curveTo(73.91f, 131.92f, 73.55f, 133.68f, 71.68f, 133.68f)
            curveTo(69.97f, 133.68f, 69.7f, 131.45f, 69.7f, 129.67f)
            curveTo(69.7f, 127.06f, 70.11f, 125.38f, 71.7f, 125.38f)
            close()
            moveTo(75.53f, 124.19f)
            horizontalLineTo(73.89f)
            verticalLineTo(125.59f)
            horizontalLineTo(73.85f)
            curveTo(73.63f, 124.72f, 72.67f, 123.91f, 71.48f, 123.91f)
            curveTo(68.7f, 123.91f, 67.91f, 126.71f, 67.91f, 129.67f)
            curveTo(67.91f, 131.13f, 68.11f, 135.15f, 71.3f, 135.15f)
            curveTo(72.38f, 135.15f, 73.4f, 134.6f, 73.77f, 133.68f)
            horizontalLineTo(73.81f)
            verticalLineTo(135.11f)
            curveTo(73.81f, 135.87f, 73.89f, 137.56f, 71.68f, 137.56f)
            curveTo(70.78f, 137.56f, 70.01f, 137.17f, 69.93f, 136.18f)
            horizontalLineTo(68.21f)
            curveTo(68.49f, 138.91f, 71.13f, 138.91f, 71.8f, 138.91f)
            curveTo(74.01f, 138.91f, 75.53f, 137.73f, 75.53f, 134.46f)
            verticalLineTo(124.19f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(77.89f, 135.15f)
            horizontalLineTo(79.6f)
            verticalLineTo(124.19f)
            horizontalLineTo(77.89f)
            verticalLineTo(135.15f)
            close()
            moveTo(77.89f, 122.52f)
            horizontalLineTo(79.6f)
            verticalLineTo(120.55f)
            horizontalLineTo(77.89f)
            verticalLineTo(122.52f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(84.22f, 132.99f)
            horizontalLineTo(82.34f)
            verticalLineTo(135.15f)
            horizontalLineTo(84.22f)
            verticalLineTo(132.99f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(86.96f, 135.15f)
            horizontalLineTo(88.68f)
            verticalLineTo(124.19f)
            horizontalLineTo(86.96f)
            verticalLineTo(135.15f)
            close()
            moveTo(86.96f, 122.52f)
            horizontalLineTo(88.68f)
            verticalLineTo(120.55f)
            horizontalLineTo(86.96f)
            verticalLineTo(122.52f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(91.03f, 135.15f)
            horizontalLineTo(92.74f)
            verticalLineTo(127.59f)
            curveTo(92.74f, 125.99f, 93.99f, 125.38f, 94.91f, 125.38f)
            curveTo(96.46f, 125.38f, 96.53f, 126.75f, 96.53f, 127.59f)
            verticalLineTo(135.15f)
            horizontalLineTo(98.24f)
            verticalLineTo(127.38f)
            curveTo(98.24f, 126.1f, 98.24f, 123.91f, 95.36f, 123.91f)
            curveTo(94.3f, 123.91f, 93.15f, 124.48f, 92.71f, 125.49f)
            horizontalLineTo(92.66f)
            verticalLineTo(124.19f)
            horizontalLineTo(91.03f)
            verticalLineTo(135.15f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(101.01f, 135.15f)
            horizontalLineTo(102.73f)
            verticalLineTo(125.54f)
            horizontalLineTo(104.61f)
            verticalLineTo(124.19f)
            horizontalLineTo(102.73f)
            verticalLineTo(122.91f)
            curveTo(102.73f, 122.15f, 103.1f, 121.86f, 103.83f, 121.86f)
            horizontalLineTo(104.63f)
            verticalLineTo(120.39f)
            horizontalLineTo(103.38f)
            curveTo(101.72f, 120.39f, 101.01f, 121.25f, 101.01f, 122.91f)
            verticalLineTo(124.19f)
            horizontalLineTo(99.41f)
            verticalLineTo(125.54f)
            horizontalLineTo(101.01f)
            verticalLineTo(135.15f)
            close()
        }
        path(
            fill = SolidColor(Color(0xffffffff)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(105.26f, 129.67f)
            curveTo(105.26f, 132.76f, 106.04f, 135.44f, 109.2f, 135.44f)
            curveTo(112.35f, 135.44f, 113.15f, 132.76f, 113.15f, 129.67f)
            curveTo(113.15f, 126.58f, 112.37f, 123.91f, 109.2f, 123.91f)
            curveTo(106.06f, 123.91f, 105.26f, 126.58f, 105.26f, 129.67f)
            close()
            moveTo(109.16f, 134.09f)
            curveTo(107.4f, 134.09f, 107.06f, 132.04f, 107.06f, 129.67f)
            curveTo(107.06f, 127.3f, 107.43f, 125.26f, 109.16f, 125.26f)
            curveTo(111.0f, 125.26f, 111.35f, 127.3f, 111.35f, 129.67f)
            curveTo(111.35f, 132.04f, 110.98f, 134.09f, 109.16f, 134.09f)
            close()
        }
    }
        .build().also { cache = it }
