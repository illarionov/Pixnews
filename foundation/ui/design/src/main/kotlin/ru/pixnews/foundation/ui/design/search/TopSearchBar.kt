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
package ru.pixnews.foundation.ui.design.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.pixnews.foundation.ui.design.R

@Composable
public fun PixnewsTopSearchBar(
    query: String,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onClearTextClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 0.dp,
    colors: SearchBarColors = SearchBarDefaults.colors(
        containerColor = MaterialTheme.colorScheme.onPrimary,
    ),
    placeholderText: String = stringResource(id = R.string.search_game_placeholder),
    windowInsets: WindowInsets = WindowInsets.statusBars.add(WindowInsets(top = 7.dp)),
    content: @Composable ColumnScope.() -> Unit,
) {
    // Talkback focus order sorts based on x and y position before considering z-index. The
    // extra Box with semantics and fillMaxWidth is a workaround to get the search bar to focus
    // before the content.
    Box(
        modifier
            .semantics { isContainer = true }
            .zIndex(1f)
            .fillMaxWidth(),
    ) {
        val focusManager = LocalFocusManager.current

        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = query,
            onQueryChange = onQueryChange,
            tonalElevation = tonalElevation,
            colors = colors,
            onSearch = {
                focusManager.clearFocus()
                onSearch(it)
            },
            active = active,
            onActiveChange = onActiveChange,
            placeholder = { Text(placeholderText) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onClearTextClick(query)
                        },
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.search_game_clear_field_content_description),
                        )
                    }
                }
            },
            windowInsets = windowInsets,
            content = content,
        )
    }
}
