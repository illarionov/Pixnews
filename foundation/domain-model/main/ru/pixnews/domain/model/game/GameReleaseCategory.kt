/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game

import ru.pixnews.domain.model.id.GameId

public sealed interface GameReleaseCategory {
    public val parentGame: GameId?
        get() = null

    public data object MainGame : GameReleaseCategory
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
