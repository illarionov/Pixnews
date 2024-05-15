/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.pixnews.foundation.database.entity.mode.GameModeNameEntity
import ru.pixnews.foundation.database.model.LanguageCodeWrapper

@Dao
public abstract class GameModeNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract suspend fun insertGameModeName(gameModeName: GameModeNameEntity): Long

    @Query(
        "UPDATE OR IGNORE `gameModeName` " +
                "SET name = :name " +
                "WHERE gameModeId = :gameModeId " +
                "AND languageCode = :languageCode",
    )
    public abstract suspend fun updateGameModeName(
        gameModeId: Long,
        languageCode: LanguageCodeWrapper,
        name: String,
    )

    @Query("SELECT * FROM `gameModeName` WHERE `id` = :id")
    public abstract suspend fun getById(id: Long): GameModeNameEntity?

    @Query("SELECT * FROM `gameModeName` WHERE `id` = :id")
    public abstract fun getByIdTestBlocking(id: Long): GameModeNameEntity?

    @Query(
        "SELECT `gameModeName`.* " +
                "FROM `gameModeName` " +
                "INNER JOIN `gameMode` ON `gameModeName`.gameModeId = `gameMode`.id " +
                "WHERE `gameMode`.`slug` = :slug",
    )
    public abstract suspend fun getBySlug(slug: String): List<GameModeNameEntity>

    @Query(
        "SELECT gameModeName.* " +
                "FROM gameModeName " +
                "INNER JOIN gameMode ON gameModeName.gameModeId = gameMode.id " +
                "WHERE gameMode.slug = :slug AND gameModeName.languageCode = :language " +
                "LIMIT 1",
    )
    public abstract suspend fun getBySlugAndLanguage(
        slug: String,
        language: LanguageCodeWrapper,
    ): GameModeNameEntity?

    @Query(
        "SELECT * " +
                "FROM gameModeName " +
                "WHERE gameModeId = :gameModeId AND languageCode = :language",
    )
    public abstract suspend fun getByGameIdAndLanguage(
        gameModeId: Long,
        language: LanguageCodeWrapper,
    ): GameModeNameEntity?

    @Transaction
    public open suspend fun upsert(
        gameModeName: GameModeNameEntity,
    ): Long {
        val old = getByGameIdAndLanguage(gameModeName.gameModeId, gameModeName.languageCode)
        return if (old != null) {
            updateGameModeName(
                gameModeId = gameModeName.gameModeId,
                languageCode = gameModeName.languageCode,
                name = gameModeName.name,
            )
            old.id
        } else {
            insertGameModeName(gameModeName)
        }
    }
}
