/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameSeriesId
import ru.pixnews.domain.model.game.GameSeriesSummary
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.igdbclient.model.Collection as IgdbCollection

internal fun IgdbCollection.toGamesSeriesSummary(): GameSeriesSummary {
    val id = requireFieldInitialized("collection.id", id).toString()
    val name = requireFieldInitialized("collection.name", name)

    return GameSeriesSummary(
        id = GameSeriesId(id),
        name = Localized(name, LanguageCode.ENGLISH),
        totalGamesCount = games.size.toUInt().takeIf { it != 0U },
        games = games.asSequence()
            .map { GameId(it.id.toString()) }
            .toImmutableList(),
        attribution = persistentListOf(igdbDataSource),
    )
}
