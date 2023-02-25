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
package ru.pixnews.foundation.ui.assets.icons.social

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.foundation.ui.assets.icons.util.IconsPreview

public object SocialIcons {
    public val comments: ImageVector
        get() = CommentsUnfilled
    public val reply: ImageVector
        get() = ReplyRoundedFilled400w0g24dp
    public val repost: ImageVector
        get() = RepostsRoundedFilled400w0g24dp
    public val rate: ImageVector
        get() = ThumbsUpDownRoundedFilled400w0g24dpx
    public val google: ImageVector
        get() = GoogleSocialIcon
}

@Preview(showBackground = true)
@Composable
private fun SocialIconsPreview() = IconsPreview(
    icons = listOf(
        SocialIcons.google,
        SocialIcons.rate,
        SocialIcons.repost,
        SocialIcons.comments,
        SocialIcons.reply,
    ),
)
