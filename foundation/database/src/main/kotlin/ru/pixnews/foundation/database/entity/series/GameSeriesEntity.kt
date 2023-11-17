/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.series

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.entity.embedded.DataSourceMetadataEntity

@Entity(
    tableName = "gameSeries",
)
public data class GameSeriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val totalGamesCount: Long = 0,
    @Embedded
    val dataSource: DataSourceMetadataEntity,
)
