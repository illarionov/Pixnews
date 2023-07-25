/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
