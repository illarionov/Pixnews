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
@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS", "MagicNumber")

package ru.pixnews.library.ui.tooling.debuglayout.rowcolumn

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import ru.pixnews.library.ui.tooling.debuglayout.DebugLayer

internal class ColumnsLayer constructor(
    val columns: RowsColumnsCount,
    val color: Color,
    val arrangement: DebugColumnsArrangement,
) : DebugLayer {
    private val arrangementCalculator: ArrangementCalculator = ArrangementCalculator(arrangement, columns)

    override fun ContentDrawScope.draw() {
        val columns = with(arrangementCalculator) {
             arrange(size.width)
        }

        for (column in columns) {
            drawRect(
                color,
                topLeft = Offset(x = column.start, y = 0f),
                size = Size(
                    width = column.size,
                    height = size.height,
                ),
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as ColumnsLayer

        if (columns != other.columns) {
            return false
        }
        if (color != other.color) {
            return false
        }
        if (arrangement != other.arrangement) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = columns.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + arrangement.hashCode()
        return result
    }
}
