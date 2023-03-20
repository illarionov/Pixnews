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
package ru.pixnews.test.screen.calendar

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.filters.RequiresDevice
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.pixnews.test.BenchmarkAppConfig
import ru.pixnews.test.screen.calendar.CalendarActions.waitForContent

@RequiresDevice
@RunWith(AndroidJUnit4ClassRunner::class)
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
