/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

public sealed class GameMode(
    public open val name: String,
) {
    public object SinglePlayer : GameMode("Single Player")
    public object Multiplayer : GameMode("Multiplayer")
    public object SplitScreen : GameMode("Split Screen")
    public object CoOperative : GameMode("Co-operative")
    public object BattleRoyale : GameMode("Battle royale")
    public object Mmo : GameMode("MMO")
    public data class Other(override val name: String) : GameMode(name)
}
