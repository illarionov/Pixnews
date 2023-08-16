/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.PlayerPerspective
import ru.pixnews.igdbclient.model.PlayerPerspective as IgdbPlayerPerspective

internal fun Collection<IgdbPlayerPerspective>.toPlayerPerspectives(): ImmutableSet<PlayerPerspective> =
    this.asSequence()
        .map(IgdbPlayerPerspective::toPlayerPerspective)
        .toImmutableSet()

@Suppress("MagicNumber")
internal fun IgdbPlayerPerspective.toPlayerPerspective(): PlayerPerspective {
    val id = requireFieldInitialized("player_perspectives.id", id)

    return when (id) {
        1L -> PlayerPerspective.FirstPerson
        2L -> PlayerPerspective.ThirdPerson
        3L -> PlayerPerspective.Isometric
        4L -> PlayerPerspective.SideView
        5L -> PlayerPerspective.Text
        6L -> PlayerPerspective.Auditory
        7L -> PlayerPerspective.Vr
        else -> findPlayerPerspectiveBySlug(slug) ?: PlayerPerspective.Other(name)
    }
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
