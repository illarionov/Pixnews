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
@file:Suppress("FILE_NAME_MATCH_CLASS")

package ru.pixnews.foundation.ui.assets.icons

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ru.pixnews.foundation.ui.assets.icons.Icon.DrawableResourceIcon
import ru.pixnews.foundation.ui.assets.icons.Icon.ImageVectorIcon
import ru.pixnews.foundation.ui.assets.icons.access.AccessIcons
import ru.pixnews.foundation.ui.assets.icons.action.ActionIcons
import ru.pixnews.foundation.ui.assets.icons.content.ContentIcons
import ru.pixnews.foundation.ui.assets.icons.distribution.DistributionPlatformIcons
import ru.pixnews.foundation.ui.assets.icons.externallink.ExternalLinkIcons
import ru.pixnews.foundation.ui.assets.icons.navigation.NavigationIcons
import ru.pixnews.foundation.ui.assets.icons.platform.PlatformIcons
import ru.pixnews.foundation.ui.assets.icons.social.SocialIcons

public object PixnewsIcons {
    public val action: ActionIcons = ActionIcons
    public val content: ContentIcons = ContentIcons
    public val navigation: NavigationIcons = NavigationIcons
    public val platform: PlatformIcons = PlatformIcons
    public val distributionPlatform: DistributionPlatformIcons = DistributionPlatformIcons
    public val externalLink: ExternalLinkIcons = ExternalLinkIcons
    public val access: AccessIcons = AccessIcons
    public val social: SocialIcons = SocialIcons
}

@Immutable
public sealed class Icon {
    public data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    public data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}

@Composable
public fun Icon.asImageVector(): ImageVector = when (this) {
    is DrawableResourceIcon -> ImageVector.vectorResource(id = id)
    is ImageVectorIcon -> imageVector
}
