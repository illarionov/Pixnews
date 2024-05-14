/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
