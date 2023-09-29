/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Game as IgdbGame

internal object IgdbGameParentGameConverter : IgdbGameFieldConverter<Ref<Game>?> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(from.id)
    override fun convert(game: IgdbGame): Ref<Game>? = game.parent_game?.toGameRef()
}
