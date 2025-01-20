/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.Game
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.PlayerPerspective
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbPlayerPerspectiveId
import at.released.igdbclient.model.PlayerPerspective as IgdbPlayerPerspective

internal object IgdbGamePlayerPerspectivesConverter : IgdbGameFieldConverter<ImmutableSet<Ref<PlayerPerspective>>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.player_perspectives.id,
        from.player_perspectives.slug,
        from.player_perspectives.name,
    )

    override fun convert(game: Game): ImmutableSet<Ref<PlayerPerspective>> = game.player_perspectives
        .map(::convertToPlayerPerspectiveRef)
        .toImmutableSet()

    internal fun convertToPlayerPerspectiveRef(igdbPlayerPerspective: IgdbPlayerPerspective): Ref<PlayerPerspective> {
        if (igdbPlayerPerspective.name.isNotEmpty() || igdbPlayerPerspective.slug.isNotEmpty()) {
            return FullObject(igdbPlayerPerspective.toPlayerPerspective())
        }
        val byId = findPlayerPerspectiveById(igdbPlayerPerspective.id)
        return if (byId != null) {
            FullObject(byId)
        } else {
            Ref.Id(IgdbPlayerPerspectiveId(igdbPlayerPerspective.id))
        }
    }

    private fun IgdbPlayerPerspective.toPlayerPerspective(): PlayerPerspective {
        val byId = findPlayerPerspectiveById(id)
        if (byId != null) {
            return byId
        }
        return findPlayerPerspectiveBySlug(slug)
            ?: PlayerPerspective.Other(requireFieldInitialized("player_perspectives.name", name))
    }

    @Suppress("MagicNumber")
    private fun findPlayerPerspectiveById(igdbId: Long): PlayerPerspective? = when (igdbId) {
        0L -> errorFieldNotRequested("player_perspectives.id")
        1L -> PlayerPerspective.FirstPerson
        2L -> PlayerPerspective.ThirdPerson
        3L -> PlayerPerspective.Isometric
        4L -> PlayerPerspective.SideView
        5L -> PlayerPerspective.Text
        6L -> PlayerPerspective.Auditory
        7L -> PlayerPerspective.Vr
        else -> null
    }

    private fun findPlayerPerspectiveBySlug(slug: String): PlayerPerspective? = when (slug) {
        "first-person" -> PlayerPerspective.FirstPerson
        "third-person" -> PlayerPerspective.ThirdPerson
        "bird-view-slash-isometric" -> PlayerPerspective.Isometric
        "side-view" -> PlayerPerspective.SideView
        "text" -> PlayerPerspective.Text
        "auditory" -> PlayerPerspective.Auditory
        "virtual-reality" -> PlayerPerspective.Vr
        else -> null
    }
}
