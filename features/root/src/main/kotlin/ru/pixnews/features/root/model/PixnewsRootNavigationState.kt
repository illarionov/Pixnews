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
package ru.pixnews.features.root.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.pixnews.features.root.model.navigation.TopLevelDestination

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
    public val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntry?.destination

    public val currentTopLevelDestinationFlow: Flow<TopLevelDestination?>
        get() = navController.currentBackStackEntryFlow
            .map { entry ->
                entry.destination.hierarchy.firstNotNullOfOrNull { topLevelRoutes[it.route] }
            }

    private companion object {
        private val topLevelRoutes = TopLevelDestination
            .values().associateBy(TopLevelDestination::route)
    }
}
