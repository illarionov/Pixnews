/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.sync

import androidx.room.withTransaction
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.optional.SingleIn
import kotlinx.coroutines.CancellationException
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.pixnews.feature.calendar.data.IgdbDataSource
import ru.pixnews.feature.calendar.data.model.GameModeIgdbDto
import ru.pixnews.feature.calendar.data.repository.status.IGDB_GAME_MODE_LAST_SYNC_KEY
import ru.pixnews.feature.calendar.data.repository.status.IGDB_GAME_MODE_MAX_UPDATED_AT_KEY
import ru.pixnews.feature.calendar.data.repository.status.IgdbSyncStatusRepository
import ru.pixnews.feature.calendar.data.sync.policy.SyncPolicy
import ru.pixnews.feature.calendar.data.sync.policy.SyncRequiredResult
import ru.pixnews.feature.calendar.data.sync.policy.isSyncRequired
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.database.entity.mode.GameModeEntity
import ru.pixnews.foundation.database.entity.mode.GameModeNameEntity
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.library.functional.Result
import ru.pixnews.library.functional.completeFailure
import ru.pixnews.library.functional.completeSuccess
import ru.pixnews.library.functional.network.NetworkRequestFailureException
import ru.pixnews.library.internationalization.language.LanguageCode
import javax.inject.Inject
import kotlin.time.Duration

/**
 * Loads fresh Game Modes from IGDB and updates database
 */
@SingleIn(AppScope::class)
public class IgdbGameModeSyncService(
    private val database: PixnewsDatabase,
    private val syncStatusRepository: IgdbSyncStatusRepository,
    private val igdbDataSource: IgdbDataSource,
    private val syncPolicy: SyncPolicy = DEFAULT_SYNC_POLICY,
    private val clock: Clock = Clock.System,
    logger: Logger = Logger,
) {
    private val logger = logger.withTag("IgdbGameModeSyncService")
    private val gameModeDao = database.gameModeDao()
    private val gameModeNameDao = database.gameModeNameDao()

    @Inject
    public constructor(
        database: PixnewsDatabase,
        syncStatusRepository: IgdbSyncStatusRepository,
        igdbDataSource: IgdbDataSource,
        logger: Logger,
    ) : this(
        database = database,
        syncStatusRepository = syncStatusRepository,
        igdbDataSource = igdbDataSource,
        logger = logger,
        syncPolicy = DEFAULT_SYNC_POLICY,
        clock = Clock.System,
    )

    public suspend fun sync(
        forceSync: Boolean = false,
        forceFullReload: Boolean = false,
    ): Result<Throwable, Unit> = try {
        syncThrowable(forceSync, forceFullReload)
        Unit.completeSuccess()
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (@Suppress("TooGenericExceptionCaught") throwable: Throwable) {
        throwable.completeFailure()
    }

    private suspend fun syncThrowable(
        forceSync: Boolean,
        forceFullReload: Boolean,
    ) {
        logger.i { "Sync GameModes" }
        val lastUpdateMetaInfo = database.withTransaction {
            getLastUpdateMetaInfo()
        }
        val syncPolicyStatus = syncPolicy.isSyncRequired(
            lastSyncTime = lastUpdateMetaInfo.lastSyncTime,
            forceSync = forceSync,
            forceFullReload = forceFullReload,
        )
        if (syncPolicyStatus !is SyncRequiredResult.Required) {
            logger.i { "Sync not required: $syncPolicyStatus" }
            return
        }

        val newGameModes = downloadNewGameModes(
            lastUpdatedAt = lastUpdateMetaInfo.lastMaxUpdatedAt.takeIf { !forceFullReload },
        )
        if (newGameModes.isEmpty()) {
            logger.i { "No new game modes" }
            return
        }
        upsertGameModes(newGameModes, lastUpdateMetaInfo)
    }

    private suspend fun downloadNewGameModes(
        lastUpdatedAt: Instant?,
    ): List<GameModeIgdbDto> {
        val result = igdbDataSource.getGameModes(
            updatedLaterThan = lastUpdatedAt,
            offset = 0,
            limit = 100,
        )

        return result.fold(
            ifLeft = {
                throw NetworkRequestFailureException(it, "Can not download updated game modes")
            },
            ifRight = { it },
        )
    }

    private suspend fun upsertGameModes(
        modes: List<GameModeIgdbDto>,
        lastUpdateMetaInfo: LastUpdateMetaInfo,
    ) = database.withTransaction {
        val newMetaInfo = getLastUpdateMetaInfo()
        if (newMetaInfo.lastSyncTime != lastUpdateMetaInfo.lastSyncTime) {
            logger.i { "Another sync active" }
            return@withTransaction
        }
        for (mode in modes) {
            val gameModeEntity = GameModeEntity(slug = mode.igdbSlug)
            val id = gameModeDao.insertOrGetId(gameModeEntity)
            if (id != -1L) {
                val nameEntity = GameModeNameEntity(
                    gameModeId = id,
                    languageCode = LanguageCodeWrapper(LanguageCode.ENGLISH),
                    name = mode.mode.name,
                )
                val id = gameModeNameDao.upsert(nameEntity)
                if (id == -1L) {
                    logger.i { "Can not insert game name for `$mode`" }
                }
            } else {
                logger.i { "Can not insert game mode with slug `${mode.igdbSlug}`" }
            }
        }

        setLastUpdateMetaInfo(
            LastUpdateMetaInfo(
                lastSyncTime = clock.now(),
                lastMaxUpdatedAt = modes.maxOf { it.updatedAt },
            ),
        )
    }

    private suspend fun getLastUpdateMetaInfo(): LastUpdateMetaInfo {
        val lastUpdatedAt = syncStatusRepository.getInstantKey(IGDB_GAME_MODE_MAX_UPDATED_AT_KEY)
        val lastSyncTime = syncStatusRepository.getInstantKey(IGDB_GAME_MODE_LAST_SYNC_KEY)
        return LastUpdateMetaInfo(lastSyncTime, lastUpdatedAt)
    }

    private suspend fun setLastUpdateMetaInfo(
        info: LastUpdateMetaInfo,
    ) {
        syncStatusRepository.setInstantKey(IGDB_GAME_MODE_MAX_UPDATED_AT_KEY, info.lastMaxUpdatedAt)
        syncStatusRepository.setInstantKey(IGDB_GAME_MODE_LAST_SYNC_KEY, info.lastSyncTime)
    }

    private class LastUpdateMetaInfo(
        val lastSyncTime: Instant,
        val lastMaxUpdatedAt: Instant,
    )

    private companion object {
        val DEFAULT_SYNC_POLICY = SyncPolicy(minPeriod = Duration.ZERO)
    }
}
