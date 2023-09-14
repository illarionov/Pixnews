/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

import ru.pixnews.domain.model.id.GameModeId
import ru.pixnews.domain.model.util.HasId

public sealed class GameMode(
    public open val name: String,
    public override val id: GameModeId = GameModeId(name),
) : HasId<GameModeId> {
    public data object SinglePlayer : GameMode("Single Player")
    public data object Multiplayer : GameMode("Multiplayer")
    public data object SplitScreen : GameMode("Split Screen")
    public data object CoOperative : GameMode("Co-operative")
    public data object BattleRoyale : GameMode("Battle royale")
    public data object Mmo : GameMode("MMO")
    public data class Other(
        override val name: String,
        public override val id: GameModeId = GameModeId(name),
    ) : GameMode(name)
}
