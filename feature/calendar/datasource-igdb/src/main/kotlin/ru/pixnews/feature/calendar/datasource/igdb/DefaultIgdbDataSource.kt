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
import ru.pixnews.feature.calendar.datasource.igdb.dsl.IgdbRequestField
import ru.pixnews.feature.calendar.datasource.igdb.field.field
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
            IgdbGame.field.release_dates.all,
        )
        logger.i { "request fields: $igdbFields" }

        @Suppress("MagicNumber")
        val igdbGameResult = igdbClient.execute(
            endpoint = IgdbEndpoint.GAME,
            query = apicalypseQuery {
                fields(fieldList = igdbFields.map { it.igdbFullName }.toTypedArray())
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

    private fun Set<GameField>.toIgdbRequestFields(): Set<IgdbRequestField<*>> = this.flatMap {
        it.toIgdbRequestFields()
    }.toSet()

    @Suppress("CyclomaticComplexMethod", "LongMethod", "COMPLEX_EXPRESSION")
    private fun GameField.toIgdbRequestFields(): Collection<IgdbRequestField<*>> = when (this) {
        GameField.Id -> listOf(
            IgdbGame.field.id,
            IgdbGame.field.slug,
        )

        GameField.Name -> listOf(IgdbGame.field.name)
        GameField.Description -> listOf(IgdbGame.field.storyline)
        GameField.Summary -> listOf(IgdbGame.field.summary)
        GameField.VideoUrls -> listOf(
            IgdbGame.field.videos.video_id,
        )

        GameField.Screenshots -> listOf(
            IgdbGame.field.cover.image_id,
            IgdbGame.field.cover.animated,
            IgdbGame.field.cover.width,
            IgdbGame.field.cover.height,
            IgdbGame.field.screenshots.image_id,
            IgdbGame.field.screenshots.animated,
            IgdbGame.field.screenshots.width,
            IgdbGame.field.screenshots.height,
        )

        GameField.Developer, GameField.Publisher -> IgdbGame.field.involved_companies.let {
            listOf(
                it.developer,
                it.company.id,
                it.company.name,
                it.developer,
                it.publisher,
            )
        } + IgdbGame.field.involved_companies.company.let { company ->
            listOf(
                company.description,
                company.start_date,
                company.start_date_category,
                company.country,
                company.parent.id,
                company.url,
                company.websites.category,
                company.websites.url,
                company.logo.image_id,
                company.logo.animated,
                company.logo.width,
                company.logo.height,
            )
        }

        GameField.ReleaseDate -> IgdbGame.field.release_dates.let {
            listOf(
                it.category,
                it.date,
                it.y,
                it.m,
                it.human,
            )
        }

        GameField.ReleaseStatus -> listOf(IgdbGame.field.status)

        GameField.Genres -> listOf(IgdbGame.field.genres.name)

        GameField.Tags -> listOf(IgdbGame.field.themes.name)

        GameField.Ratings -> listOf(
            IgdbGame.field.rating_count,
            IgdbGame.field.rating,
            IgdbGame.field.aggregated_rating_count,
            IgdbGame.field.aggregated_rating,
        )

        GameField.Links -> listOf(
            IgdbGame.field.url,
            IgdbGame.field.websites.category,
            IgdbGame.field.websites.url,
        )

        GameField.Category -> listOf(
            IgdbGame.field.parent_game.id,
            IgdbGame.field.category,
        )

        is GameField.ParentGame -> listOf(
            IgdbGame.field.parent_game.id,
        )

        is GameField.Series -> listOf(
            IgdbGame.field.collection.id,
            IgdbGame.field.collection.name,
            IgdbGame.field.collection.games.id,
            IgdbGame.field.collection.games.name,
            IgdbGame.field.parent_game.id,
        )

        is GameField.Platforms -> listOf(
            IgdbGame.field.platforms.id,
            IgdbGame.field.platforms.slug,
            IgdbGame.field.platforms.name,
        )

        GameField.AgeRanking -> IgdbGame.field.age_ratings.let {
            listOf(it.id, it.category, it.rating)
        }

        GameField.Localizations -> listOf(
            IgdbGame.field.language_supports.language_support_type.id,
            IgdbGame.field.language_supports.language_support_type.name,
            IgdbGame.field.language_supports.language.locale,
        )

        is GameField.GameMode -> listOf(
            IgdbGame.field.game_modes.id,
            IgdbGame.field.game_modes.slug,
            IgdbGame.field.game_modes.name,
        )

        is GameField.PlayerPerspective -> listOf(
            IgdbGame.field.player_perspectives.id,
        )

        GameField.SystemRequirements -> error("$this not supported")
    }
}
