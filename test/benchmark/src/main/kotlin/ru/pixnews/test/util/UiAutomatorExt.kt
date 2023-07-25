/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.test.util

import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction.DOWN
import androidx.test.uiautomator.Direction.UP
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

internal fun UiDevice.scrollFeedDownUp(element: UiObject2) {
    // Set some margin from the sides to prevent triggering system navigation
    element.setGestureMargin(displayWidth / 5)

    element.fling(DOWN)
    waitForIdle()
    element.fling(UP)
}

internal fun UiDevice.waitForObject(selector: BySelector, timeout: Long = 5_000): UiObject2 {
    if (wait(Until.hasObject(selector), timeout)) {
        return findObject(selector)
    }

    error("Object with selector [$selector] not found")
}
