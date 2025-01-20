/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.Game
import ru.pixnews.domain.model.company.Company
import ru.pixnews.feature.calendar.datasource.igdb.converter.company.IgdbCompanyConverter

internal object IgdbGamePublisherConverter : IgdbGameFieldConverter<Company?> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> =
        listOf(from.involved_companies.publisher) +
                IgdbCompanyConverter.getRequiredFields(from.involved_companies.company)

    override fun convert(game: Game): Company? = game.involved_companies
        .firstOrNull { it.publisher }
        ?.company
        ?.let(IgdbCompanyConverter::convert)
}
