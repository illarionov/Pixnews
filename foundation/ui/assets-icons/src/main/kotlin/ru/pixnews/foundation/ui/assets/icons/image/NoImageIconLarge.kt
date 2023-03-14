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
package ru.pixnews.foundation.ui.assets.icons.image

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
internal val NoImagePlaceholderLarge: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "no_image_large",
        defaultWidth = 161.dp,
        defaultHeight = 118.dp,
        viewportWidth = 161.0f,
        viewportHeight = 118.0f,
    ).path(
        fill = SolidColor(Color(0xff767680)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(19.03f, 111.0f)
        horizontalLineTo(14.04f)
        lineTo(5.95f, 96.96f)
        horizontalLineTo(5.85f)
        curveTo(5.87f, 97.39f, 5.88f, 97.84f, 5.9f, 98.29f)
        curveTo(5.92f, 98.72f, 5.94f, 99.16f, 5.95f, 99.61f)
        curveTo(5.99f, 100.04f, 6.01f, 100.49f, 6.03f, 100.94f)
        curveTo(6.05f, 101.37f, 6.07f, 101.81f, 6.08f, 102.26f)
        verticalLineTo(111.0f)
        horizontalLineTo(2.57f)
        verticalLineTo(92.44f)
        horizontalLineTo(7.54f)
        lineTo(15.6f, 106.35f)
        horizontalLineTo(15.68f)
        curveTo(15.66f, 105.91f, 15.64f, 105.49f, 15.63f, 105.07f)
        curveTo(15.61f, 104.64f, 15.59f, 104.21f, 15.57f, 103.8f)
        curveTo(15.57f, 103.36f, 15.56f, 102.94f, 15.55f, 102.52f)
        curveTo(15.53f, 102.09f, 15.51f, 101.66f, 15.5f, 101.22f)
        verticalLineTo(92.44f)
        horizontalLineTo(19.03f)
        verticalLineTo(111.0f)
        close()
        moveTo(44.21f, 101.69f)
        curveTo(44.21f, 103.13f, 44.03f, 104.44f, 43.67f, 105.62f)
        curveTo(43.32f, 106.78f, 42.78f, 107.79f, 42.05f, 108.63f)
        curveTo(41.34f, 109.48f, 40.42f, 110.13f, 39.3f, 110.58f)
        curveTo(38.19f, 111.04f, 36.88f, 111.26f, 35.37f, 111.26f)
        curveTo(33.86f, 111.26f, 32.55f, 111.04f, 31.42f, 110.58f)
        curveTo(30.31f, 110.12f, 29.39f, 109.47f, 28.66f, 108.63f)
        curveTo(27.95f, 107.79f, 27.42f, 106.77f, 27.05f, 105.59f)
        curveTo(26.71f, 104.41f, 26.53f, 103.11f, 26.53f, 101.67f)
        curveTo(26.53f, 99.74f, 26.84f, 98.07f, 27.47f, 96.65f)
        curveTo(28.11f, 95.23f, 29.08f, 94.13f, 30.38f, 93.35f)
        curveTo(31.7f, 92.55f, 33.37f, 92.15f, 35.4f, 92.15f)
        curveTo(37.41f, 92.15f, 39.06f, 92.55f, 40.36f, 93.35f)
        curveTo(41.66f, 94.13f, 42.63f, 95.24f, 43.25f, 96.67f)
        curveTo(43.89f, 98.1f, 44.21f, 99.77f, 44.21f, 101.69f)
        close()
        moveTo(30.67f, 101.69f)
        curveTo(30.67f, 102.99f, 30.83f, 104.11f, 31.16f, 105.05f)
        curveTo(31.49f, 105.98f, 32.0f, 106.7f, 32.69f, 107.2f)
        curveTo(33.39f, 107.71f, 34.28f, 107.96f, 35.37f, 107.96f)
        curveTo(36.5f, 107.96f, 37.4f, 107.71f, 38.08f, 107.2f)
        curveTo(38.77f, 106.7f, 39.27f, 105.98f, 39.58f, 105.05f)
        curveTo(39.91f, 104.11f, 40.08f, 102.99f, 40.08f, 101.69f)
        curveTo(40.08f, 99.73f, 39.71f, 98.2f, 38.99f, 97.09f)
        curveTo(38.26f, 95.98f, 37.06f, 95.43f, 35.4f, 95.43f)
        curveTo(34.29f, 95.43f, 33.39f, 95.68f, 32.69f, 96.18f)
        curveTo(32.0f, 96.68f, 31.49f, 97.4f, 31.16f, 98.34f)
        curveTo(30.83f, 99.27f, 30.67f, 100.39f, 30.67f, 101.69f)
        close()
        moveTo(70.05f, 111.0f)
        horizontalLineTo(61.6f)
        verticalLineTo(108.76f)
        lineTo(63.86f, 107.72f)
        verticalLineTo(95.71f)
        lineTo(61.6f, 94.67f)
        verticalLineTo(92.44f)
        horizontalLineTo(70.05f)
        verticalLineTo(94.67f)
        lineTo(67.79f, 95.71f)
        verticalLineTo(107.72f)
        lineTo(70.05f, 108.76f)
        verticalLineTo(111.0f)
        close()
        moveTo(84.7f, 111.0f)
        lineTo(80.23f, 96.44f)
        horizontalLineTo(80.13f)
        curveTo(80.15f, 96.79f, 80.17f, 97.31f, 80.21f, 98.0f)
        curveTo(80.24f, 98.69f, 80.28f, 99.44f, 80.31f, 100.24f)
        curveTo(80.35f, 101.02f, 80.36f, 101.73f, 80.36f, 102.37f)
        verticalLineTo(111.0f)
        horizontalLineTo(76.85f)
        verticalLineTo(92.44f)
        horizontalLineTo(82.21f)
        lineTo(86.6f, 106.63f)
        horizontalLineTo(86.68f)
        lineTo(91.33f, 92.44f)
        horizontalLineTo(96.69f)
        verticalLineTo(111.0f)
        horizontalLineTo(93.02f)
        verticalLineTo(102.21f)
        curveTo(93.02f, 101.62f, 93.03f, 100.95f, 93.05f, 100.18f)
        curveTo(93.09f, 99.42f, 93.11f, 98.7f, 93.13f, 98.03f)
        curveTo(93.16f, 97.33f, 93.19f, 96.81f, 93.21f, 96.47f)
        horizontalLineTo(93.1f)
        lineTo(88.32f, 111.0f)
        horizontalLineTo(84.7f)
        close()
        moveTo(116.38f, 111.0f)
        lineTo(115.03f, 106.58f)
        horizontalLineTo(108.27f)
        lineTo(106.92f, 111.0f)
        horizontalLineTo(102.68f)
        lineTo(109.23f, 92.36f)
        horizontalLineTo(114.04f)
        lineTo(120.62f, 111.0f)
        horizontalLineTo(116.38f)
        close()
        moveTo(112.74f, 98.96f)
        curveTo(112.65f, 98.67f, 112.54f, 98.29f, 112.4f, 97.84f)
        curveTo(112.26f, 97.39f, 112.13f, 96.93f, 111.99f, 96.47f)
        curveTo(111.85f, 96.0f, 111.74f, 95.59f, 111.65f, 95.24f)
        curveTo(111.56f, 95.59f, 111.44f, 96.02f, 111.29f, 96.54f)
        curveTo(111.15f, 97.05f, 111.01f, 97.53f, 110.87f, 98.0f)
        curveTo(110.75f, 98.45f, 110.65f, 98.77f, 110.58f, 98.96f)
        lineTo(109.26f, 103.28f)
        horizontalLineTo(114.09f)
        lineTo(112.74f, 98.96f)
        close()
        moveTo(133.15f, 100.57f)
        horizontalLineTo(140.51f)
        verticalLineTo(110.19f)
        curveTo(139.54f, 110.52f, 138.52f, 110.78f, 137.46f, 110.97f)
        curveTo(136.43f, 111.17f, 135.24f, 111.26f, 133.9f, 111.26f)
        curveTo(132.07f, 111.26f, 130.5f, 110.9f, 129.2f, 110.17f)
        curveTo(127.91f, 109.44f, 126.93f, 108.36f, 126.26f, 106.94f)
        curveTo(125.6f, 105.52f, 125.27f, 103.77f, 125.27f, 101.69f)
        curveTo(125.27f, 99.75f, 125.64f, 98.07f, 126.39f, 96.65f)
        curveTo(127.15f, 95.23f, 128.25f, 94.13f, 129.69f, 93.35f)
        curveTo(131.13f, 92.57f, 132.89f, 92.18f, 134.97f, 92.18f)
        curveTo(135.96f, 92.18f, 136.93f, 92.28f, 137.88f, 92.49f)
        curveTo(138.83f, 92.7f, 139.7f, 92.97f, 140.48f, 93.32f)
        lineTo(139.18f, 96.47f)
        curveTo(138.61f, 96.17f, 137.96f, 95.93f, 137.23f, 95.74f)
        curveTo(136.5f, 95.55f, 135.74f, 95.45f, 134.94f, 95.45f)
        curveTo(133.8f, 95.45f, 132.8f, 95.71f, 131.95f, 96.23f)
        curveTo(131.12f, 96.75f, 130.47f, 97.49f, 130.0f, 98.44f)
        curveTo(129.55f, 99.38f, 129.33f, 100.49f, 129.33f, 101.77f)
        curveTo(129.33f, 102.98f, 129.49f, 104.06f, 129.82f, 104.99f)
        curveTo(130.15f, 105.93f, 130.67f, 106.67f, 131.38f, 107.2f)
        curveTo(132.09f, 107.72f, 133.02f, 107.98f, 134.16f, 107.98f)
        curveTo(134.54f, 107.98f, 134.87f, 107.97f, 135.15f, 107.96f)
        curveTo(135.45f, 107.92f, 135.71f, 107.89f, 135.96f, 107.85f)
        curveTo(136.2f, 107.8f, 136.43f, 107.76f, 136.66f, 107.72f)
        verticalLineTo(103.85f)
        horizontalLineTo(133.15f)
        verticalLineTo(100.57f)
        close()
        moveTo(159.24f, 111.0f)
        horizontalLineTo(148.56f)
        verticalLineTo(92.44f)
        horizontalLineTo(159.24f)
        verticalLineTo(95.66f)
        horizontalLineTo(152.48f)
        verticalLineTo(99.74f)
        horizontalLineTo(158.78f)
        verticalLineTo(102.97f)
        horizontalLineTo(152.48f)
        verticalLineTo(107.75f)
        horizontalLineTo(159.24f)
        verticalLineTo(111.0f)
        close()
    }
        .path(
            fill = SolidColor(Color(0xff767680)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(108.33f, 45.33f)
            curveTo(108.33f, 46.56f, 108.0f, 47.65f, 107.34f, 48.59f)
            curveTo(106.68f, 49.54f, 105.81f, 50.22f, 104.72f, 50.65f)
            lineTo(99.41f, 45.33f)
            horizontalLineTo(102.67f)
            verticalLineTo(22.67f)
            horizontalLineTo(76.74f)
            lineTo(71.07f, 17.0f)
            horizontalLineTo(102.67f)
            curveTo(104.22f, 17.0f, 105.56f, 17.56f, 106.67f, 18.67f)
            curveTo(107.78f, 19.77f, 108.33f, 21.11f, 108.33f, 22.67f)
            verticalLineTo(45.33f)
            close()
            moveTo(95.58f, 34.0f)
            curveTo(94.4f, 34.0f, 93.4f, 33.59f, 92.57f, 32.76f)
            curveTo(91.75f, 31.93f, 91.33f, 30.93f, 91.33f, 29.75f)
            curveTo(91.33f, 28.57f, 91.75f, 27.57f, 92.57f, 26.74f)
            curveTo(93.4f, 25.91f, 94.4f, 25.5f, 95.58f, 25.5f)
            curveTo(96.76f, 25.5f, 97.77f, 25.91f, 98.59f, 26.74f)
            curveTo(99.42f, 27.57f, 99.83f, 28.57f, 99.83f, 29.75f)
            curveTo(99.83f, 30.93f, 99.42f, 31.93f, 98.59f, 32.76f)
            curveTo(97.77f, 33.59f, 96.76f, 34.0f, 95.58f, 34.0f)
            close()
            moveTo(65.83f, 42.5f)
            verticalLineTo(36.83f)
            horizontalLineTo(60.17f)
            verticalLineTo(31.17f)
            horizontalLineTo(65.83f)
            verticalLineTo(25.5f)
            horizontalLineTo(71.5f)
            verticalLineTo(31.17f)
            horizontalLineTo(77.17f)
            verticalLineTo(36.83f)
            horizontalLineTo(71.5f)
            verticalLineTo(42.5f)
            horizontalLineTo(65.83f)
            close()
            moveTo(57.33f, 51.0f)
            curveTo(55.78f, 51.0f, 54.44f, 50.45f, 53.33f, 49.34f)
            curveTo(52.22f, 48.23f, 51.67f, 46.89f, 51.67f, 45.33f)
            verticalLineTo(22.67f)
            curveTo(51.67f, 21.06f, 52.23f, 19.7f, 53.37f, 18.59f)
            curveTo(54.5f, 17.48f, 55.87f, 16.93f, 57.47f, 16.93f)
            horizontalLineTo(62.93f)
            lineTo(68.67f, 22.67f)
            horizontalLineTo(57.33f)
            verticalLineTo(45.33f)
            horizontalLineTo(83.26f)
            lineTo(49.9f, 11.97f)
            lineTo(53.93f, 7.93f)
            lineTo(106.07f, 60.07f)
            lineTo(102.03f, 64.1f)
            lineTo(88.93f, 51.0f)
            horizontalLineTo(57.33f)
            close()
        }.build().also { cache = it }
