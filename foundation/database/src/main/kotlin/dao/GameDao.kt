/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.pixnews.foundation.database.entity.game.GameEntity

@Dao
public interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertAll(vararg games: GameEntity)

    @Update
    public suspend fun update(game: GameEntity): Int

    @Delete
    public suspend fun delete(game: GameEntity)

    @Query("SELECT * FROM game")
    public suspend fun getAll(): List<GameEntity>
}
