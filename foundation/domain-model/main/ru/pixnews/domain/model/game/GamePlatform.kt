/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

public sealed class GamePlatform(
    public open val name: String,
) {
    public object Windows : GamePlatform("Windows")
    public object Macos : GamePlatform("MacOS")
    public object Linux : GamePlatform("Linux")
    public object PlayStation4 : GamePlatform("PlayStation 4")
    public object PlayStation5 : GamePlatform("PlayStation 5")
    public object PsVita : GamePlatform("PlayStation Vita")
    public object XboxOne : GamePlatform("Xbox One")
    public object XboxSeriesXs : GamePlatform("Xbox Series X|S")
    public object NintendoSwitch : GamePlatform("Nintendo Switch")
    public object Nintendo3Ds : GamePlatform("Nintendo 3DS")
    public object Ios : GamePlatform("IOS")
    public object Android : GamePlatform("Android")
    public data class Other(override val name: String) : GamePlatform(name)
}
