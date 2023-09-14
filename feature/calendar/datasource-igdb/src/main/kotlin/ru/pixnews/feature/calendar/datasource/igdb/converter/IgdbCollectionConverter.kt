/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.game.GameSeriesSummary
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameSeriesSummaryId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.Collection as IgdbCollection

internal fun IgdbCollection.toGamesSeriesSummaryRef(): Ref<GameSeriesSummary> =
    when {
        name.isNotEmpty() -> Ref.FullObject(this.toGamesSeriesSummary())
        id != 0L -> Ref.Id(IgdbGameSeriesSummaryId(id))
        else -> errorFieldNotRequested("collection.id")
    }

private fun IgdbCollection.toGamesSeriesSummary(): GameSeriesSummary {
    val id = requireFieldInitialized("collection.id", id)
    val name = requireFieldInitialized("collection.name", name)

    return GameSeriesSummary(
        id = IgdbGameSeriesSummaryId(id),
        name = Localized(name, LanguageCode.ENGLISH),
        totalGamesCount = games.size.toUInt().takeIf { it != 0U },
        games = games.asSequence()
            .map(Game::toGameRef)
            .toImmutableList(),
        attribution = persistentListOf(igdbDataSource),
    )
}
