/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.repository.status

import kotlinx.datetime.Instant

public const val IGDB_GAME_MODE_MAX_UPDATED_AT_KEY: String = "igdb_game_mode_max_updated_at"
public const val IGDB_GAME_MODE_LAST_SYNC_KEY: String = "igdb_game_mode_last_sync"

public interface IgdbSyncStatusRepository {
    public suspend fun getInstantKey(key: String): Instant
    public suspend fun setInstantKey(key: String, value: Instant)
}
