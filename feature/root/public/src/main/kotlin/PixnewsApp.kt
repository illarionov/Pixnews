/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.ui.tooling.PreviewComplete

@Composable
public fun PixnewsApp(
    modifier: Modifier = Modifier,
    navigationState: PixnewsRootNavigationState = rememberPixnewsRootNavigationState(),
    navHost: @Composable ColumnScope.() -> Unit = { NavHost(navState = navigationState) },
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            val selectedItem = navigationState.currentTopLevelDestinationFlow
                .collectAsState(initial = TopLevelDestination.START_DESTINATION)

            BottomNavigationBar(
                selectedTabFlow = selectedItem,
                onClickTab = navigationState::navigateToTopLevelDestination,
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
            content = navHost,
        )
    }
}

@Composable
@PreviewComplete
private fun PreviewApp() {
    PixnewsTheme(
        useDynamicColor = false,
    ) {
        PixnewsApp()
    }
}
