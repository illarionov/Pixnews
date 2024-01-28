/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.pixnews.foundation.database.entity.sync.IgdbSyncStatusEntity

@Dao
public interface IgdbSyncStatusDao {
    @Query("SELECT `value` FROM `igdbSyncStatus` WHERE `key` = :key LIMIT 1")
    public suspend fun get(key: String): String?

    @Upsert
    public suspend fun set(key: IgdbSyncStatusEntity)
}
