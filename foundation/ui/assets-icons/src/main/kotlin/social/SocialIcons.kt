/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
