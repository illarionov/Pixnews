/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
@file:Suppress("MagicNumber", "TooManyFunctions")
@file:OptIn(ExperimentalTextApi::class)

package ru.pixnews.library.ui.tooling.debuglayout

import android.annotation.SuppressLint
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.pixnews.library.ui.tooling.debuglayout.guideline.DebugGuidelineLayer
import ru.pixnews.library.ui.tooling.debuglayout.guideline.DebugGuidelineOffset
import ru.pixnews.library.ui.tooling.debuglayout.guideline.DebugGuidelinePosition
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.ColumnsLayer
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugColumnsArrangement
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugRowsArrangement
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.RowsColumnsCount
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.RowsLayer
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.asRowColumnCount
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerHorizontalZeroPoint
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerMeasureUnit
import ru.pixnews.library.ui.tooling.debuglayout.ruler.DebugRulerVerticalZeroPoint
import ru.pixnews.library.ui.tooling.debuglayout.ruler.HorizontalRulerLayer
import ru.pixnews.library.ui.tooling.debuglayout.ruler.VerticalRulerLayer

public class DebugLayout private constructor(
    private val textMeasurer: TextMeasurer,
    private val displayMetrics: DisplayMetrics,
) {
    private val layers: MutableList<DebugLayer> = mutableListOf()

    public fun grid(
        size: Dp = DebugLayoutDefaults.Grid.size,
        color: Color = DebugLayoutDefaults.Grid.color,
        strokeWidth: Dp = DebugLayoutDefaults.Grid.strokeWidth,
    ) {
        layers.add(GridLayer(size, color, strokeWidth))
    }

    public fun columns(
        arrangement: DebugColumnsArrangement = DebugColumnsArrangement.Stretch(),
        columns: RowsColumnsCount = DebugLayoutDefaults.Column.defaultColumns,
        color: Color = DebugLayoutDefaults.Column.color,
    ) {
        layers.add(ColumnsLayer(columns, color, arrangement))
    }

    public fun rows(
        arrangement: DebugRowsArrangement = DebugRowsArrangement.Stretch(),
        rows: RowsColumnsCount = DebugLayoutDefaults.Row.defaultRows,
        color: Color = DebugLayoutDefaults.Row.color,
    ) {
        layers.add(RowsLayer(rows, color, arrangement))
    }

    public fun guideline(
        position: DebugGuidelinePosition = DebugGuidelinePosition.Start(),
        color: Color = DebugLayoutDefaults.Guideline.color,
        strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
    ) {
        layers.add(DebugGuidelineLayer(position, color, strokeWidth))
    }

    public fun horizontalRuler(
        step: DebugRulerMeasureUnit = DebugLayoutDefaults.Ruler.step,
        zeroOffset: DebugRulerHorizontalZeroPoint = DebugRulerHorizontalZeroPoint.ZERO,
    ) {
        layers.add(HorizontalRulerLayer(textMeasurer, displayMetrics, step, zeroOffset))
    }

    public fun verticalRuler(
        step: DebugRulerMeasureUnit = DebugLayoutDefaults.Ruler.step,
        zeroOffset: DebugRulerVerticalZeroPoint = DebugRulerVerticalZeroPoint.ZERO,
    ) {
        layers.add(VerticalRulerLayer(textMeasurer, displayMetrics, step, zeroOffset))
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    private fun build(): Modifier = DebugLayoutModifier(layers.toList())

    public companion object {
        @Stable
        @Suppress("MemberNameEqualsClassName")
        public fun Modifier.debugLayout(
            testMeasurer: TextMeasurer? = null,
            buildAction: DebugLayout.() -> Unit,
        ): Modifier = this.composed(
            inspectorInfo = debugInspectorInfo {
                name = "debugLayout"
                properties["buildAction"] = buildAction
            },
        ) {
            val textMeasurer = testMeasurer ?: rememberTextMeasurer()
            val displayMetrics = LocalContext.current.resources.displayMetrics.let {
                DisplayMetrics(it.density, it.xdpi, it.ydpi)
            }

            this.then(DebugLayout(textMeasurer, displayMetrics).apply(buildAction).build())
        }

        @Stable
        public fun Modifier.debugLayoutGrid(
            size: Dp = DebugLayoutDefaults.Grid.size,
            color: Color = DebugLayoutDefaults.Grid.color,
            strokeWidth: Dp = DebugLayoutDefaults.Grid.strokeWidth,
        ): Modifier = this.then(DebugLayoutModifier(GridLayer(size, color, strokeWidth)))

        @Stable
        public fun Modifier.debugLayoutColumns(
            arrangement: DebugColumnsArrangement = DebugColumnsArrangement.Stretch(),
            columns: RowsColumnsCount = DebugLayoutDefaults.Column.defaultColumns,
            color: Color = DebugLayoutDefaults.Column.color,
        ): Modifier = this.then(DebugLayoutModifier(ColumnsLayer(columns, color, arrangement)))

        @Stable
        public fun Modifier.debugLayoutRows(
            arrangement: DebugRowsArrangement = DebugRowsArrangement.Stretch(),
            rows: RowsColumnsCount = DebugLayoutDefaults.Row.defaultRows,
            color: Color = DebugLayoutDefaults.Row.color,
        ): Modifier = this.then(DebugLayoutModifier(RowsLayer(rows, color, arrangement)))

        @Stable
        public fun Modifier.debugLayoutGuideline(
            position: DebugGuidelinePosition = DebugGuidelinePosition.Start(),
            color: Color = DebugLayoutDefaults.Guideline.color,
            strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
        ): Modifier = this.then(
            DebugLayoutModifier(DebugGuidelineLayer(position, color, strokeWidth)),
        )
    }
}

public fun DebugLayout.columnsFromLeft(
    columnWidth: Dp,
    offset: Dp = DebugLayoutDefaults.Column.offset,
    gutter: Dp = DebugLayoutDefaults.Column.gutter,
    columns: RowsColumnsCount = DebugLayoutDefaults.Column.defaultColumns,
    color: Color = DebugLayoutDefaults.Column.color,
): Unit = columns(
    arrangement = DebugColumnsArrangement.Left(columnWidth, offset, gutter),
    columns = columns,
    color = color,
)

public fun DebugLayout.columnsFromRight(
    columnWidth: Dp,
    offset: Dp = DebugLayoutDefaults.Column.offset,
    gutter: Dp = DebugLayoutDefaults.Column.gutter,
    columns: RowsColumnsCount = DebugLayoutDefaults.Column.defaultColumns,
    color: Color = DebugLayoutDefaults.Column.color,
): Unit = columns(
    arrangement = DebugColumnsArrangement.Right(columnWidth, offset, gutter),
    columns = columns,
    color = color,
)

public fun DebugLayout.columnsFromCenter(
    columnWidth: Dp,
    gutter: Dp = DebugLayoutDefaults.Column.gutter,
    columns: RowsColumnsCount = DebugLayoutDefaults.Column.defaultColumns,
    color: Color = DebugLayoutDefaults.Column.color,
): Unit = columns(
    arrangement = DebugColumnsArrangement.Center(columnWidth, gutter),
    columns = columns,
    color = color,
)

public fun DebugLayout.columnsStretch(
    margin: Dp = DebugLayoutDefaults.Column.margin,
    gutter: Dp = DebugLayoutDefaults.Column.gutter,
    columns: Int,
    color: Color = DebugLayoutDefaults.Column.color,
): Unit = columns(
    arrangement = DebugColumnsArrangement.Stretch(margin, gutter),
    columns = columns.asRowColumnCount,
    color = color,
)

public fun DebugLayout.guidelineFromStart(
    offset: DebugGuidelineOffset = DebugLayoutDefaults.Guideline.horizontalOffset,
    color: Color = DebugLayoutDefaults.Guideline.color,
    strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
): Unit = guideline(
    position = DebugGuidelinePosition.Start(offset),
    color = color,
    strokeWidth = strokeWidth,
)

public fun DebugLayout.guidelineFromEnd(
    offset: DebugGuidelineOffset = DebugLayoutDefaults.Guideline.horizontalOffset,
    color: Color = DebugLayoutDefaults.Guideline.color,
    strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
): Unit = guideline(
    position = DebugGuidelinePosition.End(offset),
    color = color,
    strokeWidth = strokeWidth,
)

public fun DebugLayout.guidelineFromTop(
    offset: DebugGuidelineOffset = DebugLayoutDefaults.Guideline.verticalOffset,
    color: Color = DebugLayoutDefaults.Guideline.color,
    strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
): Unit = guideline(
    position = DebugGuidelinePosition.Top(offset),
    color = color,
    strokeWidth = strokeWidth,
)

public fun DebugLayout.guidelineFromBottom(
    offset: DebugGuidelineOffset = DebugLayoutDefaults.Guideline.verticalOffset,
    color: Color = DebugLayoutDefaults.Guideline.color,
    strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
): Unit = guideline(
    position = DebugGuidelinePosition.Bottom(offset),
    color = color,
    strokeWidth = strokeWidth,
)

public fun DebugLayout.guidelineCenterHorizontal(
    verticalOffset: Dp = 0.dp,
    color: Color = DebugLayoutDefaults.Guideline.color,
    strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
): Unit = guideline(
    position = DebugGuidelinePosition.CenterHorizontal(verticalOffset),
    color = color,
    strokeWidth = strokeWidth,
)

public fun DebugLayout.guidelineCenterVertical(
    horizontalOffset: Dp = 0.dp,
    color: Color = DebugLayoutDefaults.Guideline.color,
    strokeWidth: Dp = DebugLayoutDefaults.Guideline.strokeWidth,
): Unit = guideline(
    position = DebugGuidelinePosition.CenterVertical(horizontalOffset),
    color = color,
    strokeWidth = strokeWidth,
)

public fun DebugLayout.rowsFromTop(
    height: Dp,
    offset: Dp = DebugLayoutDefaults.Row.offset,
    gutter: Dp = DebugLayoutDefaults.Row.gutter,
    rows: RowsColumnsCount = DebugLayoutDefaults.Row.defaultRows,
    color: Color = DebugLayoutDefaults.Row.color,
): Unit = rows(
    arrangement = DebugRowsArrangement.Top(height, offset, gutter),
    rows = rows,
    color = color,
)

public fun DebugLayout.rowsFromBottom(
    height: Dp,
    offset: Dp = DebugLayoutDefaults.Row.offset,
    gutter: Dp = DebugLayoutDefaults.Row.gutter,
    rows: RowsColumnsCount = DebugLayoutDefaults.Row.defaultRows,
    color: Color = DebugLayoutDefaults.Row.color,
): Unit = rows(
    arrangement = DebugRowsArrangement.Bottom(height, offset, gutter),
    rows = rows,
    color = color,
)

public fun DebugLayout.rowsFromCenter(
    height: Dp,
    gutter: Dp = DebugLayoutDefaults.Row.gutter,
    rows: RowsColumnsCount = DebugLayoutDefaults.Row.defaultRows,
    color: Color = DebugLayoutDefaults.Row.color,
): Unit = rows(
    arrangement = DebugRowsArrangement.Center(height, gutter),
    rows = rows,
    color = color,
)

public fun DebugLayout.rowsStretch(
    margin: Dp = DebugLayoutDefaults.Row.margin,
    gutter: Dp = DebugLayoutDefaults.Row.gutter,
    rows: RowsColumnsCount = DebugLayoutDefaults.Row.defaultRows,
    color: Color = DebugLayoutDefaults.Row.color,
): Unit = rows(
    arrangement = DebugRowsArrangement.Stretch(margin, gutter),
    rows = rows,
    color = color,
)

private class DebugLayoutModifier(
    val layers: List<DebugLayer>,
) : DrawModifier {
    constructor(layer: DebugLayer) : this(listOf(layer))

    override fun ContentDrawScope.draw() {
        drawContent()

        for (debugLayer in layers) {
            with(debugLayer) {
                draw()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as DebugLayoutModifier

        if (layers != other.layers) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return layers.hashCode()
    }
}
