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
import ru.pixnews.domain.model.company.company.electronicArts
import ru.pixnews.domain.model.company.company.theSimsStudio
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameMode.Multiplayer
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.rpg
import ru.pixnews.domain.model.game.sims
import ru.pixnews.domain.model.game.simulator
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val sims5GameId = GameId("the-sims-5")

public val GameFixtures.sims5: Game
    get() = Game(
        id = sims5GameId,
        name = Localized(value = "The Sims 5", language = LanguageCode.ENGLISH),
        summary = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        videoUrls = persistentListOf(),
        screenshots = persistentListOf(),
        developer = CompanyFixtures.theSimsStudio,
        publisher = CompanyFixtures.electronicArts,
        releaseDate = ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
        releaseStatus = NOT_YET_RELEASED,
        genres = persistentSetOf(
            GameGenreFixtures.rpg,
            GameGenreFixtures.simulator,
        ),
        tags = persistentSetOf(),
        ratings = RatingsSummary(
            gameId = sims5GameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(
            ExternalLink(TWITCH, Url("https://www.twitch.tv/directory/game/The%20Sims%205")),
        ),
        category = MainGame,
        parentGame = null,
        series = GameSeriesSummaryFixtures.sims,
        platforms = persistentSetOf(),
        ageRanking = null,
        localizations = null,
        gameMode = persistentSetOf(
            Multiplayer,
            SinglePlayer,
        ),
        playerPerspectives = persistentSetOf(),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
