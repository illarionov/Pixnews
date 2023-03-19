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
package ru.pixnews.test.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test
import ru.pixnews.test.BenchmarkAppConfig
import ru.pixnews.test.screen.calendar.CalendarActions
import ru.pixnews.test.screen.navigation.gotoCalendarScreen
import ru.pixnews.test.screen.navigation.gotoCollectionsScreen
import ru.pixnews.test.screen.navigation.gotoProfileScreen

/**
 * Generates a baseline profile which can be copied to `app/src/main/baseline-prof.txt`
 * Should be run the test on a rooted device/emulator.
 *
 * Output: `[name-of-test]-baseline.prof.txt` in
 * `macrobenchmark/build/outputs/connected_android_test_additional_output/benchmark/[device name]`
 */
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generateBaselineProfile() {
        baselineProfileRule.collectBaselineProfile(packageName = BenchmarkAppConfig.targetPackage) {
            pressHome()
            startActivityAndWait()

            with(CalendarActions) {
                waitForContent()
                scrollFeedDownUp()

                gotoCollectionsScreen()
                gotoProfileScreen()

                gotoCalendarScreen()
                waitForContent()
            }
        }
    }
}
