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

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import ru.pixnews.test.util.scrollFeedDownUp
import ru.pixnews.test.util.waitForObject

object CalendarActions {
    fun MacrobenchmarkScope.waitForContent() {
        device.wait(Until.gone(By.res("calendar:loading_overlay")), 20_00)
        val lazyList = device.waitForObject(By.res("calendar:content:lazy_list"), 60_000)
        lazyList.wait(Until.findObject(By.res("calendar:content:game_subheader")), 20_000)
    }

    fun MacrobenchmarkScope.scrollFeedDownUp() {
        val list = device.findObject(By.res("calendar:content:lazy_list"))
        device.scrollFeedDownUp(list)
    }
}
