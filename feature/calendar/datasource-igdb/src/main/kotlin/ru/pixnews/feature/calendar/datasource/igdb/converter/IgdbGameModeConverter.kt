/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameModeId
import ru.pixnews.igdbclient.model.GameMode as IgdbGameMode

internal fun Collection<IgdbGameMode>.toGameModes(): ImmutableSet<Ref<GameMode>> = this.asSequence()
    .map(IgdbGameMode::toGameModeRef)
    .toImmutableSet()

internal fun IgdbGameMode.toGameModeRef(): Ref<GameMode> {
    val byId = findGameModeById(id)
    if (byId != null) {
        return FullObject(byId)
    }

    return if (name.isNotEmpty() || slug.isNotEmpty()) {
        val bySlug = findGameModeBySlug(slug)
            ?: GameMode.Other(requireFieldInitialized("game_modes.name", this.name))
        FullObject(bySlug)
    } else {
        Ref.Id(IgdbGameModeId(this.id))
    }
}

@Suppress("MagicNumber")
private fun findGameModeById(igdbId: Long): GameMode? = when (igdbId) {
    0L -> errorFieldNotRequested("game_modes.id")
    1L -> GameMode.SinglePlayer
    2L -> GameMode.Multiplayer
    3L -> GameMode.CoOperative
    4L -> GameMode.SplitScreen
    5L -> GameMode.Mmo
    6L -> GameMode.BattleRoyale
    else -> null
}

private fun findGameModeBySlug(slug: String): GameMode? = when (slug) {
    "" -> errorFieldNotRequested("game_modes.slug")
    "single-player" -> GameMode.SinglePlayer
    "multiplayer" -> GameMode.Multiplayer
    "co-operative" -> GameMode.CoOperative
    "split-screen" -> GameMode.SplitScreen
    "massively-multiplayer-online-mmo" -> GameMode.Mmo
    "battle-royale" -> GameMode.BattleRoyale
    else -> null
}
