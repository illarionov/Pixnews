/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb

import arrow.core.Either.Companion.catch
import arrow.core.flatMap
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.IgdbDataSource
import ru.pixnews.feature.calendar.data.model.GameModeIgdbDto
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameGameModeConverter
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.igdbFieldConverter
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.toGame
import ru.pixnews.feature.calendar.datasource.igdb.converter.toNetworkResult
import ru.pixnews.foundation.coroutines.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.igdbclient.IgdbClient
import ru.pixnews.igdbclient.IgdbEndpoint
import ru.pixnews.igdbclient.apicalypse.SortOrder.DESC
import ru.pixnews.igdbclient.apicalypse.apicalypseQuery
import ru.pixnews.igdbclient.dsl.field.IgdbRequestField
import ru.pixnews.igdbclient.dsl.field.field
import ru.pixnews.igdbclient.model.GameMode
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.library.functional.network.NetworkResult
import javax.inject.Inject
import ru.pixnews.igdbclient.model.Game as IgdbGame

@ContributesBinding(boundType = IgdbDataSource::class, scope = AppScope::class)
public class DefaultIgdbDataSource(
    private val igdbClient: IgdbClient,
    private val backgroundDispatcher: CoroutineDispatcher,
    logger: Logger,
) : IgdbDataSource {
    private val logger = logger.withTag("DefaultIgdbDataSource")

    @Inject
    public constructor(
        igdbClient: IgdbClient,
        backgroundDispatcher: ComputationCoroutineDispatcherProvider,
        logger: Logger,
    ) : this(igdbClient, backgroundDispatcher.get(), logger)

    override suspend fun fetchUpcomingReleases(
        startDate: Instant,
        requiredFields: Set<GameField>,
        offset: Int,
        limit: Int,
    ): NetworkResult<List<Game>> {
        val igdbFields = requiredFields.toIgdbRequestFields()

        val query = apicalypseQuery {
            fields(fieldList = igdbFields.toTypedArray())
            where("release_dates.date > ${startDate.epochSeconds}")
            offset(offset)
            limit(limit)
            sort("id", DESC)
        }
        logger.d { "REQ IGDB query: '$query'" }

        val igdbGameResult = igdbClient.execute(
            endpoint = IgdbEndpoint.GAME,
            query = query,
        ).toNetworkResult()

        val result: NetworkResult<List<Game>> = igdbGameResult.mapSuccessOnBackground { gameResult ->
            logger.v { "Received games ${gameResult.games.prettyPrintGames()}" }
            gameResult.games.map(IgdbGame::toGame)
        }

        return result
    }

    override suspend fun getGameModes(
        updatedLaterThan: Instant?,
        offset: Int,
        limit: Int,
    ): NetworkResult<List<GameModeIgdbDto>> {
        val converter = IgdbGameGameModeConverter
        val query = apicalypseQuery {
            fields(
                fieldList = (converter.getRequiredFields(GameMode.field) + GameMode.field.updated_at).toTypedArray(),
            )
            if (updatedLaterThan != null) {
                where("updated_at > ${updatedLaterThan.epochSeconds}")
            }
            offset(offset)
            limit(limit)
        }
        logger.d { "REQ IGDB query: '$query'" }

        val igdbResult = igdbClient.execute(
            endpoint = IgdbEndpoint.GAME_MODE,
            query = query,
        ).toNetworkResult()

        val result: NetworkResult<List<GameModeIgdbDto>> = igdbResult.mapSuccessOnBackground { result ->
            logger.v { "Received game modes ${result.gamemodes}" }
            result.gamemodes.map(converter::toGameModeIgdbDto)
        }

        return result
    }

    private suspend fun <I : Any, O : Any> NetworkResult<I>.mapSuccessOnBackground(
        mapper: (I) -> O,
    ): NetworkResult<O> = flatMap { result ->
        withContext(backgroundDispatcher) {
            catch {
                mapper(result)
            }.mapLeft { error: Throwable ->
                logger.v(error) { "Mapping error" }
                NetworkRequestFailure.ApiFailure(error)
            }
        }
    }

    private fun Set<GameField>.toIgdbRequestFields(): Set<IgdbRequestField<*>> = this.flatMap {
        it.igdbFieldConverter.getRequiredFields()
    }.toSet()

    private fun List<IgdbGame>.prettyPrintGames() = joinToString(",\n") { game ->
        val releaseDates = game.release_dates.map {
            "{release_date: ${it.category}-${it.date}}"
        }
        "[${game.id}: ${game.name} $releaseDates]"
    }
}
