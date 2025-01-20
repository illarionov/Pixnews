/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.GameModeFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.dsl.field.field
import at.released.igdbclient.model.Game
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.domain.model.game.GameMode.Other
import ru.pixnews.domain.model.id.GameModeId
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.data.model.GameModeIgdbDto
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.fieldShouldBeRequestedError
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameModeId
import at.released.igdbclient.model.GameMode as IgdbGameMode

internal object IgdbGameGameModeConverter : IgdbGameFieldConverter<ImmutableSet<Ref<GameMode>>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<IgdbGameMode>> = getRequiredFields(
        from.game_modes,
    )

    fun getRequiredFields(
        from: GameModeFieldDsl = IgdbGameMode.field,
    ): List<IgdbRequestField<IgdbGameMode>> = listOf(
        from.id,
        from.name,
        from.slug,
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
            ?: Other(
                requireFieldInitialized("game_modes.name", name),
                GameModeId(slug),
            )
    }

    fun toGameModeIgdbDto(mode: IgdbGameMode): GameModeIgdbDto {
        val updatedAt = requireNotNull(mode.updated_at) {
            fieldShouldBeRequestedError("game_modes.updated_at")
        }
        return GameModeIgdbDto(
            mode = mode.toGameMode(),
            igdbSlug = mode.slug,
            updatedAt = Instant.fromEpochSeconds(updatedAt.epochSecond),
        )
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
