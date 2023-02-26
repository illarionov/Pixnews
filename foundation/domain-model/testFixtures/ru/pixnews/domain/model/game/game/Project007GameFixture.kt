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
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.ioInteractive
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GamePlatform.PlayStation5
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GamePlatform.XboxSeriesXs
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.jamesBond
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.DefaultVideoUrl
import ru.pixnews.domain.model.util.RichText

private val project007GameId = GameId("project-007")

public val GameFixtures.project007: Game
    get() = Game(
        id = project007GameId,
        name = Localized(value = "Project 007", language = LanguageCode.ENGLISH),
        summary = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        videoUrls = persistentListOf(
            DefaultVideoUrl("https://www.youtube.com/watch?v=slAhuh21ii8"),
        ),
        screenshots = persistentListOf(
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/co56d8.png",
                size = CanvasSize(width = 264U, height = 352U),
            ),
        ),
        developer = CompanyFixtures.ioInteractive,
        publisher = CompanyFixtures.ioInteractive,
        releaseDate = ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
        releaseStatus = NOT_YET_RELEASED,
        genres = persistentSetOf(),
        tags = persistentSetOf(),
        ratings = RatingsSummary(
            gameId = project007GameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(),
        category = MainGame,
        parentGame = null,
        series = GameSeriesSummaryFixtures.jamesBond,
        platforms = persistentSetOf(PlayStation5, Windows, XboxSeriesXs),
        ageRanking = null,
        localizations = null,
        gameMode = persistentSetOf(),
        playerPerspectives = persistentSetOf(),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
