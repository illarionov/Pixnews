/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.sync

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "igdbSyncStatus",
)
public data class IgdbSyncStatusEntity(
    @PrimaryKey
    val key: String,
    val value: String,
)
