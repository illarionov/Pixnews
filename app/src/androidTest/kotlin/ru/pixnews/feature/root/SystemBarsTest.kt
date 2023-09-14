/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.root

import android.os.Build
import android.view.Window
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.filters.SdkSuppress
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.pixnews.MainActivity
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import ru.pixnews.library.instrumented.test.rule.NightModeRule
import ru.pixnews.library.instrumented.test.rule.NightModeRule.NightMode.DARK
import ru.pixnews.library.instrumented.test.rule.NightModeRule.NightMode.LIGHT
import ru.pixnews.library.instrumented.test.util.isTransparent

@RunWith(Enclosed::class)
class SystemBarsTest : BaseInstrumentedTest() {
    @RunWith(Parameterized::class)
    class SystemBarsApi27Test(
        uiMode: NightModeRule.NightMode,
    ) {
        @get:Rule
        val composeTestRule = createAndroidComposeRule<MainActivity>()

        @get:Rule
        val nightModelRule = NightModeRule(uiMode)
        private lateinit var window: Window

        @Before
        fun setup() {
            window = composeTestRule.activity.window

            if (Build.VERSION.SDK_INT >= 29) {
                // On API 29+, the system can modify the bar colors to maintain contrast.
                // We disable that here to make it simple to assert expected values
                window.apply {
                    isNavigationBarContrastEnforced = false
                    isStatusBarContrastEnforced = false
                }
            }
        }

        @Test
        @SdkSuppress(minSdkVersion = 27)
        fun calendarScreen_systemBarShouldBeTransparent_api27() {
            assertThat(Color(window.statusBarColor)).isTransparent()
            assertThat(Color(window.navigationBarColor)).isTransparent()
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "{0}")
            fun data(): Collection<NightModeRule.NightMode> = listOf(LIGHT, DARK)
        }
    }

    class SystemBarsApi23Test {
        @get:Rule
        val composeTestRule = createAndroidComposeRule<MainActivity>()
        private lateinit var window: Window

        @Before
        fun setup() {
            window = composeTestRule.activity.window
        }

        @Test
        @SdkSuppress(maxSdkVersion = 26)
        fun calendarScreen_checkSystemBars_api26() {
            assertThat(Color(window.statusBarColor)).isTransparent()
            assertThat(Color(window.navigationBarColor)).isEqualTo(Color.Black)
        }
    }
}
