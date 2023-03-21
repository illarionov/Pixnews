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
package ru.pixnews.features.calendar

import android.os.Build
import android.view.Window
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import assertk.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.MainActivity
import ru.pixnews.features.navigation.BottomBarElement
import ru.pixnews.features.root.TopLevelDestination.CALENDAR
import ru.pixnews.test.util.isTransparent

class SystemBarsTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var bottomBar: BottomBarElement
    private lateinit var window: Window

    @Before
    fun setup() {
        window = composeTestRule.activity.window

        bottomBar = BottomBarElement(composeTestRule)
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
    fun calendarScreen_shouldBeFirstScreen() {
        bottomBar.assertActiveTab(CALENDAR)
    }

    @Test
    fun calendarScreen_systemBarShouldBeTransparent() {
        assertThat(Color(window.statusBarColor)).isTransparent()
        assertThat(Color(window.navigationBarColor)).isTransparent()
    }
}
