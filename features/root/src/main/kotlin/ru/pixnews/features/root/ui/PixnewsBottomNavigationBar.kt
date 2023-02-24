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
package ru.pixnews.features.root.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.Flow
import ru.pixnews.features.root.model.navigation.TopLevelDestination

@Composable
public fun PixnewsBottomNavigationBar(
    selectedTabFlow: Flow<TopLevelDestination?>,
    onTabClicked: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val selectedTab by selectedTabFlow.collectAsState(initial = TopLevelDestination.START_DESTINATION)
        TopLevelDestination.values().forEach { destination ->
            DestinationNavigationBarItem(
                destination,
                selectedTab == destination,
                onTabClicked,
            )
        }
    }
}

@Composable
private fun RowScope.DestinationNavigationBarItem(
    destination: TopLevelDestination,
    selected: Boolean,
    onTabClicked: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigationBarItemColors = NavigationBarItemDefaults.colors(
        indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
    )

    CompositionLocalProvider(LocalRippleTheme provides NavigationBarItemRippleTheme) {
        val title = stringResource(destination.title)
        NavigationBarItem(
            modifier = modifier,
            selected = selected,
            onClick = { onTabClicked(destination) },
            alwaysShowLabel = true,
            colors = navigationBarItemColors,
            label = {
                Text(title)
            },
            icon = {
                DestinationIcon(selected, destination)
            },
        )
    }
}

@Composable
private fun DestinationIcon(
    selected: Boolean,
    destination: TopLevelDestination,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier,
        imageVector = if (selected) destination.icon.filled else destination.icon.unfilled,
        contentDescription = stringResource(destination.contentDescription),
    )
}

@Immutable
private object NavigationBarItemRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = MaterialTheme.colorScheme.secondary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(
        draggedAlpha = 0.16f,
        focusedAlpha = 0.12f,
        hoveredAlpha = 0.08f,
        pressedAlpha = 0.12f,
    )
}
