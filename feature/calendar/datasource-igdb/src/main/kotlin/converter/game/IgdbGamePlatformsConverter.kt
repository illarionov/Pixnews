/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.dsl.field.GameFieldDsl
import at.released.igdbclient.dsl.field.IgdbRequestField
import at.released.igdbclient.model.Game
import at.released.igdbclient.model.Platform
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGamePlatformId

internal object IgdbGamePlatformsConverter : IgdbGameFieldConverter<ImmutableSet<Ref<GamePlatform>>> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = listOf(
        from.platforms.id,
        from.platforms.name,
        from.platforms.slug,
    )

    override fun convert(game: Game): ImmutableSet<Ref<GamePlatform>> = game.platforms.map {
        it.toGamePlatformRef()
    }.toImmutableSet()

    internal fun Platform.toGamePlatformRef(): Ref<GamePlatform> {
        if (slug.isNotEmpty() || name.isNotEmpty()) {
            return FullObject(toGamePlatform())
        }

        val byId = findGamePlatformById(id)
        return if (byId != null) {
            return FullObject(byId)
        } else {
            Ref.Id(IgdbGamePlatformId(id))
        }
    }

    private fun Platform.toGamePlatform(): GamePlatform {
        val byId = findGamePlatformById(id)
        if (byId != null) {
            return byId
        }

        return findGamePlatformBySlug(requireFieldInitialized("platform.slug", slug))
            ?: GamePlatform.Other(requireFieldInitialized("platform.name", name))
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
}
