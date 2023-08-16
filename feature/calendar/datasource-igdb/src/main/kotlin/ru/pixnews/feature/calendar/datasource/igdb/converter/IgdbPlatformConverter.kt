/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.igdbclient.model.Platform

@Suppress("MagicNumber", "CyclomaticComplexMethod")
internal fun Platform.toGamePlatform(): GamePlatform {
    val id = requireFieldInitialized("platform.id", id)
    val slug = requireFieldInitialized("platform.slug", slug)

    return when (id) {
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
        else -> getGamePlatformBySlug(slug) ?: GamePlatform.Other(this.name)
    }
}

private fun getGamePlatformBySlug(slug: String): GamePlatform? = when (slug) {
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
