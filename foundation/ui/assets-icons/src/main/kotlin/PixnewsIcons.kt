/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
