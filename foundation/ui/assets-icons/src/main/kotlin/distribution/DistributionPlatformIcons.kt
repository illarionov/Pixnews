/*
 * Copyright 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.distribution

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.foundation.ui.assets.icons.platform.NintendoSwitchPlatform
import ru.pixnews.foundation.ui.assets.icons.platform.PlaystationPlatform
import ru.pixnews.foundation.ui.assets.icons.platform.XboxPlatform
import ru.pixnews.foundation.ui.assets.icons.util.IconsPreview

public object DistributionPlatformIcons {
    public val appStore: ImageVector
        get() = AppStoreDistributionPlatform
    public val battleNet: ImageVector
        get() = BattleNetDistributionPlatform
    public val epicGames: ImageVector
        get() = EpicGamesDistributionPlatform
    public val gog: ImageVector
        get() = GogDistributionPlatform
    public val googlePlay: ImageVector
        get() = GooglePlayDistributionPlatform
    public val humbleBumble: ImageVector
        get() = HumbleBumbleDistributionPlatform
    public val itchIo: ImageVector
        get() = ItchIoDistributionPlatform
    public val microsoftStore: ImageVector
        get() = XboxPlatform
    public val nintendoSwitch: ImageVector
        get() = NintendoSwitchPlatform
    public val origin: ImageVector
        get() = OriginDistributionPlatform
    public val playStation: ImageVector
        get() = PlaystationPlatform
    public val steam: ImageVector
        get() = SteamDistributionPlatform
    public val ubisoft: ImageVector
        get() = UbisoftDistributionPlatform
}

@Preview(showBackground = true)
@Composable
private fun DistributionPlatformIconsPreview() = IconsPreview(
    icons = listOf(
        DistributionPlatformIcons.appStore,
        DistributionPlatformIcons.battleNet,
        DistributionPlatformIcons.epicGames,
        DistributionPlatformIcons.gog,
        DistributionPlatformIcons.googlePlay,
        DistributionPlatformIcons.humbleBumble,
        DistributionPlatformIcons.itchIo,
        DistributionPlatformIcons.microsoftStore,
        DistributionPlatformIcons.nintendoSwitch,
        DistributionPlatformIcons.origin,
        DistributionPlatformIcons.playStation,
        DistributionPlatformIcons.steam,
        DistributionPlatformIcons.ubisoft,
    ),
)
