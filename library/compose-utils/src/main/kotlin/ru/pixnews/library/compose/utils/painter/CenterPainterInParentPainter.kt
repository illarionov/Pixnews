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
package ru.pixnews.library.compose.utils.painter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
public fun rememberCenterPainterInParentPainter(
    painter: Painter,
    backgroundColor: Color? = null,
): Painter = remember(painter, backgroundColor) {
    CenterPainterInParentPainter(painter, backgroundColor)
}

/**
 * Draws [image] ImageVector in center of the parent container in its intrinsic size
 */
@Composable
public fun rememberCenteredInParentVectorPainter(
    image: ImageVector,
    backgroundColor: Color? = null,
): Painter {
    val vectorPainter = rememberVectorPainter(image = image)
    return remember(vectorPainter, backgroundColor) {
        CenterPainterInParentPainter(vectorPainter, backgroundColor)
    }
}

/**
 * Draws [painter] in center of the parent container in its intrinsic size
 */
public class CenterPainterInParentPainter(
    private var painter: Painter,
    private var backgroundColor: Color?,
) : Painter() {
    private var alpha: Float by mutableStateOf(DefaultAlpha)
    private var colorFilter: ColorFilter? by mutableStateOf(null)

    override val intrinsicSize: Size
        get() = Size.Unspecified

    override fun applyAlpha(alpha: Float): Boolean {
        this.alpha = alpha
        return true
    }

    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        this.colorFilter = colorFilter
        return true
    }

    override fun DrawScope.onDraw() {
        backgroundColor?.let {
            drawRect(color = it, alpha = alpha, colorFilter = colorFilter)
        }

        val size = this.size
        val painterIntrinsicSize = painter.intrinsicSize

        val drawSize = computeDrawSize(painterIntrinsicSize, size)

        if (size.isUnspecified || size.isEmpty()) {
            with(painter) {
                draw(drawSize, alpha, colorFilter)
            }
        } else {
            inset(
                horizontal = (size.width - drawSize.width) / 2,
                vertical = (size.height - drawSize.height) / 2,
            ) {
                with(painter) {
                    draw(drawSize, alpha, colorFilter)
                }
            }
        }
    }

    private fun computeDrawSize(srcSize: Size, dstSize: Size): Size {
        if (srcSize.isUnspecified || srcSize.isEmpty()) {
            return dstSize
        }
        if (dstSize.isUnspecified || dstSize.isEmpty()) {
            return dstSize
        }
        return srcSize
    }
}
