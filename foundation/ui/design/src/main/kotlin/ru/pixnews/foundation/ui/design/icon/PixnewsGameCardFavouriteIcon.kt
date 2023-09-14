/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.pixnews.foundation.ui.assets.icons.content.ContentIcons.Bookmark
import ru.pixnews.foundation.ui.design.R

@Composable
public fun PixnewsGameCardFavouriteIcon(
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier,
        contentDescription = if (isFavourite) {
            stringResource(R.string.in_favorites_content_description)
        } else {
            null
        },
        tint = if (isFavourite) MaterialTheme.colorScheme.tertiary else LocalContentColor.current,
        imageVector = if (isFavourite) Bookmark.Filled else Bookmark.Unfilled,
    )
}
