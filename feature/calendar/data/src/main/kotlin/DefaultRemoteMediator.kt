/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH
import androidx.paging.RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH
import co.touchlab.kermit.Logger
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.repository.upcoming.IgdbPagingSourceKey
import ru.pixnews.feature.calendar.data.sync.SyncService

@OptIn(ExperimentalPagingApi::class)
internal class DefaultRemoteMediator(
    private val startDate: Instant,
    private val requiredFields: Set<GameField>,
    private val syncService: SyncService,
    logger: Logger = Logger,
) : RemoteMediator<IgdbPagingSourceKey, Game>() {
    private val logger = logger.withTag("DefaultRemoteMediator")

    override suspend fun initialize(): InitializeAction {
        return if (syncService.isDataStale()) {
            LAUNCH_INITIAL_REFRESH
        } else {
            SKIP_INITIAL_REFRESH
        }
    }

    // TODO: tests
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<IgdbPagingSourceKey, Game>,
    ): MediatorResult {
        logger.d { "load() with loadType: $loadType" }
        if (loadType == PREPEND) {
            logger.d { "PREPEND: nothing to prepend (we refresh always from first page)" }
            return MediatorResult.Success(endOfPaginationReached = true)
        }
        if (loadType == APPEND && state.lastItemOrNull() == null) {
            logger.d { "APPEND: Last item is null. No more items after initial REFRESH, nothing to load" }
            return MediatorResult.Success(endOfPaginationReached = true)
        }

        val loadKey = if (loadType == APPEND) {
            state.lastItemOrNull()!!.id
        } else {
            null
        }
        return try {
            syncService.syncGameModes(false, true)

            val result = syncService.syncGames(
                fullRefresh = loadType == REFRESH,
                startDate = startDate,
                minimumRequiredFields = requiredFields,
                earlierThanGameId = loadKey,
            )
            MediatorResult.Success(endOfPaginationReached = !result.hasMorePagesToLoad)
        } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
