/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
import ru.pixnews.domain.model.game.GameMode.Multiplayer
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.rpg
import ru.pixnews.domain.model.game.sims
import ru.pixnews.domain.model.game.simulator
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

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
        series = FullObject(GameSeriesSummaryFixtures.sims),
        platforms = persistentSetOf(),
        ageRanking = null,
        localizations = null,
        gameMode = persistentSetOf(
            FullObject(Multiplayer),
            FullObject(SinglePlayer),
        ),
        playerPerspectives = persistentSetOf(),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
