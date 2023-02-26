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
import ru.pixnews.domain.model.company.company.rockstarGames
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameReleaseCategory
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.gta
import ru.pixnews.domain.model.game.shooter
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.RichText

internal val gta6GameId = GameId("grand-theft-auto-vi")

public val GameFixtures.gta6: Game
    get() = Game(
        id = gta6GameId,
        name = Localized(
            value = "Grand Theft Auto VI",
            language = LanguageCode.ENGLISH,
        ),
        summary = Localized(
            value = RichText("""The next entry in the Grand Theft Auto series.""".trimIndent()),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(
            value = RichText(""),
            language = LanguageCode.ENGLISH,
        ),
        videoUrls = persistentListOf(),
        screenshots = persistentListOf(),
        developer = CompanyFixtures.rockstarGames,
        publisher = CompanyFixtures.rockstarGames,
        releaseDate = ApproximateDate.ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
        releaseStatus = NOT_YET_RELEASED,
        genres = persistentSetOf(
            GameGenreFixtures.adventure,
            GameGenreFixtures.shooter,
        ),
        tags = persistentSetOf(),
        ratings = RatingsSummary(
            gameId = gta6GameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(),
        category = GameReleaseCategory.MainGame,
        parentGame = null,
        series = GameSeriesSummaryFixtures.gta,
        platforms = persistentSetOf(),
        ageRanking = null,
        localizations = null,
        gameMode = persistentSetOf(),
        playerPerspectives = persistentSetOf(),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
