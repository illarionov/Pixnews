/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.RUMORED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.PlayerPerspective.FirstPerson
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.halfLife
import ru.pixnews.domain.model.game.shooter
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDetermined
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

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
        series = FullObject(GameSeriesSummaryFixtures.halfLife),
        platforms = persistentSetOf(FullObject(Windows)),
        ageRanking = null,
        localizations = null,
        gameMode = persistentSetOf(FullObject(SinglePlayer)),
        playerPerspectives = persistentSetOf(FullObject(FirstPerson)),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
