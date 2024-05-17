/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.foundation.ui.assets.icons.util.IconsPreview

public object PlatformIcons {
    public val android: ImageVector
        get() = AndroidPlatform
    public val ios: ImageVector
        get() = IosPlatform
    public val linux: ImageVector
        get() = LinuxPlatform
    public val macos: ImageVector
        get() = MacosPlatform
    public val nintendoSwitch: ImageVector
        get() = NintendoSwitchPlatform
    public val playstation: ImageVector
        get() = PlaystationPlatform
    public val web: ImageVector
        get() = WebPlatform
    public val windows: ImageVector
        get() = WindowsPlatform
    public val xbox: ImageVector
        get() = XboxPlatform
}

@Preview(showBackground = true)
@Composable
private fun PlatformIconsPreview() = IconsPreview(
    icons = listOf(
        PlatformIcons.android,
        PlatformIcons.ios,
        PlatformIcons.linux,
        PlatformIcons.macos,
        PlatformIcons.nintendoSwitch,
        PlatformIcons.playstation,
        PlatformIcons.web,
        PlatformIcons.windows,
        PlatformIcons.xbox,
    ),
)
