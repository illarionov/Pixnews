/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.Game
import at.released.igdbclient.model.Genre
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized

internal object IgdbGameGenresConverter : IgdbGameFieldConverter<ImmutableSet<GameGenre>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.genres.name,
    )

    override fun convert(game: Game): ImmutableSet<GameGenre> = game.genres
        .map(::convertGenreToGameGenre)
        .toImmutableSet()

    fun convertGenreToGameGenre(igdbGenre: Genre): GameGenre = GameGenre(
        name = requireFieldInitialized("genres.name", igdbGenre.name),
    )
}
