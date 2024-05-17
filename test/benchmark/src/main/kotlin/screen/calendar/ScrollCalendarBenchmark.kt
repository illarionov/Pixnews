/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.screen.calendar

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.RequiresDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.pixnews.test.BenchmarkAppConfig
import ru.pixnews.test.screen.calendar.CalendarActions.waitForContent

@RequiresDevice
@RunWith(AndroidJUnit4::class)
public class ScrollCalendarBenchmark {
    @get:Rule
    val baselineProfileRule = MacrobenchmarkRule()

    @Test
    fun scrollFeed() = baselineProfileRule.measureRepeated(
        packageName = BenchmarkAppConfig.targetPackage,
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.DEFAULT,
        startupMode = StartupMode.WARM,
        iterations = 5,
        setupBlock = {
            pressHome()
            startActivityAndWait()
            waitForContent()
        },
    ) {
        with(CalendarActions) {
            scrollFeedDownUp()
        }
    }
}
