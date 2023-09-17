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
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameField
import ru.pixnews.feature.calendar.data.IgdbDataSource
import ru.pixnews.feature.calendar.datasource.igdb.converter.toGame
import ru.pixnews.feature.calendar.datasource.igdb.converter.toNetworkResult
import ru.pixnews.foundation.coroutines.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.igdbclient.IgdbClient
import ru.pixnews.igdbclient.IgdbEndpoint
import ru.pixnews.igdbclient.apicalypse.SortOrder.DESC
import ru.pixnews.igdbclient.apicalypse.apicalypseQuery
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.library.functional.network.NetworkResult
import javax.inject.Inject
import ru.pixnews.igdbclient.model.Game as IgdbGame

@ContributesBinding(boundType = IgdbDataSource::class, scope = AppScope::class)
public class DefaultIgdbDataSource(
    private val igdbClient: IgdbClient,
    private val backgroundDispatcher: CoroutineDispatcher,
    @Suppress("UnusedPrivateProperty") private val clock: Clock,
    logger: Logger,
) : IgdbDataSource {
    private val logger = logger.withTag("DefaultIgdbDataSource")

    @Inject
    public constructor(
        igdbClient: IgdbClient,
        backgroundDispatcher: ComputationCoroutineDispatcherProvider,
        logger: Logger,
    ) : this(igdbClient, backgroundDispatcher.get(), Clock.System, logger)

    override suspend fun fetchUpcomingReleases(
        startDate: Instant,
        requiredFields: Set<GameField>,
    ): NetworkResult<List<Game>> {
        val igdbFields = requiredFields.toIgdbRequestFields() + setOf(
            "release_dates.*",
        )

        @Suppress("MagicNumber")
        val igdbGameResult = igdbClient.execute(
            endpoint = IgdbEndpoint.GAME,
            query = apicalypseQuery {
                fields(fieldList = igdbFields.toTypedArray())
                where("release_dates.date > ${startDate.epochSeconds}")
                limit(10)
                sort("id", DESC)
            },
        ).toNetworkResult()

        val result: NetworkResult<List<Game>> = igdbGameResult.flatMap { gameResult ->
            withContext(backgroundDispatcher) {
                catch {
                    logger.d { "Received games: ${gameResult.games}" }
                    gameResult.games.map(IgdbGame::toGame)
                }.mapLeft { error ->
                    logger.e(error) { "Mapping error" }
                    NetworkRequestFailure.ApiFailure(error)
                }
            }
        }

        return result
    }

    private fun Set<GameField>.toIgdbRequestFields(): Set<String> = this.flatMap {
        it.toIgdbRequestFields()
    }.toSet()

    @Suppress("CyclomaticComplexMethod", "LongMethod")
    private fun GameField.toIgdbRequestFields(): Collection<String> = when (this) {
        GameField.Id -> listOf("id", "slug")
        GameField.Name -> listOf("name")
        GameField.Description -> listOf("storyline")
        GameField.Summary -> listOf("summary")
        GameField.VideoUrls -> listOf("videos.video_id")
        GameField.Screenshots -> listOf(
            "cover.image_id",
            "cover.animated",
            "cover.width",
            "cover.height",
            "screenshots.image_id",
            "screenshots.animated",
            "screenshots.width",
            "screenshots.height",
        )

        GameField.Developer, GameField.Publisher -> listOf(
            "involved_companies.developer",
            "involved_companies.company.id",
            "involved_companies.company.name",
            "involved_companies.company.developer",
            "involved_companies.company.publisher",
            "involved_companies.company.description",
            "involved_companies.company.logo.image_id",
            "involved_companies.company.logo.animated",
            "involved_companies.company.logo.width",
            "involved_companies.company.logo.height",
            "involved_companies.company.start_date",
            "involved_companies.company.start_date_category",
            "involved_companies.company.country",
            "involved_companies.company.parent",
            "involved_companies.company.url",
            "involved_companies.company.websites.category",
            "involved_companies.company.websites.url",
        )

        GameField.ReleaseDate -> listOf(
            "release_dates.category",
            "release_dates.date",
            "release_dates.y",
            "release_dates.m",
            "release_dates.human",
        )

        GameField.ReleaseStatus -> listOf(
            "status",
        )

        GameField.Genres -> listOf("genres.name")
        GameField.Tags -> listOf("themes.name")
        GameField.Ratings -> listOf(
            "rating_count",
            "rating",
            "aggregated_rating",
            "aggregated_rating_count",
        )

        GameField.Links -> listOf(
            "url",
            "websites.category",
            "websites.url",
        )

        GameField.Category -> listOf(
            "parent_game",
            "category",
        )

        is GameField.ParentGame -> listOf("parent_game")
        is GameField.Series -> listOf(
            "collection.id",
            "collection.name",
            "collection.games.id",
            "collection.games.name",
            "parent_game",
        )

        is GameField.Platforms -> listOf(
            "platforms.id",
            "platforms.slug",
            "platforms.name",
        )

        GameField.AgeRanking -> listOf(
            "age_ratings.id",
            "age_ratings.category",
            "age_ratings.rating",
        )

        GameField.Localizations -> listOf(
            "language_supports.language_support_type",
            "language_supports.language.locale",
        )

        is GameField.GameMode -> listOf(
            "game_modes.id",
            "game_modes.slug",
            "game_modes.name",
        )

        is GameField.PlayerPerspective -> listOf(
            "player_perspectives.id",
        )

        GameField.SystemRequirements -> error("$this not supported")
    }
}
