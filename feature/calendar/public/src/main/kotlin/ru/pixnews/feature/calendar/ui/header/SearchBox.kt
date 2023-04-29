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
package ru.pixnews.feature.calendar.ui.header

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pixnews.foundation.ui.design.search.PixnewsTopSearchBar

@Composable
internal fun SearchBox(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    defaultSearchQuery: String = "",
    defaultActive: Boolean = false,
) {
    var searchQuery by rememberSaveable { mutableStateOf(defaultSearchQuery) }
    val focusManager = LocalFocusManager.current
    var active: Boolean by rememberSaveable { mutableStateOf(defaultActive) }

    PixnewsTopSearchBar(
        modifier = modifier,
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = {
            active = false
            onSearch(it)
        },
        active = active,
        onActiveChange = {
            active = it
            if (!active) {
                focusManager.clearFocus()
            }
        },
        onClearTextClick = { searchQuery = "" },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            @Suppress("MagicNumber")
            items(3) { idx ->
                val resultText = "Suggestion $idx"
                ListItem(
                    headlineContent = { Text(resultText) },
                    supportingContent = { Text("Additional info") },
                    modifier = Modifier.clickable {
                        searchQuery = resultText
                        active = false
                        focusManager.clearFocus()
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchBoxPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            SearchBox(onSearch = {}, defaultSearchQuery = "Tetris")
        }
    }
}

@Preview
@Composable
private fun SearchBoxPreviewExtended() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize(),
        ) {
            SearchBox(onSearch = {}, defaultActive = true)
        }
    }
}
