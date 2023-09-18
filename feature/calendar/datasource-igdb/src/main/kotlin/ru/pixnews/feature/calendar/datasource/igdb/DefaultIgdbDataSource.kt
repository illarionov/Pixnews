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
import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbRequestField
import ru.pixnews.feature.calendar.datasource.igdb.field.builder.field
import ru.pixnews.feature.calendar.datasource.igdb.field.child
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbFieldTemp
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
            IgdbGame.field.release_dates.child("*"),
        )
        logger.i { "request fields: $igdbFields" }

        @Suppress("MagicNumber")
        val igdbGameResult = igdbClient.execute(
            endpoint = IgdbEndpoint.GAME,
            query = apicalypseQuery {
                fields(fieldList = igdbFields.map { it.igdbName }.toTypedArray())
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
            IgdbGame.field.videos.child("video_id"),
        )

        GameField.Screenshots -> listOf(
            "image_id",
            "animated",
            "width",
            "height",
        ).map { IgdbGame.field.cover.child(it) } + listOf(
            "image_id",
            "animated",
            "width",
            "height",
        ).map { IgdbGame.field.screenshots.child(it) }

        GameField.Developer, GameField.Publisher -> listOf(
            IgdbGame.field.involved_companies.child("developer"),
        ) + listOf(
            "id",
            "name",
            "developer",
            "publisher",
            "description",
            "start_date",
            "start_date_category",
            "country",
            "parent",
            "url",
            "websites.category",
            "websites.url",
        ).map { IgdbGame.field.involved_companies.child("company").child(it) } + listOf(
            "image_id",
            "animated",
            "width",
            "height",
        ).map { IgdbGame.field.involved_companies.child("company").child("logo").child(it) }

        GameField.ReleaseDate -> listOf(
            "category",
            "date",
            "y",
            "m",
            "human",
        ).map { IgdbRequestField(IgdbFieldTemp(it), IgdbGame.field.release_dates) }

        GameField.ReleaseStatus -> listOf(IgdbGame.field.status)

        GameField.Genres -> listOf("name")
            .map { IgdbRequestField(IgdbFieldTemp(it), IgdbGame.field.genres) }

        GameField.Tags -> listOf("name")
            .map { IgdbRequestField(IgdbFieldTemp(it), IgdbGame.field.themes) }

        GameField.Ratings -> listOf(
            IgdbGame.field.rating_count,
            IgdbGame.field.rating,
            IgdbGame.field.aggregated_rating_count,
            IgdbGame.field.aggregated_rating,
        )

        GameField.Links -> listOf(
            IgdbGame.field.url,
        ) + listOf(
            "category",
            "url",
        ).map { IgdbGame.field.websites.child(it) }

        GameField.Category -> listOf(
            IgdbGame.field.parent_game,
            IgdbGame.field.category,
        )

        is GameField.ParentGame -> listOf(IgdbGame.field.parent_game)
        is GameField.Series -> listOf(
            IgdbGame.field.collection.child("id"),
            IgdbGame.field.collection.child("name"),
            IgdbGame.field.collection.child("games").child("id"),
            IgdbGame.field.collection.child("games").child("name"),
        ) + listOf(
            IgdbGame.field.parent_game,
        )

        is GameField.Platforms -> listOf(
            "id",
            "slug",
            "name",
        ).map { IgdbGame.field.platforms.child(it) }

        GameField.AgeRanking -> IgdbGame.field.age_ratings.let {
            listOf(it.id, it.category, it.rating)
        }

        GameField.Localizations -> listOf(
            IgdbGame.field.language_supports.child("language_support_type"),
            IgdbGame.field.language_supports.child("language").child("locale"),
        )

        is GameField.GameMode -> listOf(
            IgdbGame.field.game_modes.child("id"),
            IgdbGame.field.game_modes.child("slug"),
            IgdbGame.field.game_modes.child("name"),
        )

        is GameField.PlayerPerspective -> listOf(
            IgdbGame.field.player_perspectives.child("id"),
        )

        GameField.SystemRequirements -> error("$this not supported")
    }
}
