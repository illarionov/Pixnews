/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
