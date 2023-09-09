/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.game.GameReleaseCategory
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.igdbclient.model.GameCategoryEnum as IgdbCategory

@Suppress("CyclomaticComplexMethod")
internal fun IgdbCategory.toGameReleaseCategory(parentGameId: GameId?): GameReleaseCategory =
    when (this) {
        IgdbCategory.MAIN_GAME -> GameReleaseCategory.MainGame
        IgdbCategory.DLC_ADDON -> GameReleaseCategory.DlcAddon(parentGameId)
        IgdbCategory.EXPANSION -> GameReleaseCategory.Expansion(parentGameId)
        IgdbCategory.BUNDLE -> GameReleaseCategory.Bundle(parentGameId)
        IgdbCategory.STANDALONE_EXPANSION -> GameReleaseCategory.StandaloneExpansion(parentGameId)
        IgdbCategory.MOD -> GameReleaseCategory.Mod(parentGameId)
        IgdbCategory.EPISODE -> GameReleaseCategory.Episode(parentGameId)
        IgdbCategory.SEASON -> GameReleaseCategory.Season(parentGameId)
        IgdbCategory.REMAKE -> GameReleaseCategory.Remake(parentGameId)
        IgdbCategory.REMASTER -> GameReleaseCategory.Remaster(parentGameId)
        IgdbCategory.EXPANDED_GAME -> GameReleaseCategory.ExpandedGame(parentGameId)
        IgdbCategory.PORT -> GameReleaseCategory.Port(parentGameId)
        IgdbCategory.FORK -> GameReleaseCategory.Fork(parentGameId)
        IgdbCategory.PACK -> GameReleaseCategory.Pack(parentGameId)
        IgdbCategory.UPDATE -> GameReleaseCategory.Update(parentGameId)
    }
