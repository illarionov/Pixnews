/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.Game
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.library.internationalization.language.LanguageCode.Companion.ENGLISH

internal object IgdbGameNameConverter : IgdbGameFieldConverter<Localized<String>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(from.name)
    override fun convert(game: Game): Localized<String> = Localized(game.name, ENGLISH)
}
