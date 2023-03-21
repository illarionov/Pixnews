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

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import ru.pixnews.foundation.ui.design.navigation.PixnewsBottomNavigationBar
import ru.pixnews.foundation.ui.design.navigation.PixnewsBottomNavigationBarItem

@VisibleForTesting
public const val BOTTOM_NAVIGATION_BAR_TEST_TAG: String = "bottom_navigation_bar"

@Composable
internal fun BottomNavigationBar(
    selectedTabFlow: State<TopLevelDestination?>,
    onTabClicked: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    PixnewsBottomNavigationBar(
        modifier = modifier
            .testTag(BOTTOM_NAVIGATION_BAR_TEST_TAG),
    ) {
        TopLevelDestination.values().forEach { destination ->
            DestinationNavigationBarItem(
                destination = destination,
                isSelected = remember {
                    derivedStateOf(structuralEqualityPolicy()) {
                        selectedTabFlow.value == destination
                    }
                },
                onTabClicked = onTabClicked,
            )
        }
    }
}

@Composable
private fun RowScope.DestinationNavigationBarItem(
    destination: TopLevelDestination,
    isSelected: State<Boolean>,
    onTabClicked: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    PixnewsBottomNavigationBarItem(
        modifier = modifier
            .testTag("bottom_bar:${destination.route}"),
        selected = isSelected.value,
        onClick = { onTabClicked(destination) },
        label = {
            Text(
                text = stringResource(destination.title),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        },
        icon = {
            Icon(
                imageVector = if (isSelected.value) destination.icon.filled else destination.icon.unfilled,
                contentDescription = stringResource(destination.contentDescription),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        },
    )
}
