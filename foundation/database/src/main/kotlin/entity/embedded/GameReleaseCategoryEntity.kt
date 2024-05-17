/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.embedded

import androidx.room.Entity
import ru.pixnews.domain.model.game.GameReleaseCategory
import ru.pixnews.domain.model.game.GameReleaseCategory.Bundle
import ru.pixnews.domain.model.game.GameReleaseCategory.DlcAddon
import ru.pixnews.domain.model.game.GameReleaseCategory.ExpandedGame
import ru.pixnews.domain.model.game.GameReleaseCategory.Expansion
import ru.pixnews.domain.model.game.GameReleaseCategory.Fork
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseCategory.Mod
import ru.pixnews.domain.model.game.GameReleaseCategory.Other
import ru.pixnews.domain.model.game.GameReleaseCategory.Pack
import ru.pixnews.domain.model.game.GameReleaseCategory.Port
import ru.pixnews.domain.model.game.GameReleaseCategory.Remake
import ru.pixnews.domain.model.game.GameReleaseCategory.Remaster
import ru.pixnews.domain.model.game.GameReleaseCategory.Season
import ru.pixnews.domain.model.game.GameReleaseCategory.StandaloneExpansion
import ru.pixnews.domain.model.game.GameReleaseCategory.Update
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.BUNDLE
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.DLC_ADDON
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.EPISODE
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.EXPANDED_GAME
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.EXPANSION
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.FORK
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.MAIN_GAME
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.MOD
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.OTHER
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.PACK
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.PORT
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.REMAKE
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.REMASTER
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.SEASON
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.STANDALONE_EXPANSION
import ru.pixnews.foundation.database.entity.embedded.GameReleaseCategoryEntity.Type.UPDATE
import ru.pixnews.foundation.database.model.PixnewsDatabaseGameId
import kotlin.LazyThreadSafetyMode.PUBLICATION

@Entity
public data class GameReleaseCategoryEntity(
    val type: Type,
    val parentGameId: Long? = null,
    val description: String = "",
) {
    val category: GameReleaseCategory by lazy(PUBLICATION) {
        when (type) {
            MAIN_GAME -> MainGame
            DLC_ADDON -> DlcAddon(parentGameIdAsGameId)
            EXPANSION -> Expansion(parentGameIdAsGameId)
            BUNDLE -> Bundle(parentGameIdAsGameId)
            STANDALONE_EXPANSION -> StandaloneExpansion(parentGameIdAsGameId)
            MOD -> Mod(parentGameIdAsGameId)
            EPISODE -> Bundle(parentGameIdAsGameId)
            SEASON -> Season(parentGameIdAsGameId)
            REMAKE -> Remake(parentGameIdAsGameId)
            REMASTER -> Remaster(parentGameIdAsGameId)
            EXPANDED_GAME -> ExpandedGame(parentGameIdAsGameId)
            PORT -> Port(parentGameIdAsGameId)
            FORK -> Fork(parentGameIdAsGameId)
            PACK -> Pack(parentGameIdAsGameId)
            UPDATE -> Update(parentGameIdAsGameId)
            OTHER -> Other(description = description, parentGame = parentGameIdAsGameId)
        }
    }

    private val parentGameIdAsGameId: GameId?
        get() = parentGameId?.let(::PixnewsDatabaseGameId)

    @Suppress("MagicNumber")
    public enum class Type(internal val databaseId: Int) {
        MAIN_GAME(1),
        DLC_ADDON(2),
        EXPANSION(3),
        BUNDLE(4),
        STANDALONE_EXPANSION(5),
        MOD(6),
        EPISODE(7),
        SEASON(8),
        REMAKE(9),
        REMASTER(10),
        EXPANDED_GAME(11),
        PORT(12),
        FORK(13),
        PACK(14),
        UPDATE(15),
        OTHER(16),
    }
}
