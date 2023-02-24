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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import ru.pixnews.features.root.model.PixnewsRootNavigationState
import ru.pixnews.features.root.model.navigation.TopLevelDestination
import ru.pixnews.features.root.model.rememberPixnewsRootNavigationState
import ru.pixnews.features.root.ui.PixnewsBottomNavigationBar
import ru.pixnews.features.root.ui.PixnewsNavHost

@Composable
public fun PixnewsApp(
    @Suppress("UNUSED_PARAMETER") windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    navigationState: PixnewsRootNavigationState = rememberPixnewsRootNavigationState(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            PixnewsBottomNavigationBar(
                selectedTabFlow = navigationState.currentTopLevelDestinationFlow,
                onTabClicked = onTabClicked(navigationState),
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumedWindowInsets(paddingValues)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            PixnewsNavHost(
                navController = navigationState.navController,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
@Suppress("COMPLEX_EXPRESSION")
private fun onTabClicked(navigationState: PixnewsRootNavigationState) = { targetDestination: TopLevelDestination ->
    val navController = navigationState.navController
    navController.navigate(targetDestination.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
