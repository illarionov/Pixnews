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

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.compose.composable
import ru.pixnews.feature.calendar.navigation.CALENDAR_ROUTE
import ru.pixnews.feature.collections.navigation.COLLECTIONS_ROUTE
import ru.pixnews.feature.profile.navigation.PROFILE_ROUTE
import ru.pixnews.feature.root.PixnewsRootNavigationState
import ru.pixnews.feature.root.TopLevelDestination

internal const val CALENDAR_CONTENT_TEST_TAG = "calendar_content"
internal const val COLLECTIONS_CONTENT_TEST_TAG = "collections_content"
internal const val PROFILE_CONTENT_TEST_TAG = "profile_content"

/**
 * Test NavHost without content
 */
@Composable
internal fun FakeNavHost(
    navState: PixnewsRootNavigationState,
    modifier: Modifier = Modifier,
    startDestination: String = TopLevelDestination.START_DESTINATION.route,
) {
    androidx.navigation.compose.NavHost(
        modifier = modifier,
        navController = navState.navController,
        startDestination = startDestination,
    ) {
        composable(route = CALENDAR_ROUTE) {
            Box(modifier = Modifier.testTag(CALENDAR_CONTENT_TEST_TAG))
        }
        composable(route = COLLECTIONS_ROUTE) {
            Box(modifier = Modifier.testTag(COLLECTIONS_CONTENT_TEST_TAG))
        }
        composable(route = PROFILE_ROUTE) {
            Box(modifier = Modifier.testTag(PROFILE_CONTENT_TEST_TAG))
        }
    }
}
