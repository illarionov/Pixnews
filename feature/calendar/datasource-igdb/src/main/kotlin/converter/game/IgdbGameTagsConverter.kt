/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.Theme

internal object IgdbGameTagsConverter : IgdbGameFieldConverter<ImmutableSet<GameTag>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.themes.name,
    )

    override fun convert(game: Game): ImmutableSet<GameTag> = game.themes.map(
        ::convertIgdbThemeToGameTag,
    ).toImmutableSet()

    internal fun convertIgdbThemeToGameTag(igdbTheme: Theme): GameTag = GameTag(
        requireFieldInitialized("themes.name", igdbTheme.name),
    )
}
