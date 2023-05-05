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
package ru.pixnews.feature.root.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import app.cash.turbine.testIn
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import ru.pixnews.feature.root.PixnewsRootNavigationState
import ru.pixnews.feature.root.TopLevelDestination
import ru.pixnews.feature.root.TopLevelDestination.CALENDAR
import ru.pixnews.feature.root.TopLevelDestination.COLLECTIONS
import ru.pixnews.feature.root.TopLevelDestination.PROFILE
import ru.pixnews.feature.root.rememberPixnewsRootNavigationState
import ru.pixnews.foundation.instrumented.test.base.BaseInstrumentedTest
import kotlin.time.Duration.Companion.milliseconds

class PixnewsRootNavigationStateTest : BaseInstrumentedTest() {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    // Subject under test
    private lateinit var navState: PixnewsRootNavigationState

    @Test
    fun currentDestinationShouldReflectDestinationChanges() = runTest {
        var currentDestination: String? = null

        composeTestRule.setContent {
            navController = rememberTestNavController()
            navState = rememberPixnewsRootNavigationState(navController)

            // Update currentDestination whenever it changes
            currentDestination = navState.currentDestination.value?.route

            LaunchedEffect(Unit) {
                navController.setCurrentDestination(PROFILE.route)
            }
        }
        Assert.assertEquals(PROFILE.route, currentDestination)
    }

    @Test
    fun topLevelDestinationShouldReflectDestinationChanges() = runTest {
        val testComplete: CompletableJob = Job()

        composeTestRule.setContent() {
            navController = rememberTestNavController()
            navState = rememberPixnewsRootNavigationState(navController)

            LaunchedEffect(Unit) {
                val topLevelFlow = navState.currentTopLevelDestinationFlow
                    .testIn(
                        scope = backgroundScope,
                        timeout = 10.milliseconds,
                    )

                Assert.assertEquals(CALENDAR, topLevelFlow.awaitItem())

                listOf(PROFILE, COLLECTIONS, CALENDAR).onEach { nextDestination ->
                    navState.navigateToTopLevelDestination(nextDestination)
                    Assert.assertEquals(nextDestination, topLevelFlow.expectMostRecentItem())
                }
                testComplete.complete()
            }
        }

        withTimeout(10_000) {
            testComplete.join()
        }
    }
}

@Composable
private fun rememberTestNavController(
    startDestination: TopLevelDestination = CALENDAR,
): TestNavHostController {
    val navController = TestNavHostController(LocalContext.current).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        graph = createGraph(startDestination = startDestination.route) {
            for (destination in TopLevelDestination.values()) {
                composable(destination.route) {}
            }
        }
    }
    return remember { navController }
}
