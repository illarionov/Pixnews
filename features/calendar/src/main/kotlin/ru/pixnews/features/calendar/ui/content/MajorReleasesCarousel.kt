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

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.features.calendar.PreviewFixtures.MajorRelease
import ru.pixnews.features.calendar.R.string
import ru.pixnews.features.calendar.model.MajorReleaseCarouselItemUiModel
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardGridSmall
import ru.pixnews.foundation.ui.design.text.PixnewsGameListSubheader
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun MajorReleasesCarousel(
    releases: ImmutableList<MajorReleaseCarouselItemUiModel>,
    onReleaseClick: (GameId) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = WindowInsets.safeContent.only(WindowInsetsSides.Horizontal)
        .union(WindowInsets(left = 16.dp, right = 16.dp))
        .asPaddingValues(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(8.dp),
    ) {
        PixnewsGameListSubheader(
            title = stringResource(string.major_releases_title),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .widthIn(max = feedMaxWidth)
                .fillMaxWidth()
                .padding(contentPadding),
        )

        val state = rememberLazyListState()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 187.dp),
            contentPadding = contentPadding,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(releases.size) {
                PixnewsGameCardGridSmall(
                    game = releases[it],
                    onClick = onReleaseClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCalendarScreenContent() {
    PixnewsTheme(useDynamicColor = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                MajorReleasesCarousel(
                    releases = persistentListOf(
                        MajorRelease.halflife3,
                        MajorRelease.beyondgoodandevil2,
                        MajorRelease.thesims5,
                        MajorRelease.hytale,
                        MajorRelease.starwarseclipse,
                    ),
                    onReleaseClick = {},
                )
            }
        }
    }
}
