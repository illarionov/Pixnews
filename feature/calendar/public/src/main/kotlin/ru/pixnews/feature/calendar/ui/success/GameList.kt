/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.success

import android.content.res.Resources
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.PreviewFixtures
import ru.pixnews.feature.calendar.model.CalendarListItem
import ru.pixnews.feature.calendar.model.CalendarListPixnewsGameUi
import ru.pixnews.feature.calendar.model.CalendarListTitle
import ru.pixnews.feature.calendar.model.MajorReleaseCarouselItemUiModel
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag.CONTENT_GAME_SUBHEADER
import ru.pixnews.feature.calendar.test.constants.upcomingReleaseGroup
import ru.pixnews.feature.calendar.util.CalendarScreenSubheaderLocalization
import ru.pixnews.foundation.ui.design.card.PixnewsGameCard
import ru.pixnews.foundation.ui.design.text.PixnewsGameListSubheader
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.compose.utils.defaultLocale
import ru.pixnews.library.ui.tooling.PreviewDevices
import ru.pixnews.library.ui.tooling.debuglayout.DebugLayout.Companion.debugLayout
import ru.pixnews.library.ui.tooling.debuglayout.mediumLaptopScreen12Columns
import java.util.Locale

internal val feedMaxWidth = 552.dp
internal val gameListContentPaddings = PaddingValues(top = 8.dp, bottom = 24.dp)

internal val WindowInsets.Companion.safeContentHorizontalMin16dp: WindowInsets
    @Composable
    get() = safeContent
        .only(WindowInsetsSides.Horizontal)
        .union(WindowInsets(left = 16.dp, right = 16.dp))

@Composable
internal fun GameList(
    majorReleases: ImmutableList<MajorReleaseCarouselItemUiModel>,
    games: ImmutableList<CalendarListItem>,
    onMajorReleaseClick: (GameId) -> Unit,
    onGameClick: (GameId) -> Unit,
    onFavouriteClick: (GameId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    val listItemsPadding = WindowInsets.safeContentHorizontalMin16dp.asPaddingValues()

    LazyColumn(
        modifier = modifier
            .testTag(CalendarTestTag.CONTENT_LAZY_LIST)
            .fillMaxWidth(),
        contentPadding = gameListContentPaddings,
        state = state,
        verticalArrangement = spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            MajorReleasesCarousel(
                modifier = Modifier
                    .testTag(CalendarTestTag.CONTENT_MAJOR_RELEASES_CAROUSEL),
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
                is CalendarListTitle -> PixnewsGameListSubheader(
                    title = currentItem.getLocalizedGroupTitle(),
                    modifier = Modifier
                        .widthIn(max = feedMaxWidth)
                        .padding(listItemsPadding)
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .semantics {
                            upcomingReleaseGroup = currentItem.groupId
                            testTag = CONTENT_GAME_SUBHEADER
                        },
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
internal fun CalendarListTitle.getLocalizedGroupTitle(
    locale: Locale = defaultLocale(),
    resources: Resources = LocalContext.current.resources,
): String {
    val groupSubheaderLocalization = remember(locale, resources) {
        CalendarScreenSubheaderLocalization(locale, resources)
    }
    return groupSubheaderLocalization.localize(this.groupId)
}

@PreviewDevices
@Composable
private fun PreviewCalendarScreenContent() {
    PixnewsTheme(
        useDynamicColor = false,
    ) {
        Surface(
            modifier = Modifier.debugLayout {
                mediumLaptopScreen12Columns(true)
            },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GameList(
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
