/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game

import ru.pixnews.domain.model.id.GamePlatformId
import ru.pixnews.domain.model.util.HasId

public sealed class GamePlatform(
    public open val name: String,
    public override val id: GamePlatformId = GamePlatformId(name),
) : HasId<GamePlatformId> {
    public data object Windows : GamePlatform("Windows")
    public data object Macos : GamePlatform("MacOS")
    public data object Linux : GamePlatform("Linux")
    public data object PlayStation4 : GamePlatform("PlayStation 4")
    public data object PlayStation5 : GamePlatform("PlayStation 5")
    public data object PsVita : GamePlatform("PlayStation Vita")
    public data object XboxOne : GamePlatform("Xbox One")
    public data object XboxSeriesXs : GamePlatform("Xbox Series X|S")
    public data object NintendoSwitch : GamePlatform("Nintendo Switch")
    public data object Nintendo3Ds : GamePlatform("Nintendo 3DS")
    public data object Ios : GamePlatform("IOS")
    public data object Android : GamePlatform("Android")
    public data class Other(
        override val name: String,
        override val id: GamePlatformId = GamePlatformId(name),
    ) : GamePlatform(name)
}
