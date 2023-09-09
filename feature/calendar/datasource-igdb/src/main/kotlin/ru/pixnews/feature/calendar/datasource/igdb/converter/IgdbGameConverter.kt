/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
@file:Suppress("TooManyFunctions")

package ru.pixnews.feature.calendar.datasource.igdb.converter

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.game.AverageRating
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.gameId
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.locale.LanguageCode.Companion.ENGLISH
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.ApproximateDate.Unknown
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url
import ru.pixnews.domain.model.util.VideoUrl
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.igdbclient.model.Game as IgdbGame
import ru.pixnews.igdbclient.model.GameVideo as IgdbGameVideo
import ru.pixnews.igdbclient.model.Genre as IgdbGenre
import ru.pixnews.igdbclient.model.Platform as IgdbPlatform
import ru.pixnews.igdbclient.model.ReleaseDate as IgdbReleaseDate
import ru.pixnews.igdbclient.model.Screenshot as IgdbScreenshot
import ru.pixnews.igdbclient.model.Theme as IgdbTheme

internal fun IgdbGame.toGame(): Game {
    @Suppress("NULLABLE_PROPERTY_TYPE")
    val parentGameId: GameId? = parent_game?.gameId
    requireFieldInitialized("game.id", this.id)
    requireFieldInitialized("game.name", this.name)

    return Game(
        id = gameId,
        name = Localized(name, ENGLISH),
        summary = Localized(RichText(summary), ENGLISH),
        description = Localized(RichText(storyline), ENGLISH),
        videoUrls = convertVideoUrls(),
        screenshots = convertScreenshots(),
        developer = convertDeveloper(),
        publisher = convertPublisher(),
        releaseDate = convertReleaseDate(),
        releaseStatus = this.status.toReleaseStatus(),
        genres = genres.map(IgdbGenre::toGameGenre).toImmutableSet(),
        tags = themes.map(IgdbTheme::toGameTag).toImmutableSet(),
        ratings = convertRatingsSummary(),
        links = convertExternalLinks(),
        category = category.toGameReleaseCategory(parentGameId),
        parentGame = parentGameId,
        series = collection?.toGamesSeriesSummary(),
        platforms = convertGamePlatforms(),
        ageRanking = convertAgeRating(),
        localizations = convertLocalizations(),
        gameMode = game_modes.toGameModes(),
        playerPerspectives = player_perspectives.toPlayerPerspectives(),
        systemRequirements = null,
        dataSources = persistentListOf(igdbDataSource),
    )
}

private val IgdbGame.gameId: GameId get() = id.toString().gameId()

private fun IgdbGame.convertVideoUrls(): ImmutableList<VideoUrl> = videos
    .asSequence()
    .map(IgdbGameVideo::toIgdbVideoUrl)
    .toImmutableList()

private fun IgdbGame.convertScreenshots(): ImmutableList<ImageUrl> {
    val cover = this.cover?.let {
        sequenceOf(it.toIgdbImageUrl())
    } ?: emptySequence()

    val screenshots = this.screenshots
        .asSequence()
        .map(IgdbScreenshot::toIgdbImageUrl)

    return (cover + screenshots).toImmutableList()
}

private fun IgdbGame.convertReleaseDate(): ApproximateDate = findMostRelevantReleaseDate()
    ?.let(IgdbReleaseDate::toApproximateDate)
    ?: Unknown()

private fun IgdbGame.findMostRelevantReleaseDate(): IgdbReleaseDate? {
    release_dates.forEach { requireField("game.updated_at", it.updated_at != null) }
    return release_dates.maxWithOrNull(
        compareBy(IgdbReleaseDate::updated_at, IgdbReleaseDate::date, IgdbReleaseDate::id),
    )
}

private fun IgdbGame.convertDeveloper() = involved_companies
    .firstOrNull { it.developer }
    ?.company
    ?.toCompany()

private fun IgdbGame.convertPublisher() = involved_companies
    .firstOrNull { it.publisher }
    ?.company
    ?.toCompany()

@Suppress("MagicNumber", "FLOAT_IN_ACCURATE_CALCULATIONS")
private fun IgdbGame.convertRatingsSummary(): RatingsSummary {
    return RatingsSummary(
        gameId = gameId,
        numberOfVotes = this.rating_count.toULong(),
        averageRating = if (rating >= 1.0) {
            AverageRating((rating / 10.0).toFloat().coerceIn(1f..10f))
        } else {
            null
        },
        distribution = null,
    )
}

private fun IgdbGame.convertExternalLinks() = sequence {
    if (url.isNotEmpty()) {
        yield(ExternalLink(type = OFFICIAL, url = Url(url)))
    }
    websites.forEach { yield(it.toExternalLink()) }
}.toImmutableList()

private fun IgdbGame.convertGamePlatforms(): ImmutableSet<GamePlatform> = platforms
    .map(IgdbPlatform::toGamePlatform)
    .toImmutableSet()

private fun IgdbGame.convertAgeRating(): AgeRating? = age_ratings.toAgeRating()

private fun IgdbGame.convertLocalizations(): GameLocalizations? =
    if (language_supports.isNotEmpty()) {
        language_supports.toLocalizations()
    } else {
        null
    }
