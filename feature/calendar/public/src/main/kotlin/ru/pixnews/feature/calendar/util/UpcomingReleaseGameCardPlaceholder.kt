/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import ru.pixnews.feature.calendar.ui.success.feedMaxWidth
import ru.pixnews.foundation.ui.theme.md_theme_palette_primary95
import ru.pixnews.library.compose.utils.placeholder.PlaceholderDefaults
import ru.pixnews.library.compose.utils.placeholder.PlaceholderHighlight
import ru.pixnews.library.compose.utils.placeholder.placeholder
import ru.pixnews.library.compose.utils.placeholder.shimmer

@Composable
internal fun PixnewsGameCardPlaceholder(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .widthIn(max = feedMaxWidth)
            .fillMaxWidth()
            .wrapContentHeight(unbounded = true, align = Alignment.Top)
            .height(492.dp)
            .shimmer(shape = MaterialTheme.shapes.medium),
    )
}

internal fun Modifier.shimmer(
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
