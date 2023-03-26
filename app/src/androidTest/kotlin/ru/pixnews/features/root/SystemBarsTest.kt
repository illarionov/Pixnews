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
package ru.pixnews.features.root

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
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import ru.pixnews.foundation.testing.rule.NightModeRule
import ru.pixnews.foundation.testing.rule.NightModeRule.NightMode.DARK
import ru.pixnews.foundation.testing.rule.NightModeRule.NightMode.LIGHT
import ru.pixnews.util.isTransparent

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
