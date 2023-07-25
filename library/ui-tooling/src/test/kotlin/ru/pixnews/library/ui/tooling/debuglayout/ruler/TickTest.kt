/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")

package ru.pixnews.library.ui.tooling.debuglayout.ruler

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.library.ui.tooling.debuglayout.DisplayMetrics
import ru.pixnews.library.ui.tooling.debuglayout.ruler.RulerOrientation.HORIZONTAL
import ru.pixnews.library.ui.tooling.debuglayout.ruler.RulerOrientation.VERTICAL
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType.MAJOR
import ru.pixnews.library.ui.tooling.debuglayout.ruler.Tick.TickType.MINOR
import ru.pixnews.library.ui.tooling.debuglayout.ruler.ZeroOffset.Alignment.CENTER
import ru.pixnews.library.ui.tooling.debuglayout.ruler.ZeroOffset.Alignment.LEFT_TOP
import ru.pixnews.library.ui.tooling.debuglayout.ruler.ZeroOffset.Alignment.RIGHT_BOTTOM
import kotlin.math.floor

internal class TickTest {
    @ParameterizedTest
    @MethodSource("getTicksPositionsDpTest")
    fun `getTicksPositions should return correct ticks sequence for dp steps`(test: GetTickPositionsTestCase) {
        val horizontalPositions = getTicksPositions(
            step = test.step,
            screenSize = test.screenSize,
            displayMetrics = test.displayMetrics,
            zeroOffset = test.zeroOffset,
            orientation = HORIZONTAL,
        )
        val verticalPositions = getTicksPositions(
            step = test.step,
            screenSize = test.screenSize,
            displayMetrics = test.displayMetrics,
            zeroOffset = test.zeroOffset,
            orientation = VERTICAL,
        )

        horizontalPositions.toList() shouldContainExactlyInAnyOrder test.expectedTicks
        verticalPositions.toList() shouldContainExactlyInAnyOrder test.expectedTicks
    }

    @Test
    fun `getTicksPositions should return correct sequences for millimeters`() {
        val testDisplayMetrics = DisplayMetrics(
            density = 2f,
            xdpi = 2 * 25.4f,
            ydpi = 3 * 25.4f,
        )

        val horizontalPositions = getTicksPositions(
            step = 10.rulerMm(),
            screenSize = 60f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset(CENTER, 2.rulerMm()),
            orientation = HORIZONTAL,
        )

        val verticalPositions = getTicksPositions(
            step = 10.rulerMm(),
            screenSize = 60f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset(CENTER, 1.rulerMm()),
            orientation = VERTICAL,
        )

        horizontalPositions
            .map { it.copy(position = floor(it.position)) }
            .toList()
            .shouldContainExactlyInAnyOrder(
                Tick(
                    position = 4f,
                    type = MINOR,
                    markerValue = -15f,
                ).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(14, 24, 34, 44, 54),
                ),
            )

        verticalPositions
            .map { it.copy(position = floor(it.position)) }
            .toList()
            .shouldContainExactlyInAnyOrder(
                Tick(
                    position = 3f,
                    markerValue = -10f,
                ).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(18, 33, 48),
                ),
            )
    }

    @Test
    fun `getTicksPositions should return correct sequences for inches`() {
        val horizontalPositions = getTicksPositions(
            step = 1.rulerInches(),
            screenSize = 200f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset.ZERO,
            orientation = HORIZONTAL,
        )

        val verticalPositions = getTicksPositions(
            step = 1.rulerInches(),
            screenSize = 200f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset.ZERO,
            orientation = VERTICAL,
        )

        horizontalPositions
            .map { it.copy(position = floor(it.position)) }
            .toList()
            .shouldContainExactlyInAnyOrder(
                Tick(
                    position = 0f,
                    markerValue = 0f,
                ).withSubsequentTicks(
                    markerStep = 0.5f,
                    positions = listOf(55, 111, 166),
                ),
            )

        verticalPositions
            .toList()
            .map { it.copy(position = floor(it.position)) }
            .shouldContainExactlyInAnyOrder(
                Tick(
                    position = 0f,
                    markerValue = 0f,
                ).withSubsequentTicks(
                    markerStep = 0.5f,
                    positions = listOf(61, 123, 184),
                ),
            )
    }

    internal data class GetTickPositionsTestCase(
        val comment: String,
        val step: DebugRulerMeasureUnit,
        val screenSize: Float = 200f,
        val displayMetrics: DisplayMetrics = testDisplayMetrics,
        val zeroOffset: ZeroOffset = ZeroOffset.ZERO,
        val orientation: RulerOrientation = HORIZONTAL,
        val expectedTicks: List<Tick>,
    )

    companion object {
        private val testDisplayMetrics: DisplayMetrics = DisplayMetrics(
            density = 2f,
            xdpi = 111f,
            ydpi = 123f,
        )

        @JvmStatic
        fun getTicksPositionsDpTest(): List<GetTickPositionsTestCase> {
            val testCase = GetTickPositionsTestCase(
                comment = "",
                step = 10.rulerDp(),
                screenSize = 30f,
                expectedTicks = listOf(),
            )

            return listOf(
                testCase.copy(
                    comment = "General case",
                    expectedTicks = Tick(0f, MAJOR, 0f).withSubsequentTicks(
                        markerStep = 5,
                        positions = listOf(10, 20, 30),
                    ),
                ),
                testCase.copy(
                    comment = "Offset from left",
                    zeroOffset = ZeroOffset(LEFT_TOP, 14.rulerDp()),
                    expectedTicks = Tick(position = 8f, markerValue = -10f).withSubsequentTicks(
                        markerStep = 5,
                        positions = listOf(18, 28),
                    ),
                ),
                testCase.copy(
                    comment = "Offset from center",
                    zeroOffset = ZeroOffset(CENTER, 2.rulerDp()),
                    expectedTicks = Tick(position = 9f, type = MINOR, markerValue = -5f).withSubsequentTicks(
                        markerStep = 5,
                        positions = listOf(19, 29),
                    ),
                ),
                testCase.copy(
                    comment = "Offset from right",
                    zeroOffset = ZeroOffset(RIGHT_BOTTOM, 5.rulerDp()),
                    expectedTicks = Tick(position = 0f, markerValue = -10f).withSubsequentTicks(
                        markerStep = 5,
                        positions = listOf(10, 20, 30),
                    ),
                ),
                testCase.copy(
                    comment = "Huge offset",
                    zeroOffset = ZeroOffset(LEFT_TOP, (-5000).rulerDp()),
                    expectedTicks = Tick(position = 0f, markerValue = 5000f).withSubsequentTicks(
                        markerStep = 5,
                        positions = listOf(10, 20, 30),
                    ),
                ),
            )
        }

        private fun Tick.withSubsequentTicks(markerStep: Int, positions: List<Int>): List<Tick> =
            withSubsequentTicks(markerStep.toFloat(), positions)

        private fun Tick.withSubsequentTicks(
            markerStep: Float,
            positions: List<Int>,
        ): List<Tick> {
            return sequence {
                var tickType = this@withSubsequentTicks.type
                var markerValue = this@withSubsequentTicks.markerValue
                (listOf(this@withSubsequentTicks.position) + positions).forEach { position ->
                    yield(Tick(position.toFloat(), tickType, markerValue))
                    tickType = if (tickType == MINOR) MAJOR else MINOR
                    markerValue += markerStep
                }
            }.toList()
        }
    }
}
