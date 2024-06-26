/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.success

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.PreviewFixtures.MajorRelease
import ru.pixnews.feature.calendar.model.MajorReleaseCarouselItemUiModel
import ru.pixnews.foundation.ui.design.card.PixnewsGameCardGridSmall
import ru.pixnews.foundation.ui.design.text.PixnewsGameListSubheader
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.foundation.ui.design.R as uiDesignR

internal val majorReleasesMinHeight = 187.dp

@Composable
internal fun MajorReleasesCarousel(
    releases: ImmutableList<MajorReleaseCarouselItemUiModel>,
    onReleaseClick: (GameId) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = WindowInsets.safeContentHorizontalMin16dp.asPaddingValues(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(8.dp),
    ) {
        PixnewsGameListSubheader(
            title = stringResource(uiDesignR.string.major_releases_title),
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
                .heightIn(min = majorReleasesMinHeight),
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
