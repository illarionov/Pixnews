/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
        baselineProfileRule.collect(packageName = BenchmarkAppConfig.targetPackage) {
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
