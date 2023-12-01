/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.pixnews.foundation.database.entity.mode.GameModeEntity

@Dao
public abstract class GameModeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract suspend fun insertSilent(gameMode: GameModeEntity): Long

    @Query("SELECT * FROM `gameMode` WHERE `id` = :id")
    public abstract suspend fun getById(id: Long): GameModeEntity?

    @Query("SELECT * FROM `gameMode` WHERE `slug` = :slug")
    public abstract suspend fun getBySlug(slug: String): GameModeEntity?

    @Query("SELECT `id` FROM `gameMode` WHERE `slug` = :slug")
    public abstract suspend fun getIdBySlug(slug: String): Long?

    public suspend fun insertOrGetId(
        gameMode: GameModeEntity,
    ): Long {
        val id = insertSilent(gameMode)
        return if (id == -1L) {
            getIdBySlug(gameMode.slug) ?: -1
        } else {
            id
        }
    }
}
