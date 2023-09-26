/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import ru.pixnews.domain.model.game.GameReleaseCategory
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameId.Companion.asIgdbGameId
import ru.pixnews.igdbclient.dsl.field.GameFieldDsl
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.GameCategoryEnum

internal object IgdbGameCategoryConverter : IgdbGameFieldConverter<GameReleaseCategory?> {
    override fun getRequiredFields(from: GameFieldDsl): List<IgdbRequestField<*>> = with(from) {
        listOf(
            category,
            parent_game.id,
        )
    }

    override fun convert(game: Game): GameReleaseCategory {
        val parentGameId = game.parent_game?.id?.asIgdbGameId()
        return convertToGameReleaseCategory(game.category, parentGameId)
    }

    @Suppress("CyclomaticComplexMethod")
    private fun convertToGameReleaseCategory(
        category: GameCategoryEnum,
        parentGameId: GameId?,
    ): GameReleaseCategory = when (category) {
        GameCategoryEnum.MAIN_GAME -> GameReleaseCategory.MainGame
        GameCategoryEnum.DLC_ADDON -> GameReleaseCategory.DlcAddon(parentGameId)
        GameCategoryEnum.EXPANSION -> GameReleaseCategory.Expansion(parentGameId)
        GameCategoryEnum.BUNDLE -> GameReleaseCategory.Bundle(parentGameId)
        GameCategoryEnum.STANDALONE_EXPANSION -> GameReleaseCategory.StandaloneExpansion(parentGameId)
        GameCategoryEnum.MOD -> GameReleaseCategory.Mod(parentGameId)
        GameCategoryEnum.EPISODE -> GameReleaseCategory.Episode(parentGameId)
        GameCategoryEnum.SEASON -> GameReleaseCategory.Season(parentGameId)
        GameCategoryEnum.REMAKE -> GameReleaseCategory.Remake(parentGameId)
        GameCategoryEnum.REMASTER -> GameReleaseCategory.Remaster(parentGameId)
        GameCategoryEnum.EXPANDED_GAME -> GameReleaseCategory.ExpandedGame(parentGameId)
        GameCategoryEnum.PORT -> GameReleaseCategory.Port(parentGameId)
        GameCategoryEnum.FORK -> GameReleaseCategory.Fork(parentGameId)
        GameCategoryEnum.PACK -> GameReleaseCategory.Pack(parentGameId)
        GameCategoryEnum.UPDATE -> GameReleaseCategory.Update(parentGameId)
    }
}
