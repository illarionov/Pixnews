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

public sealed interface GameReleaseCategory {
    public val parentGame: GameId?
        get() = null

    public object MainGame : GameReleaseCategory
    public data class DlcAddon(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Expansion(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Bundle(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class StandaloneExpansion(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Mod(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Episode(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Season(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Remake(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Remaster(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class ExpandedGame(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Port(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Fork(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Pack(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Update(override val parentGame: GameId? = null) : GameReleaseCategory
    public data class Other(
        val description: String,
        override val parentGame: GameId? = null,
    ) : GameReleaseCategory
}
