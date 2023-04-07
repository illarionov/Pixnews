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
