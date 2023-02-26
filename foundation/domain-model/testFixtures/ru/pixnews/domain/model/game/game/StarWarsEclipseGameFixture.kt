/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.domain.model.game.game

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.quanticDream
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GamePlatform.PlayStation5
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.beyondGoodEvil
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating.RATING_PENDING
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.DefaultVideoUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

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
        series = GameSeriesSummaryFixtures.beyondGoodEvil,
        platforms = persistentSetOf(PlayStation5, Windows),
        ageRanking = AgeRating(
            gameId = starWarsEclipseGameId,
            esrbRating = RATING_PENDING,
            pegiRating = null,
        ),
        localizations = null,
        gameMode = persistentSetOf(),
        playerPerspectives = persistentSetOf(),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
