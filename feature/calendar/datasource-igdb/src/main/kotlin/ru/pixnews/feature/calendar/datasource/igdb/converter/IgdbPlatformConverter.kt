/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.id.GamePlatformId
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGamePlatformId
import ru.pixnews.igdbclient.model.Platform

internal fun Platform.toGamePlatformRef(): Ref<GamePlatform, GamePlatformId> {
    val byId = findGamePlatformById(id)
    if (byId != null) {
        return FullObject(byId)
    }

    return if (slug.isNotEmpty() || name.isNotEmpty()) {
        val bySlug = findGamePlatformBySlug(slug)
            ?: GamePlatform.Other(requireFieldInitialized("platform.name", name))
        FullObject(bySlug)
    } else {
        Ref.Id(IgdbGamePlatformId(this.id))
    }
}

@Suppress("MagicNumber", "CyclomaticComplexMethod")
private fun findGamePlatformById(igdbId: Long): GamePlatform? = when (igdbId) {
    0L -> errorFieldNotRequested("platform.id")
    6L -> GamePlatform.Windows
    14L -> GamePlatform.Macos
    3L -> GamePlatform.Linux
    48L -> GamePlatform.PlayStation4
    167L -> GamePlatform.PlayStation5
    46L -> GamePlatform.PsVita
    49L -> GamePlatform.XboxOne
    169L -> GamePlatform.XboxSeriesXs
    130L -> GamePlatform.NintendoSwitch
    37L -> GamePlatform.Nintendo3Ds
    39L -> GamePlatform.Ios
    34L -> GamePlatform.Android
    else -> null
}

@Suppress("CyclomaticComplexMethod")
private fun findGamePlatformBySlug(slug: String): GamePlatform? = when (slug) {
    "" -> errorFieldNotRequested("platform.slug")
    "win" -> GamePlatform.Windows
    "mac" -> GamePlatform.Macos
    "linux" -> GamePlatform.Linux
    "ps4--1" -> GamePlatform.PlayStation4
    "ps5" -> GamePlatform.PlayStation5
    "psvita" -> GamePlatform.PsVita
    "xboxone" -> GamePlatform.XboxOne
    "series-x" -> GamePlatform.XboxSeriesXs
    "switch" -> GamePlatform.NintendoSwitch
    "3ds" -> GamePlatform.Nintendo3Ds
    "ios" -> GamePlatform.Ios
    "android" -> GamePlatform.Android
    else -> null
}
