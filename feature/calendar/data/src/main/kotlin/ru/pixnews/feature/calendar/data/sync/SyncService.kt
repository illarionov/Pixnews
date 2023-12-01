/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.sync

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.RootCoroutineScope
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.library.coroutines.newChildSupervisorScope
import javax.inject.Inject

@Suppress("UnusedPrivateProperty")
public class SyncService(
    private val database: PixnewsDatabase,
    private val parentScope: CoroutineScope,
    private val databaseDispatcher: CoroutineDispatcher,
    logger: Logger,
) {
    // TODO: remove scope?
    private val log = logger.withTag(TAG)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log.e(throwable) { "Unhandled coroutine exception in $TAG" }
    }
    private val scope: CoroutineScope = parentScope.newChildSupervisorScope(
        databaseDispatcher + exceptionHandler + CoroutineName("$TAG scope"),
    )

    @Inject
    public constructor(
        database: PixnewsDatabase,
        rootScope: RootCoroutineScope,
        databaseDispatcher: IoCoroutineDispatcherProvider,
        logger: Logger,
    ) : this(
        database = database,
        parentScope = rootScope.newChildSupervisorScope(databaseDispatcher.get()),
        databaseDispatcher = databaseDispatcher.get(),
        logger = logger,
    )

    public suspend fun syncGames(
        fullRefresh: Boolean,
        startDate: Instant,
        minimumRequiredFields: Set<GameField>,
        earlierThanGameId: GameId?,
    ): SyncGamesResult {
        log.i { "syncGames($fullRefresh, $startDate, $minimumRequiredFields, $earlierThanGameId)" }

        val dao = database.gameDao()
        val games = dao.getAll()

        // TODO
        return SyncGamesResult(
            lastGameId = earlierThanGameId,
            hasMorePagesToLoad = false,
        )
    }

    /**
     * Returns true if the data in the database is outdated and requires a complete refresh
     */
    @Suppress("FunctionOnlyReturningConstant")
    public suspend fun isDataStale(): Boolean {
        // TODO:
        return true
    }

    public data class SyncGamesResult(
        val lastGameId: GameId?,
        val hasMorePagesToLoad: Boolean = false,
    )

    private companion object {
        private const val TAG = "SyncService"
    }
}
