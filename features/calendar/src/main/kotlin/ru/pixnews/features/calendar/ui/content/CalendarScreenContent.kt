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
package ru.pixnews.features.calendar.ui.content

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.features.calendar.PreviewFixtures
import ru.pixnews.features.calendar.model.CalendarListItem
import ru.pixnews.features.calendar.model.CalendarListPixnewsGameUi
import ru.pixnews.features.calendar.model.CalendarListTitle
import ru.pixnews.features.calendar.model.MajorReleaseCarouselItemUiModel
import ru.pixnews.foundation.ui.design.card.PixnewsGameCard
import ru.pixnews.foundation.ui.design.text.PixnewsGameListSubheader
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.libraries.ui.tooling.DevicePreviews

internal val feedMaxWidth = 552.dp

@Composable
internal fun CalendarScreenContent(
    majorReleases: ImmutableList<MajorReleaseCarouselItemUiModel>,
    games: ImmutableList<CalendarListItem>,
    onMajorReleaseClick: (GameId) -> Unit,
    onGameClick: (GameId) -> Unit,
    onFavouriteClick: (GameId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    val contentPaddings = PaddingValues(top = 8.dp, bottom = 24.dp)
    val listItemsPadding = WindowInsets.safeContent.only(WindowInsetsSides.Horizontal)
        .union(WindowInsets(left = 16.dp, right = 16.dp))
        .asPaddingValues()

    LazyColumn(
        modifier = modifier
            .testTag("calendar:content:lazy_list")
            .fillMaxWidth(),
        contentPadding = contentPaddings,
        state = state,
        verticalArrangement = spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            MajorReleasesCarousel(
                modifier = Modifier
                    .testTag("calendar:content:major_releases_carousel"),
                releases = majorReleases,
                onReleaseClick = onMajorReleaseClick,
                contentPadding = listItemsPadding,
            )
        }
        items(
            count = games.size,
            contentType = { index -> games[index].uniqueId.contentType },
            key = { index -> games[index].uniqueId },
        ) { gameIndex ->
            val currentItem = games[gameIndex]

            if (gameIndex != 0) {
                val predItem = games[gameIndex - 1]
                val height = when {
                    currentItem is CalendarListTitle -> 8.dp
                    predItem is CalendarListTitle && currentItem is CalendarListPixnewsGameUi -> 0.dp
                    else -> 16.dp
                }
                if (height != 0.dp) {
                    Spacer(modifier = Modifier.height(height))
                }
            }

            when (currentItem) {
                is CalendarListTitle ->
                    GameSubheader(
                        modifier = Modifier
                            .widthIn(max = feedMaxWidth)
                            .padding(listItemsPadding),
                        title = currentItem.title,
                    )

                is CalendarListPixnewsGameUi -> PixnewsGameCard(
                    modifier = Modifier
                        .widthIn(max = feedMaxWidth)
                        .padding(listItemsPadding),
                    game = currentItem,
                    onClick = { onGameClick(currentItem.gameId) },
                    onFavouriteClick = { onFavouriteClick(currentItem.gameId) },
                )
            }
        }
    }
}

@Composable
internal fun GameSubheader(
    title: String,
    modifier: Modifier = Modifier,
) {
    PixnewsGameListSubheader(
        title = title,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .testTag("calendar:content:game_subheader"),
    )
}

@DevicePreviews
@Composable
private fun PreviewCalendarScreenContent() {
    PixnewsTheme(
        useDynamicColor = false,
    ) {
        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CalendarScreenContent(
                    majorReleases = PreviewFixtures.previewSuccessState.majorReleases,
                    games = PreviewFixtures.previewSuccessState.games,
                    onMajorReleaseClick = {},
                    onFavouriteClick = {},
                    onGameClick = {},
                )
            }
        }
    }
}
