/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import ru.pixnews.feature.calendar.navigation.calendarScreen
import ru.pixnews.feature.collections.navigation.collectionsScreen
import ru.pixnews.feature.profile.navigation.profileScreen

@Composable
internal fun NavHost(
    navState: PixnewsRootNavigationState,
    modifier: Modifier = Modifier,
    startDestination: String = TopLevelDestination.START_DESTINATION.route,
) {
    NavHost(
        modifier = modifier
            .fillMaxSize(),
        navController = navState.navController,
        startDestination = startDestination,
    ) {
        calendarScreen()
        collectionsScreen()
        profileScreen()
    }
}
