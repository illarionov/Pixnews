/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.screen.calendar

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.test.util.scrollFeedDownUp
import ru.pixnews.test.util.waitForObject

object CalendarActions {
    fun MacrobenchmarkScope.waitForContent() {
        device.wait(Until.gone(By.res(CalendarTestTag.LOADING_OVERLAY)), 20_00)
        val lazyList = device.waitForObject(By.res(CalendarTestTag.CONTENT_LAZY_LIST), 60_000)
        lazyList.wait(Until.findObject(By.res(CalendarTestTag.CONTENT_GAME_SUBHEADER)), 20_000)
    }

    fun MacrobenchmarkScope.scrollFeedDownUp() {
        val list = device.findObject(By.res(CalendarTestTag.CONTENT_LAZY_LIST))
        device.scrollFeedDownUp(list)
    }
}
