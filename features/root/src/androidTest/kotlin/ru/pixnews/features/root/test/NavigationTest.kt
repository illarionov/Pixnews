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
package ru.pixnews.features.root.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.pixnews.features.root.BOTTOM_NAVIGATION_BAR_TEST_TAG
import ru.pixnews.features.root.CALENDAR_CONTENT_TEST_TAG
import ru.pixnews.features.root.COLLECTIONS_CONTENT_TEST_TAG
import ru.pixnews.features.root.FakeNavHost
import ru.pixnews.features.root.PROFILE_CONTENT_TEST_TAG
import ru.pixnews.features.root.PixnewsApp
import ru.pixnews.features.root.TopLevelDestination
import ru.pixnews.features.root.TopLevelDestination.CALENDAR
import ru.pixnews.features.root.TopLevelDestination.COLLECTIONS
import ru.pixnews.features.root.TopLevelDestination.PROFILE
import ru.pixnews.features.root.rememberPixnewsRootNavigationState
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.DefaultAppConfig
import ru.pixnews.foundation.ui.theme.PixnewsTheme

class NavigationTest {
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
            collectionsTab.button().performClick()
            assertActiveTab(COLLECTIONS)

            calendarTab.button().performClick()
            assertActiveTab(CALENDAR)
        }
    }

    @Test
    fun testNavigateToProfile() {
        with(screen) {
            profileTab.button().performClick()
            assertActiveTab(PROFILE)

            calendarTab.button().performClick()
            assertActiveTab(CALENDAR)
        }
    }

    @Test(expected = NoActivityResumedException::class)
    fun homeScreen_collections_back_quitApp() {
        with(screen) {
            // GIVEN the user navigates to the Collections destination
            collectionsTab.button().performClick()
            // and then navigates to the Calendar destination
            calendarTab.button().performClick()
            // WHEN the user uses the system button/gesture to go back
            Espresso.pressBack()
            // THEN the app quits
        }
    }

    @Test
    fun backFromAnyDestinationShouldReturnToCalendar() {
        with(screen) {
            listOf(collectionsTab, profileTab).forEach { tab ->
                tab.button().performClick()
                Espresso.pressBack()
                assertActiveTab(CALENDAR)
            }
        }
    }

    private class NavigationTestScreen(
        private val composeRule: AndroidComposeTestRule<*, *>,
    ) {
        private val bottomBar = BottomBar(composeRule)
        val calendarTab: Tab = Tab(
            destination = CALENDAR,
            buttonMatcher = bottomBar.calendarButton,
            contentMatcher = hasTestTag(CALENDAR_CONTENT_TEST_TAG),
        )
        val profileTab: Tab = Tab(
            destination = PROFILE,
            buttonMatcher = bottomBar.profileButton,
            contentMatcher = hasTestTag(PROFILE_CONTENT_TEST_TAG),
        )
        val collectionsTab: Tab = Tab(
            destination = COLLECTIONS,
            buttonMatcher = bottomBar.collectionsButton,
            contentMatcher = hasTestTag(COLLECTIONS_CONTENT_TEST_TAG),
        )
        val tabs: Map<TopLevelDestination, Tab> = listOf(calendarTab, profileTab, collectionsTab)
            .associateBy(Tab::destination)

        fun assertActiveTab(destination: TopLevelDestination) {
            tabs.values.forEach {
                if (destination == it.destination) {
                    it.assertActive()
                } else {
                    it.assertNotActive()
                }
            }
        }

        public inner class Tab(
            val destination: TopLevelDestination,
            val buttonMatcher: SemanticsMatcher,
            val contentMatcher: SemanticsMatcher,
        ) {
            fun button(): SemanticsNodeInteraction = composeRule.onNode(buttonMatcher)
            fun content(): SemanticsNodeInteraction = composeRule.onNode(contentMatcher)

            fun assertActive() {
                button().assertIsSelected()
                content().assertExists()
            }

            fun assertNotActive() {
                button().assertIsNotSelected()
                content().assertDoesNotExist()
            }
        }
}

    private class BottomBar(
        composeRule: AndroidComposeTestRule<*, *>,
    ) {
        private val context = composeRule.activity
        val root: SemanticsMatcher = hasTestTag(BOTTOM_NAVIGATION_BAR_TEST_TAG)
        var calendarDestination = context.getString(CALENDAR.title)
        var profileDestination = context.getString(PROFILE.title)
        var collectionsDestination = context.getString(COLLECTIONS.title)
        val calendarButton = hasText(calendarDestination).and(hasAnyAncestor(root))
        val profileButton = hasText(profileDestination).and(hasAnyAncestor(root))
        val collectionsButton = hasText(collectionsDestination).and(hasAnyAncestor(root))
    }
}
