/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.domain.model.game.GameMode.Other
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameModeId
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.GameMode as IgdbGameMode

internal object IgdbGameGameModeConverter : IgdbGameFieldConverter<ImmutableSet<Ref<GameMode>>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.game_modes.id,
        from.game_modes.name,
        from.game_modes.slug,
    )

    override fun convert(game: Game): ImmutableSet<Ref<GameMode>> = game.game_modes
        .map(::convertToGameModeRef)
        .toImmutableSet()

    private fun convertToGameModeRef(igdbMode: IgdbGameMode): Ref<GameMode> {
        val byId = findGameModeById(igdbMode.id)
        if (byId != null) {
            return FullObject(byId)
        }

        return if (igdbMode.name.isNotEmpty() || igdbMode.slug.isNotEmpty()) {
            FullObject(igdbMode.toGameMode())
        } else {
            Ref.Id(IgdbGameModeId(igdbMode.id))
        }
    }

    fun IgdbGameMode.toGameMode(): GameMode {
        return findGameModeBySlug(requireFieldInitialized("game_modes.slug", slug))
            ?: Other(requireFieldInitialized("game_modes.name", name))
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
}
