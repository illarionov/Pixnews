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
package ru.pixnews.foundation.ui.design.assets.icons.externallink

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pixnews.foundation.ui.design.assets.icons.util.IconsPreview

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
