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
