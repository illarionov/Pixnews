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
package ru.pixnews.feature.navigation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.feature.root.PixnewsApp
import ru.pixnews.feature.root.TopLevelDestination
import ru.pixnews.feature.root.TopLevelDestination.CALENDAR
import ru.pixnews.feature.root.TopLevelDestination.COLLECTIONS
import ru.pixnews.feature.root.TopLevelDestination.PROFILE
import ru.pixnews.feature.root.rememberPixnewsRootNavigationState
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.DefaultAppConfig
import ru.pixnews.foundation.testing.base.BaseInstrumentedTest
import ru.pixnews.foundation.ui.theme.PixnewsTheme

class NavigationTest : BaseInstrumentedTest() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val appConfig: AppConfig = DefaultAppConfig()
    private lateinit var screen: NavigationTestScreen

    @Before
    fun setup() {
        screen = NavigationTestScreen(composeTestRule)
        composeTestRule.setContent {
            PixnewsTheme(
                appConfig = appConfig,
                useDynamicColor = false,
            ) {
                val navigationState = rememberPixnewsRootNavigationState()
                PixnewsApp(
                    navigationState = navigationState,
                    navHost = { FakeNavHost(navState = navigationState) },
                )
            }
        }
    }

    @Test
    fun firstScreen_shouldBeCalendar() {
        screen.assertActiveTab(CALENDAR)
    }

    @Test
    fun testNavigateToCollectionsAndBack() {
        with(screen) {
            bottomBar.button(COLLECTIONS).performClick()
            assertActiveTab(COLLECTIONS)

            bottomBar.button(CALENDAR).performClick()
            assertActiveTab(CALENDAR)
        }
    }

    @Test
    fun testNavigateToProfile() {
        with(screen) {
            bottomBar.button(PROFILE).performClick()
            assertActiveTab(PROFILE)

            bottomBar.button(CALENDAR).performClick()
            assertActiveTab(CALENDAR)
        }
    }

    @Test(expected = NoActivityResumedException::class)
    fun homeScreen_collections_back_quitApp() {
        with(screen) {
            // GIVEN the user navigates to the Collections destination
            bottomBar.button(COLLECTIONS).performClick()
            // and then navigates to the Calendar destination
            bottomBar.button(CALENDAR).performClick()
            // WHEN the user uses the system button/gesture to go back
            Espresso.pressBack()
            // THEN the app quits
        }
    }

    @Test
    fun backFromAnyDestinationShouldReturnToCalendar() {
        with(screen) {
            listOf(COLLECTIONS, PROFILE).forEach { tab ->
                bottomBar.button(tab).performClick()
                Espresso.pressBack()
                assertActiveTab(CALENDAR)
            }
        }
    }

    private class NavigationTestScreen(
        private val composeRule: AndroidComposeTestRule<*, *>,
    ) {
        val bottomBar = BottomBarElement(composeRule)
        private val TopLevelDestination.contentMatcher: SemanticsMatcher
            get() = when (this) {
                CALENDAR -> hasTestTag(CALENDAR_CONTENT_TEST_TAG)
                PROFILE -> hasTestTag(PROFILE_CONTENT_TEST_TAG)
                COLLECTIONS -> hasTestTag(COLLECTIONS_CONTENT_TEST_TAG)
            }

        fun TopLevelDestination.bottomBarContent() = composeRule.onNode(this.contentMatcher)

        fun assertActiveTab(tab: TopLevelDestination) {
            bottomBar.assertActiveTab(tab)
            for (destination in TopLevelDestination.values()) {
                if (tab == destination) {
                    destination.bottomBarContent().assertExists()
                } else {
                    destination.bottomBarContent().assertDoesNotExist()
                }
            }
        }
    }
}
