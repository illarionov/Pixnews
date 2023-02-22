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
package ru.pixnews.foundation.ui.design.assets.icons.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.pixnews.foundation.ui.design.assets.icons.util.IconsPreview

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
