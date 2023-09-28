/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.game.GameSeriesSummary
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameSeriesSummaryId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Collection
import ru.pixnews.igdbclient.model.Game

internal object IgdbGameSeriesConverter : IgdbGameFieldConverter<Ref<GameSeriesSummary>?> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.collection.id,
        from.collection.name,
        from.collection.games.id,
    )

    override fun convert(game: Game): Ref<GameSeriesSummary>? = game.collection?.run {
        when {
            name.isNotEmpty() -> Ref.FullObject(convert(this))
            id != 0L -> Ref.Id(IgdbGameSeriesSummaryId(id))
            else -> errorFieldNotRequested("collection.id")
        }
    }

    fun convert(igdbObject: Collection): GameSeriesSummary = with(igdbObject) {
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
}
