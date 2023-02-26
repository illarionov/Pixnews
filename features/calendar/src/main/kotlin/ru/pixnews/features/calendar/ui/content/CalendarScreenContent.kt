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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import ru.pixnews.libraries.ui.tooling.PhonePreviews

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
    val contentPaddings = WindowInsets.systemBars
        .add(WindowInsets(bottom = 24.dp))
    val listItemsPadding = WindowInsets.safeContent.only(WindowInsetsSides.Horizontal)
        .union(WindowInsets(left = 16.dp, right = 16.dp))
        .asPaddingValues()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPaddings.asPaddingValues(),
        state = state,
        verticalArrangement = spacedBy(8.dp),
    ) {
        item {
            MajorReleasesCarousel(
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
            when (val item = games[gameIndex]) {
                is CalendarListTitle -> GameSubheader(
                    modifier = Modifier.padding(listItemsPadding),
                    title = item.title,
                )

                is CalendarListPixnewsGameUi -> PixnewsGameCard(
                    modifier = Modifier.padding(listItemsPadding),
                    game = item,
                    onClick = { onGameClick(item.gameId) },
                    onFavouriteClick = { onFavouriteClick(item.gameId) },
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
            .padding(
                bottom = 8.dp,
            ),
    )
}

@PhonePreviews
@Composable
private fun PreviewCalendarScreenContent() {
    PixnewsTheme(
        useDynamicColor = false,
    ) {
        Surface {
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
