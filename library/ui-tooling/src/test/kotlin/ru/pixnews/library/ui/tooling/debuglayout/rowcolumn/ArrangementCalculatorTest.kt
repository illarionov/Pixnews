/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("LongMethod")

package ru.pixnews.library.ui.tooling.debuglayout.rowcolumn

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.ArrangementCalculator.PaintedSubrange
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugColumnsArrangement.Center
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugColumnsArrangement.Left
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugColumnsArrangement.Right
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugColumnsArrangement.Stretch
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugRowsArrangement.Bottom
import ru.pixnews.library.ui.tooling.debuglayout.rowcolumn.DebugRowsArrangement.Top

public class ArrangementCalculatorTest {
    internal class FillFromCenterCalculatorTest {
        @ParameterizedTest
        @MethodSource("testFillFromCenterCalculator")
        fun `fillFromCenterCalculator should return correct arrangement on columns`(test: FillFromCenterTestCase) {
            val arrangement = Center(
                width = test.width,
                gutter = test.gutter,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        @ParameterizedTest
        @MethodSource("testFillFromCenterCalculator")
        fun `fillFromCenterCalculator should return correct arrangement on rows`(test: FillFromCenterTestCase) {
            val arrangement = DebugRowsArrangement.Center(
                height = test.width,
                gutter = test.gutter,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        internal data class FillFromCenterTestCase(
            val comment: String,
            val rowsColumnsCount: RowsColumnsCount,
            val width: Dp,
            val gutter: Dp,
            val screenSize: Float,
            val expectedRanges: List<PaintedSubrange>,
        ) {
            override fun toString() = comment
        }

        companion object {
            @JvmStatic
            fun testFillFromCenterCalculator(): List<FillFromCenterTestCase> = listOf(
                FillFromCenterTestCase(
                    comment = "Auto columns, large screen, 4 columns",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    width = 2.dp,
                    gutter = 4.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(0f, 6f, 12f, 18f).map { PaintedSubrange(it, 2f) },
                ),
                FillFromCenterTestCase(
                    comment = "Auto columns, gutter all screen",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    width = 1.dp,
                    gutter = 1.dp,
                    screenSize = 1f,
                    expectedRanges = listOf(),
                ),
                FillFromCenterTestCase(
                    comment = "Auto columns:2 columns cut on both sides",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    width = 2.dp,
                    gutter = 1.dp,
                    screenSize = 2f,
                    expectedRanges = listOf(-1.5f, 1.5f).map { PaintedSubrange(it, 2f) },
                ),
                FillFromCenterTestCase(
                    comment = "Auto columns, small item width",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    width = 0.1.dp,
                    gutter = 4.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(),
                ),
                FillFromCenterTestCase(
                    comment = "1 column, general case",
                    rowsColumnsCount = RowsColumnsCount(1),
                    width = 3.dp,
                    gutter = 4.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(PaintedSubrange(8.5f, 3f)),
                ),
                FillFromCenterTestCase(
                    comment = "1 column, cut from both sides",
                    rowsColumnsCount = RowsColumnsCount(1),
                    width = 10.dp,
                    gutter = 4.dp,
                    screenSize = 5f,
                    expectedRanges = listOf(PaintedSubrange(0f, 5f)),
                ),
                FillFromCenterTestCase(
                    comment = "1 column, equal to screen size",
                    rowsColumnsCount = RowsColumnsCount(1),
                    width = 10.dp,
                    gutter = 4.dp,
                    screenSize = 10f,
                    expectedRanges = listOf(PaintedSubrange(0f, 10f)),
                ),
                FillFromCenterTestCase(
                    comment = "3 columns, general case",
                    rowsColumnsCount = RowsColumnsCount(3),
                    width = 3.dp,
                    gutter = 4.dp,
                    screenSize = 27f,
                    expectedRanges = listOf(5f, 12f, 19f).map { PaintedSubrange(it, 3f) },
                ),
                FillFromCenterTestCase(
                    comment = "2 columns, general case",
                    rowsColumnsCount = RowsColumnsCount(2),
                    width = 3.dp,
                    gutter = 4.dp,
                    screenSize = 27f,
                    expectedRanges = listOf(8.5f, 15.5f).map { PaintedSubrange(it, 3f) },
                ),
            )
        }
    }

    internal class FillFromStartCalculatorTest {
        @ParameterizedTest
        @MethodSource("testFillFromStartCalculator")
        fun `fillFromStartCalculator should return correct arrangement on columns`(test: FillFromStartTestCase) {
            val arrangement = Left(
                width = test.size,
                gutter = test.gutter,
                offset = test.offset,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        @ParameterizedTest
        @MethodSource("testFillFromStartCalculator")
        fun `fillFromStartCalculator should return correct arrangement on rows`(test: FillFromStartTestCase) {
            val arrangement = Top(
                height = test.size,
                gutter = test.gutter,
                offset = test.offset,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        internal data class FillFromStartTestCase(
            val comment: String,
            val rowsColumnsCount: RowsColumnsCount,
            val size: Dp,
            val offset: Dp,
            val gutter: Dp,
            val screenSize: Float,
            val expectedRanges: List<PaintedSubrange>,
        ) {
            override fun toString(): String = comment
        }

        companion object {
            @JvmStatic
            fun testFillFromStartCalculator(): List<FillFromStartTestCase> = listOf(
                FillFromStartTestCase(
                    comment = "Auto rows/columns, large screen, 4 rows/columns",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    size = 2.dp,
                    gutter = 4.dp,
                    offset = 3.dp,
                    screenSize = 21f,
                    expectedRanges = listOf(3f, 9f, 15f).map { PaintedSubrange(it, 2f) },
                ),
                FillFromStartTestCase(
                    comment = "1 row/column all screen",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    size = 1.dp,
                    gutter = 1.dp,
                    offset = 0.dp,
                    screenSize = 1f,
                    expectedRanges = listOf(PaintedSubrange(0f, 1f)),
                ),
                FillFromStartTestCase(
                    comment = "Limited rows/columns, general case",
                    rowsColumnsCount = 3.asRowColumnCount,
                    size = 5.dp,
                    gutter = 1.dp,
                    offset = 0.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(0f, 6f, 12f).map { PaintedSubrange(it, 5f) },
                ),
                FillFromStartTestCase(
                    comment = "Small item width",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    size = 0.1.dp,
                    gutter = 4.dp,
                    offset = 1.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(),
                ),
                FillFromStartTestCase(
                    comment = "Limited rows/columns, cut from right on row",
                    rowsColumnsCount = RowsColumnsCount(5),
                    size = 10.dp,
                    gutter = 4.dp,
                    offset = 3.dp,
                    screenSize = 40f,
                    expectedRanges = listOf(3f, 17f, 31f).map { PaintedSubrange(it, 10f) },
                ),
                FillFromStartTestCase(
                    comment = "Limited columns, cut from right on gutter right border",
                    rowsColumnsCount = RowsColumnsCount(5),
                    size = 10.dp,
                    gutter = 4.dp,
                    offset = 3.dp,
                    screenSize = 45f,
                    expectedRanges = listOf(3f, 17f, 31f).map { PaintedSubrange(it, 10f) },
                ),
            )
        }
    }

    internal class FillFromEndCalculatorTest {
        @ParameterizedTest
        @MethodSource("testFillFromEndCalculator")
        fun `fillFromEndCalculator should return correct arrangement on columns`(test: FillFromEndTestCase) {
            val arrangement = Right(
                width = test.size,
                gutter = test.gutter,
                offset = test.offset,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        @ParameterizedTest
        @MethodSource("testFillFromEndCalculator")
        fun `fillFromLeftCalculator should return correct arrangement on rows`(test: FillFromEndTestCase) {
            val arrangement = Bottom(
                height = test.size,
                gutter = test.gutter,
                offset = test.offset,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        internal data class FillFromEndTestCase(
            val comment: String,
            val rowsColumnsCount: RowsColumnsCount,
            val size: Dp,
            val offset: Dp,
            val gutter: Dp,
            val screenSize: Float,
            val expectedRanges: List<PaintedSubrange>,
        ) {
            override fun toString(): String = comment
        }

        companion object {
            @JvmStatic
            fun testFillFromEndCalculator(): List<FillFromEndTestCase> = listOf(
                FillFromEndTestCase(
                    comment = "Auto rows/columns, large screen, 4 rows/columns",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    size = 2.dp,
                    gutter = 4.dp,
                    offset = 3.dp,
                    screenSize = 21f,
                    expectedRanges = listOf(16f, 10f, 4f).map { PaintedSubrange(it, 2f) },
                ),
                FillFromEndTestCase(
                    comment = "1 row/column all screen",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    size = 1.dp,
                    gutter = 1.dp,
                    offset = 0.dp,
                    screenSize = 1f,
                    expectedRanges = listOf(PaintedSubrange(0f, 1f)),
                ),
                FillFromEndTestCase(
                    comment = "Limited columns, general case",
                    rowsColumnsCount = 3.asRowColumnCount,
                    size = 5.dp,
                    gutter = 1.dp,
                    offset = 0.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(15f, 9f, 3f).map { PaintedSubrange(it, 5f) },
                ),
                FillFromEndTestCase(
                    comment = "Small item width",
                    rowsColumnsCount = RowsColumnsCount.Auto,
                    size = 0.1.dp,
                    gutter = 4.dp,
                    offset = 1.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(),
                ),
                FillFromEndTestCase(
                    comment = "Limited columns, cut from left on row",
                    rowsColumnsCount = RowsColumnsCount(5),
                    size = 10.dp,
                    gutter = 4.dp,
                    offset = 3.dp,
                    screenSize = 40f,
                    expectedRanges = listOf(-1f, 13f, 27f).map { PaintedSubrange(it, 10f) },
                ),
            )
        }
    }

    internal class StretchCalculatorTest {
        @ParameterizedTest
        @MethodSource("testStretchCalculator")
        fun `stretchCalculator should return correct arrangement on columns`(test: StretchTestCase) {
            val arrangement = Stretch(
                gutter = test.gutter,
                margin = test.margin,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount.asRowColumnCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        @ParameterizedTest
        @MethodSource("testStretchCalculator")
        fun `stretchCalculator should return correct arrangement on rows`(test: StretchTestCase) {
            val arrangement = DebugRowsArrangement.Stretch(
                gutter = test.gutter,
                margin = test.margin,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount.asRowColumnCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            ranges.toList() shouldContainExactlyInAnyOrder test.expectedRanges
        }

        @ParameterizedTest
        @CsvSource(
            delimiter = '|',
            textBlock = """
            0 |  1.0 |  1.0 | 10.0
           -1 |  1.0 |  1.0 | 10.0
            1 | -1.0 |  1.0 | 10.0
            1 |    0 | -1.0 | 10.0
            1 |  0.0 |  0.0 | 0.99
            1 |  0.0 |  0.0 | -1.0
            1 |  0.0 |  0.0 |  0.0""",
        )
        fun `stretchCalculator should return empty list on invalid input values`(
            rowsColumnsCount: Int,
            margin: Float,
            gutter: Float,
            screenSize: Float,
        ) {
            val arrangement = Stretch(
                margin = margin.dp,
                gutter = gutter.dp,
            )
            val calculator = ArrangementCalculator(arrangement, rowsColumnsCount.asRowColumnCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(screenSize)
                }
            }

            ranges.toList().shouldBeEmpty()
        }

        internal data class StretchTestCase(
            val comment: String,
            val rowsColumnsCount: Int,
            val margin: Dp,
            val gutter: Dp,
            val screenSize: Float,
            val expectedRanges: List<PaintedSubrange>,
        ) {
            override fun toString(): String = comment
        }

        companion object {
            @JvmStatic
            fun testStretchCalculator(): List<StretchTestCase> = listOf(
                StretchTestCase(
                    comment = "1 column",
                    rowsColumnsCount = 1,
                    gutter = 8.dp,
                    screenSize = 20f,
                    margin = 4.dp,
                    expectedRanges = listOf(PaintedSubrange(4f, 12f)),
                ),
                StretchTestCase(
                    comment = "2 columns",
                    rowsColumnsCount = 2,
                    gutter = 3.dp,
                    margin = 0.dp,
                    screenSize = 10f,
                    expectedRanges = listOf(0f, 6.5f).map { PaintedSubrange(it, 3.5f) },
                ),
                StretchTestCase(
                    comment = "3 columns",
                    rowsColumnsCount = 3,
                    gutter = 3.dp,
                    margin = 4.dp,
                    screenSize = 20f,
                    expectedRanges = listOf(4f, 9f, 14f).map { PaintedSubrange(it, 2f) },
                ),
                StretchTestCase(
                    comment = "Huge gutter",
                    rowsColumnsCount = 3,
                    gutter = 6.dp,
                    margin = 0.dp,
                    screenSize = 13f,
                    expectedRanges = listOf(),
                ),
                StretchTestCase(
                    comment = "Huge Margin",
                    rowsColumnsCount = 3,
                    gutter = 1.dp,
                    margin = 6.dp,
                    screenSize = 13f,
                    expectedRanges = listOf(),
                ),
            )
        }
    }
}

val oneToOneDensity: Density = object : Density {
    override val density: Float = 1f
    override val fontScale: Float = 1f
}
