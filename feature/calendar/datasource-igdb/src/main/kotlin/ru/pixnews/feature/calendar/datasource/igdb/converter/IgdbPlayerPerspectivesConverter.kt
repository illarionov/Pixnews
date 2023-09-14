/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.PlayerPerspective
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbPlayerPerspectiveId
import ru.pixnews.igdbclient.model.PlayerPerspective as IgdbPlayerPerspective

internal fun Collection<IgdbPlayerPerspective>.toPlayerPerspectives(): ImmutableSet<Ref<PlayerPerspective>> =
    this.asSequence()
        .map(IgdbPlayerPerspective::toPlayerPerspectiveRef)
        .toImmutableSet()

internal fun IgdbPlayerPerspective.toPlayerPerspectiveRef(): Ref<PlayerPerspective> {
    val byId = findPlayerPerspectiveById(id)
    if (byId != null) {
        return FullObject(byId)
    }

    return if (name.isNotEmpty() || slug.isNotEmpty()) {
        val bySlug = findPlayerPerspectiveBySlug(slug)
            ?: PlayerPerspective.Other(requireFieldInitialized("player_perspectives.name", name))
        FullObject(bySlug)
    } else {
        Ref.Id(IgdbPlayerPerspectiveId(this.id))
    }
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
