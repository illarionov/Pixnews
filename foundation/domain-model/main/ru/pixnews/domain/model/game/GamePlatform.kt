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
package ru.pixnews.domain.model.game

public sealed class GamePlatform(
    public open val name: String,
) {
    public object Windows : GamePlatform("Windows")
    public object Macos : GamePlatform("MacOS")
    public object Linux : GamePlatform("Linux")
    public object Ps4 : GamePlatform("PlayStation 4")
    public object Ps5 : GamePlatform("PlayStation 5")
    public object PsVita : GamePlatform("PlayStation Vita")
    public object XboxOne : GamePlatform("Xbox One")
    public object XboxSeriesSx : GamePlatform("Xbox Series S|X")
    public object NintendoSwitch : GamePlatform("Nintendo Switch")
    public object Nintendo3Ds : GamePlatform("Nintendo 3DS")
    public object Ios : GamePlatform("IOS")
    public object Android : GamePlatform("Android")
    public data class Other(override val name: String) : GamePlatform(name)
}
