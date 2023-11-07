/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.pixnews.feature.calendar.data.domain.upcoming.UpcomingReleaseTimeCategory
import ru.pixnews.feature.calendar.model.CalendarListTitle
import ru.pixnews.feature.calendar.test.constants.UpcomingReleaseGroupId
import ru.pixnews.feature.calendar.ui.success.feedMaxWidth
import ru.pixnews.feature.calendar.ui.success.gameListContentPaddings
import ru.pixnews.feature.calendar.ui.success.getLocalizedGroupTitle
import ru.pixnews.feature.calendar.ui.success.majorReleasesMinHeight
import ru.pixnews.feature.calendar.ui.success.safeContentHorizontalMin16dp
import ru.pixnews.foundation.ui.design.R.string
import ru.pixnews.foundation.ui.design.text.PixnewsGameListSubheader
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.foundation.ui.theme.md_theme_palette_primary95
import ru.pixnews.library.compose.utils.placeholder.PlaceholderDefaults
import ru.pixnews.library.compose.utils.placeholder.PlaceholderHighlight
import ru.pixnews.library.compose.utils.placeholder.placeholder
import ru.pixnews.library.compose.utils.placeholder.shimmer
import ru.pixnews.library.ui.tooling.PreviewPhones

private val majorReleasesCardWidth = 136.dp
private val calendarListTitlePlaceholder = CalendarListTitle(
    UpcomingReleaseGroupId.YearMonthDay(
        category = UpcomingReleaseTimeCategory.FEW_DAYS,
        year = 2020,
        monthNumber = 1,
        dayOfMonth = 1,
    ),
)

@Composable
internal fun InitialLoadingPlaceholder(
    modifier: Modifier = Modifier,
) {
    val safeContentPadding = WindowInsets.safeContentHorizontalMin16dp.asPaddingValues()
    Column(
        modifier = modifier
            .padding(gameListContentPaddings)
            .padding(safeContentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MajorReleasesCarouselPlaceholder()
        GameListPlaceholder()
    }
}

@Composable
private fun MajorReleasesCarouselPlaceholder(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .widthIn(max = feedMaxWidth)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PixnewsGameListSubheader(
            title = stringResource(string.major_releases_title),
            modifier = Modifier
                .align(Alignment.Start)
                .widthIn(max = feedMaxWidth)
                .shimmer(shape = RectangleShape),
        )
        Row(
            modifier = Modifier
                .heightIn(min = majorReleasesMinHeight)
                .wrapContentWidth(unbounded = true, align = Alignment.Start),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            @Suppress("MagicNumber")
            repeat(3) {
                Spacer(
                    modifier = Modifier
                        .size(width = majorReleasesCardWidth, height = 238.dp)
                        .shimmer(shape = MaterialTheme.shapes.medium),
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.GameListPlaceholder() {
    PixnewsGameListSubheader(
        title = calendarListTitlePlaceholder.getLocalizedGroupTitle(),
        modifier = Modifier
            .align(Alignment.Start)
            .widthIn(max = feedMaxWidth)
            .padding(top = 8.dp)
            .shimmer(shape = RectangleShape),
    )
    Spacer(
        modifier = Modifier
            .widthIn(max = feedMaxWidth)
            .fillMaxWidth()
            .wrapContentHeight(unbounded = true, align = Alignment.Top)
            .height(492.dp)
            .shimmer(shape = MaterialTheme.shapes.medium),
    )
}

private fun Modifier.shimmer(
    shape: Shape? = null,
    color: Color = md_theme_palette_primary95,
) = this.composed {
    placeholder(
        visible = true,
        color = color,
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = PlaceholderDefaults.shimmerHighlightColor(
                backgroundColor = MaterialTheme.colorScheme.background,
            ),
            progressForMaxAlpha = 0.8f,
        ),
        shape = shape,
    )
}

@PreviewPhones
@Composable
private fun InitialLoadingPlaceholderPreview() {
    PixnewsTheme(useDynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            InitialLoadingPlaceholder()
        }
    }
}
