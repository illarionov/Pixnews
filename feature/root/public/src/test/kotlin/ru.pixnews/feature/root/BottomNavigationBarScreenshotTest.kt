/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.root

import androidx.compose.runtime.mutableStateOf
import app.cash.paparazzi.DeviceConfig.Companion.PIXEL_5
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams.RenderingMode.SHRINK
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@RunWith(Parameterized::class)
class BottomNavigationBarScreenshotTest(
    val topLevelDestination: TopLevelDestination,
) {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SHRINK,
        showSystemUi = false,
    )

    @Test
    fun checkBottomBarWithSelectedDestination() {
        val destination = mutableStateOf(topLevelDestination)
        paparazzi.snapshot {
            PixnewsTheme(
                useDynamicColor = false,
            ) {
                BottomNavigationBar(
                    selectedTabFlow = destination,
                    onTabClicked = {},
                )
            }
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<TopLevelDestination> {
            return TopLevelDestination.values().toList()
        }
    }
}
