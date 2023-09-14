/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
