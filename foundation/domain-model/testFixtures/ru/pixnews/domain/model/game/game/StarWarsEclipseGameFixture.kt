/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game.game

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.quanticDream
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GamePlatform.PlayStation5
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.beyondGoodEvil
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating.RATING_PENDING
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.DefaultVideoUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.RichText

internal val starWarsEclipseGameId = GameId("star-wars-eclipse")

public val GameFixtures.starWarsEclipse: Game
    get() = Game(
        id = starWarsEclipseGameId,
        name = Localized(
            value = "Star Wars: Eclipse",
            language = LanguageCode.ENGLISH,
        ),
        summary = Localized(
            value = RichText(
                """A new action-adventure, multiple-character branching narrative game set in the
                |High Republic era, presented to you by Quantic Dream and Lucasfilm Games. Now early in development.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        videoUrls = persistentListOf(
            DefaultVideoUrl("https://www.youtube.com/watch?v=4cJpiOPKH14"),
        ),
        screenshots = (
                listOf(
                    DefaultImageUrl(
                        rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/co4a8z.webp",
                        size = CanvasSize(900U, 1200U),
                    ),
                ) + listOf(
                    "https://images.igdb.com/igdb/image/upload/t_original/scf141.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scf140.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scf13z.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scf13y.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scf13x.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scf13w.webp",
                ).map {
                    DefaultImageUrl(
                        rawUrl = it,
                        size = CanvasSize(1400U, 600U),
                    )
                }
                ).toImmutableList(),
        developer = CompanyFixtures.quanticDream,
        publisher = null,
        releaseDate = ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
        releaseStatus = NOT_YET_RELEASED,
        genres = persistentSetOf(GameGenreFixtures.adventure),
        tags = persistentSetOf(),
        ratings = RatingsSummary(
            gameId = starWarsEclipseGameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("http://www.starwarseclipse.com/")),
        ),
        category = MainGame,
        parentGame = null,
        series = FullObject(GameSeriesSummaryFixtures.beyondGoodEvil),
        platforms = persistentSetOf(PlayStation5, Windows).map { FullObject(it) }.toImmutableSet(),
        ageRanking = AgeRating(
            esrbRating = RATING_PENDING,
            pegiRating = null,
        ),
        localizations = null,
        gameMode = persistentSetOf(),
        playerPerspectives = persistentSetOf(),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
