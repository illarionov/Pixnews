/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.failure

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams.RenderingMode.SHRINK
import org.junit.Rule
import org.junit.Test
import ru.pixnews.foundation.ui.theme.PixnewsTheme

class OtherNetworkErrorScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(
            locale = "en",
        ),
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SHRINK,
        showSystemUi = false,
    )

    @Test
    fun checkOtherNetworkErrorRefreshNotActive() {
        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                OtherNetworkError(
                    onRefreshClicked = { },
                    refreshActive = { false },
                )
            }
        }
    }

    @Test
    fun checkOtherNetworkErrorRefreshActive() {
        paparazzi.snapshot {
            PixnewsTheme(useDynamicColor = false) {
                OtherNetworkError(
                    onRefreshClicked = { },
                    refreshActive = { true },
                )
            }
        }
    }
}
