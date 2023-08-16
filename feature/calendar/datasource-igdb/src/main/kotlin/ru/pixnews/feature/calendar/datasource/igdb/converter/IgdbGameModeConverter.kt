/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.igdbclient.model.GameMode as IgdbGameMode

internal fun Collection<IgdbGameMode>.toGameModes(): ImmutableSet<GameMode> = this.asSequence()
    .map(IgdbGameMode::toGameMode)
    .toImmutableSet()

@Suppress("MagicNumber")
internal fun IgdbGameMode.toGameMode(): GameMode {
    val id = requireFieldInitialized("game_modes.id", this.id)
    val slug = requireFieldInitialized("game_modes.slug", this.slug)
    val name = requireFieldInitialized("game_modes.name", this.name)

    return when (id) {
        1L -> GameMode.SinglePlayer
        2L -> GameMode.Multiplayer
        3L -> GameMode.CoOperative
        4L -> GameMode.SplitScreen
        5L -> GameMode.Mmo
        6L -> GameMode.BattleRoyale
        else -> findGameModeBySlug(slug) ?: GameMode.Other(name)
    }
}

private fun findGameModeBySlug(slug: String): GameMode? = when (slug) {
    "single-player" -> GameMode.SinglePlayer
    "multiplayer" -> GameMode.Multiplayer
    "co-operative" -> GameMode.CoOperative
    "split-screen" -> GameMode.SplitScreen
    "massively-multiplayer-online-mmo" -> GameMode.Mmo
    "battle-royale" -> GameMode.BattleRoyale
    else -> null
}
