/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.screen.navigation

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.collections.test.constants.CollectionsTestTags
import ru.pixnews.feature.profile.test.constants.ProfileTestTags
import ru.pixnews.feature.root.test.constants.BottomBarTestTags

fun MacrobenchmarkScope.gotoCollectionsScreen() {
    device.findObject(By.res(BottomBarTestTags.COLLECTIONS)).click()
    device.waitForIdle()

    // Wait until collections are shown on screen
    device.wait(Until.hasObject(By.res(CollectionsTestTags.SUCCESS_CONTENT)), 10_000)
}

fun MacrobenchmarkScope.gotoProfileScreen() {
    device.findObject(By.res(BottomBarTestTags.PROFILE)).click()
    device.waitForIdle()

    // Wait until profile shown on screen
    device.wait(Until.hasObject(By.res(ProfileTestTags.SUCCESS_CONTENT)), 10_000)
}

fun MacrobenchmarkScope.gotoCalendarScreen() {
    device.findObject(By.res(BottomBarTestTags.CALENDAR)).click()
    device.waitForIdle()

    // Wait until calendar shown on screen
    device.wait(Until.hasObject(By.res(CalendarTestTag.SUCCESS_CONTENT)), 10_000)
}
