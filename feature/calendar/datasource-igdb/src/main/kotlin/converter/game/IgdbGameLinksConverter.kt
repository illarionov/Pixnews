/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.Url
import ru.pixnews.feature.calendar.datasource.igdb.converter.toExternalLinkType
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Game

internal object IgdbGameLinksConverter : IgdbGameFieldConverter<ImmutableList<ExternalLink>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = with(from) {
        listOf(
            url,
            websites.category,
            websites.url,
        )
    }

    override fun convert(game: Game): ImmutableList<ExternalLink> = sequence {
        if (game.url.isNotEmpty()) {
            yield(ExternalLink(type = OFFICIAL, url = Url(game.url)))
        }
        game.websites.forEach { website ->
            yield(
                ExternalLink(
                    type = website.category.toExternalLinkType() ?: errorFieldNotRequested("website.category"),
                    url = Url(requireFieldInitialized("website.url", website.url)),
                ),
            )
        }
    }.toImmutableList()
}
