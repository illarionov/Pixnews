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
package ru.pixnews.features.calendar.ui.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.tappableElement
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.features.calendar.PreviewFixtures.FilterChip
import ru.pixnews.features.calendar.R.string
import ru.pixnews.features.calendar.model.GameListFilterChip
import ru.pixnews.features.calendar.model.GameListFilterChipStyle.SELECTED
import ru.pixnews.features.calendar.model.GameListViewMode
import ru.pixnews.features.calendar.model.GameListViewMode.GRID
import ru.pixnews.foundation.ui.assets.icons.content.ContentIcons
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun ChipsRow(
    chips: ImmutableList<GameListFilterChip>,
    viewMode: State<GameListViewMode>,
    onFilterChipClick: (String) -> Unit,
    onOpenFilterClick: () -> Unit,
    onViewModeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .windowInsetsPadding(
                WindowInsets.tappableElement.only(WindowInsetsSides.End),
            )
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val contentPadding = WindowInsets(left = 16.dp, right = 8.dp)
            .union(WindowInsets.tappableElement.only(WindowInsetsSides.Start))
            .asPaddingValues()

        val surfaceColor = MaterialTheme.colorScheme.surface
        @Suppress("MagicNumber")
        LazyRow(
            modifier = Modifier
                .weight(1f, fill = true)
                .drawWithCache {
                    val brush = Brush.horizontalGradient(
                        0.8f to surfaceColor.copy(alpha = 0f),
                        1f to surfaceColor.copy(alpha = 1f),
                    )
                    this.onDrawWithContent {
                        drawContent()
                        drawRect(brush)
                    }
                },
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = chips.size,
                key = { chips[it].id },
                contentType = { chips[it].style },
            ) { chipIndex ->
                val chip = chips[chipIndex]
                FilterChip(
                    selected = chip.style == SELECTED,
                    onClick = { onFilterChipClick(chip.id) },
                    leadingIcon = if (chip.style == SELECTED) {
                        {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    },
                    label = {
                        Text(chip.label)
                    },
                )
            }
        }

        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant,
        ) {
            IconButton(
                onClick = onOpenFilterClick,
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = ContentIcons.FilterList,
                    contentDescription = stringResource(string.open_filter_menu_content_description),
                )
            }
            IconButton(
                onClick = { onViewModeClick() },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = viewMode.value.icon,
                    contentDescription = stringResource(viewMode.value.contentDescription),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChipsRowPreview() {
    PixnewsTheme(
        useDynamicColor = false,
    ) {
        val viewMode = remember { mutableStateOf(GRID) }
        Surface {
            ChipsRow(
                chips = FilterChip.sampleChips.toImmutableList(),
                viewMode = remember { mutableStateOf(GRID) },
                onOpenFilterClick = {},
                onViewModeClick = { viewMode.value = viewMode.value.next() },
                onFilterChipClick = {},
            )
        }
    }
}
