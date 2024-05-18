/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.externallink

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pixnews.foundation.ui.assets.icons.util.IconsPreview

public object ExternalLinkIcons {
    public val discord: ImageVector
        get() = DiscordLink
    public val dtf: ImageVector
        get() = DtfLink
    public val facebook: ImageVector
        get() = FacebookLink
    public val igdb: ImageVector
        get() = IgdbLink
    public val instagram: ImageVector
        get() = InstagramLink
    public val rawg: ImageVector
        get() = RawgLink
    public val reddit: ImageVector
        get() = RedditLink
    public val twitch: ImageVector
        get() = TwitchLink
    public val twitter: ImageVector
        get() = TwitterLink
    public val vk: ImageVector
        get() = VkLink
    public val web: ImageVector
        get() = WebLink
    public val youtube: ImageVector
        get() = YoutubeLink
}

@Preview(showBackground = true)
@Composable
private fun ExternalLinkIconsPreview() = IconsPreview(
    icons = listOf(
        ExternalLinkIcons.web,
        ExternalLinkIcons.instagram,
        ExternalLinkIcons.discord,
        ExternalLinkIcons.youtube,
        ExternalLinkIcons.twitch,
        ExternalLinkIcons.facebook,
        ExternalLinkIcons.twitter,
        ExternalLinkIcons.vk,
        ExternalLinkIcons.reddit,
        ExternalLinkIcons.igdb,
        ExternalLinkIcons.rawg,
        ExternalLinkIcons.dtf,
    ),
    iconSize = 64.dp,
)
