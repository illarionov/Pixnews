/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PREVIEW_ANNOTATION")

package ru.pixnews.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.pixnews.foundation.ui.theme.PixnewsTheme

class BottomNavigationBarScreenshots {
    @Composable
    @Preview(
        locale = "en",
        showBackground = true,
    )
    fun YopLevelDestinationCalendar(
        @PreviewParameter(BottomNavigationBarTabProvider::class) selectedTab: TopLevelDestination,
    ) {
        PixnewsTheme(
            useDynamicColor = false,
        ) {
            val destination = remember { mutableStateOf(selectedTab) }

            BottomNavigationBar(
                selectedTabFlow = destination,
                onTabClicked = {},
            )
        }
    }

    class BottomNavigationBarTabProvider : PreviewParameterProvider<TopLevelDestination> {
        override val values: Sequence<TopLevelDestination> = TopLevelDestination.entries.asSequence()
    }
}
