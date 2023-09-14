/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemGestures
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.feature.calendar.PreviewFixtures.FilterChip
import ru.pixnews.feature.calendar.model.GameListFilterChip
import ru.pixnews.feature.calendar.model.GameListFilterChipStyle.SELECTED
import ru.pixnews.feature.calendar.model.GameListViewMode
import ru.pixnews.feature.calendar.model.GameListViewMode.GRID
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.foundation.ui.assets.icons.content.ContentIcons
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.foundation.ui.design.R as uiDesignR

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
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val contentPadding = WindowInsets(left = 16.dp, right = 16.dp)
            .union(WindowInsets.systemGestures.only(WindowInsetsSides.Start))
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
                }
                .testTag(CalendarTestTag.HEADER_CHIPS_LAZY_ROW),
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
                    contentDescription = stringResource(uiDesignR.string.open_filter_menu_content_description),
                )
            }
            IconButton(
                onClick = { onViewModeClick() },
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.systemGestures.only(WindowInsetsSides.End)
                            .exclude(WindowInsets(right = 12.dp))
                            .union(WindowInsets(right = 4.dp)),
                    )
                    .size(48.dp),
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
