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
package ru.pixnews.foundation.ui.design.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.GamePlatform.Android
import ru.pixnews.domain.model.game.GamePlatform.Ios
import ru.pixnews.domain.model.game.GamePlatform.Linux
import ru.pixnews.domain.model.game.GamePlatform.Macos
import ru.pixnews.domain.model.game.GamePlatform.Nintendo3Ds
import ru.pixnews.domain.model.game.GamePlatform.NintendoSwitch
import ru.pixnews.domain.model.game.GamePlatform.Other
import ru.pixnews.domain.model.game.GamePlatform.PlayStation4
import ru.pixnews.domain.model.game.GamePlatform.PlayStation5
import ru.pixnews.domain.model.game.GamePlatform.PsVita
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GamePlatform.XboxOne
import ru.pixnews.domain.model.game.GamePlatform.XboxSeriesXs
import ru.pixnews.foundation.ui.assets.icons.platform.PlatformIcons
import ru.pixnews.foundation.ui.design.R

public fun GamePlatform.getIcon(): ImageVector? {
    return when (this) {
        Android -> PlatformIcons.android
        Ios -> PlatformIcons.ios
        Linux -> PlatformIcons.linux
        Macos -> PlatformIcons.macos
        Nintendo3Ds -> PlatformIcons.nintendoSwitch
        NintendoSwitch -> PlatformIcons.nintendoSwitch
        PlayStation4 -> PlatformIcons.playstation
        PlayStation5 -> PlatformIcons.playstation
        PsVita -> PlatformIcons.playstation
        Windows -> PlatformIcons.windows
        XboxOne -> PlatformIcons.xbox
        XboxSeriesXs -> PlatformIcons.xbox
        is Other -> null
    }
}

internal fun ImmutableSet<GamePlatform>.uniqueIcons(): Set<ImageVector> = this.mapNotNull(GamePlatform::getIcon).toSet()

@Composable
internal fun ImmutableSet<GamePlatform>.contentDescription(): String {
    val platforms = this.map(GamePlatform::name).joinToString()
    return stringResource(R.string.platforms_content_description, platforms)
}
