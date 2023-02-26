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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.vale
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.RUMORED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.PlayerPerspective.FirstPerson
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.halfLife
import ru.pixnews.domain.model.game.shooter
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDetermined
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.RichText

internal val halflife3GameId = GameId("half-life-3")

public val GameFixtures.halfLife3: Game
    get() = Game(
        id = halflife3GameId,
        name = Localized(
            value = "Half-Life 3",
            language = LanguageCode.ENGLISH,
        ),
        summary = Localized(
            value = RichText("The long awaited final part of the Half-Life saga"),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(
            value = RichText(""),
            language = LanguageCode.ENGLISH,
        ),
        videoUrls = persistentListOf(),
        screenshots = persistentListOf(
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/co1t0u.png",
                size = CanvasSize(width = 264U, height = 352U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/ntsb9jxu5pl3stgamwfr.jpg",
                size = CanvasSize(width = 1595U, height = 628U),
            ),
        ),
        developer = CompanyFixtures.vale,
        publisher = CompanyFixtures.vale,
        releaseDate = ToBeDetermined(
            expected = LocalDateTime(
                year = 2024,
                month = Month.JANUARY,
                dayOfMonth = 1,
                hour = 0,
                minute = 0,
                second = 0,
                nanosecond = 0,
            ).toInstant(TimeZone.UTC) to null,
            description = Localized("", LanguageCode.ENGLISH),
        ),
        releaseStatus = RUMORED,
        genres = persistentSetOf(
            GameGenreFixtures.shooter,
        ),
        tags = persistentSetOf(),
        ratings = RatingsSummary(
            gameId = halflife3GameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(),
        category = MainGame,
        parentGame = null,
        series = GameSeriesSummaryFixtures.halfLife,
        platforms = persistentSetOf(Windows),
        ageRanking = null,
        localizations = null,
        gameMode = persistentSetOf(SinglePlayer),
        playerPerspectives = persistentSetOf(FirstPerson),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
