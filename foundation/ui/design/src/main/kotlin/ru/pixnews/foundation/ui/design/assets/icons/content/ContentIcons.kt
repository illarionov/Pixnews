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
package ru.pixnews.foundation.ui.design.assets.icons.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.foundation.ui.design.assets.icons.util.IconsPreview

public object ContentIcons {
    public val FilterList: ImageVector
        get() = FilterListRoundedUnfilled400w0g24dp

    public val DragHandle: ImageVector
        get() = DragHandleRounded400w0g24dp

    public val Sort: ImageVector
        get() = SortRoundedUnfilled400w0g24dp

    public val DeleteSweep: ImageVector
        get() = DeleteSweepRoundedUnfilled400w0g24dp

    public val AddLink: ImageVector
        get() = AddLinkRoundedUnfilled400w0g24dp

    public val AddPhoto: ImageVector
        get() = AddPhotoAlternateRoundedUnfilled400w0g24dp

    public val ArrowInsert: ImageVector
        get() = ArrowInsertRoundedUnfilled400w0g24dp

    public val ExpandMore: ImageVector
        get() = ExpandMoreRoundedUnfilled400w0g24dp

    public val History: ImageVector
        get() = HistoryRoundedUnfilled400w0g24dp

    public val RateReview: ImageVector
        get() = RateReviewRoundedUnfilled400w0g24dp

    public val Sync: ImageVector
        get() = SynRoundedUnfilled400w0g24dp

    public object GridView {
        public val Filled: ImageVector
            get() = GridViewRoundedFilled400w0g24dp
        public val Unfilled: ImageVector
            get() = GridViewRoundedUnfilled400w0g24dp
    }

    public object Bookmark {
        public val Filled: ImageVector
            get() = BookmarkRoundedFilled400w0g24dp
        public val Unfilled: ImageVector
            get() = BookmarkRoundedUnfilled400w0g24dp
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentIconsPreview() = IconsPreview(
    listOf(
        ContentIcons.GridView.Filled,
        ContentIcons.GridView.Unfilled,
        ContentIcons.FilterList,
        ContentIcons.Bookmark.Filled,
        ContentIcons.Bookmark.Unfilled,
        ContentIcons.DragHandle,
        ContentIcons.Sort,
        ContentIcons.DeleteSweep,
        ContentIcons.Sync,
        ContentIcons.ExpandMore,
        ContentIcons.History,
        ContentIcons.ArrowInsert,
        ContentIcons.RateReview,
        ContentIcons.AddPhoto,
        ContentIcons.AddLink,
    ),
)
