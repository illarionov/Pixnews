/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameId.Companion.asIgdbGameId
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Game

internal object IgdbGameIdConverter : IgdbGameFieldConverter<GameId> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(from.id)

    override fun convert(game: Game): GameId = requireFieldInitialized("id", game.id).asIgdbGameId()
}
