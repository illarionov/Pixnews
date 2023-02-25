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
