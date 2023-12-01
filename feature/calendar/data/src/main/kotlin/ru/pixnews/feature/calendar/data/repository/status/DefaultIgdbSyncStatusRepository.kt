/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.repository.status

import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import kotlinx.datetime.Instant
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.database.dao.IgdbSyncStatusDao
import ru.pixnews.foundation.database.entity.sync.IgdbSyncStatusEntity
import ru.pixnews.foundation.di.base.scopes.AppScope
import javax.inject.Inject

@ContributesBinding(AppScope::class, boundType = IgdbSyncStatusRepository::class)
@Reusable
public class DefaultIgdbSyncStatusRepository(
    private val dao: IgdbSyncStatusDao,
) : IgdbSyncStatusRepository {
    @Inject
    public constructor(
        database: PixnewsDatabase,
    ) : this(dao = database.igdbSyncStatusDao())

    override suspend fun getInstantKey(key: String): Instant {
        val epoch = dao.get(key)?.toLongOrNull() ?: 0
        return Instant.fromEpochSeconds(epoch)
    }

    override suspend fun setInstantKey(key: String, value: Instant) {
        dao.set(IgdbSyncStatusEntity(key, value.epochSeconds.toString()))
    }
}
