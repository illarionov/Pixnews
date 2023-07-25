/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Composable
public fun rememberPixnewsRootNavigationState(
    navController: NavHostController = rememberNavController(),
): PixnewsRootNavigationState {
    return remember(navController) {
        PixnewsRootNavigationState(navController)
    }
}

@Stable
public class PixnewsRootNavigationState(
    public val navController: NavHostController,
) {
    public val currentDestination: State<NavDestination?>
        @Composable get() {
            val destinationFlow = remember(navController) {
                navController.currentBackStackEntryFlow.map { it.destination }
            }
            return destinationFlow.collectAsState(null)
        }

    public val currentTopLevelDestinationFlow: Flow<TopLevelDestination?>
        get() = navController.currentBackStackEntryFlow
            .map { entry ->
                entry.destination.hierarchy.firstNotNullOfOrNull { topLevelRoutes[it.route] }
            }

    public fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = false
            }
            launchSingleTop = true
            restoreState = true
        }
        navController.navigate(destination.route, topLevelNavOptions)
    }

    private companion object {
        private val topLevelRoutes = TopLevelDestination
            .values().associateBy(TopLevelDestination::route)
    }
}
