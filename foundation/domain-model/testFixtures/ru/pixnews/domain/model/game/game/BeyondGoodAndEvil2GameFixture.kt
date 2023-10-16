/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game.game

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.ubisoftEntertainment
import ru.pixnews.domain.model.company.company.ubisoftMontpellier
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.datetime.Date
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.domain.model.game.GamePlatform.PlayStation4
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GamePlatform.XboxOne
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.domain.model.game.PlayerPerspective
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.beyondGoodEvil
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating.RATING_PENDING
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.DefaultVideoUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.FACEBOOK
import ru.pixnews.domain.model.url.ExternalLinkType.INSTAGRAM
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

internal val beyondGoodEvil2GameId = GameId("beyond-good-evil-2")

public val GameFixtures.beyondGoodEvil2: Game
    get() = Game(
        id = beyondGoodEvil2GameId,
        name = Localized(
            value = "Beyond Good & Evil 2",
            language = LanguageCode.ENGLISH,
        ),
        summary = Localized(
            value = RichText(
                """Beyond Good & Evil 2 marks the return of one of Ubisoftís most beloved games.
                |The prequel will transport players into a profoundly multicultural world, capturing the spirit of the
                | original with unforgettable characters, grandiose decors and intense dramas that play out across a
                | vast universe. Players will rise from lowly pirate to legendary captain at the helm of massive
                | star-faring vessels, adventuring alongside crews of unforgettable characters to fight for freedom
                | and theright to determine their own fate among the stars.
                | Ubisoft Montpellier will be developing the game alongside its community of fans.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        videoUrls = persistentListOf(
            DefaultVideoUrl("https://youtu.be/o_Yc00WErvk"),
            DefaultVideoUrl("https://youtu.be/tbOYWc6bG5A"),
            DefaultVideoUrl("https://youtu.be/GuKVcEeu-80"),
            DefaultVideoUrl("https://youtu.be/Vyz7eiLA"),
            DefaultVideoUrl("https://youtu.be/lXK6Uq23eLQ"),
            DefaultVideoUrl("https://youtu.be/Y2229DmJLIY"),
        ),
        screenshots = persistentListOf(
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/akq8sc89dv97xfllbgl9.webp",
                size = CanvasSize(1334U, 563U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/pextdhnl8rctrvmaru3y.webp",
                size = CanvasSize(1270U, 566U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/ycupa4kv7rynudk4vlxw.webp",
                size = CanvasSize(1337U, 561U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/j9dqsv1psqqnsqj1wkxe.webp",
                size = CanvasSize(1336U, 564U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/xtwej9t60lrwfbazurcy.webp",
                size = CanvasSize(1334U, 567U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/brykknvyoymk8xgqxnqr.webp",
                size = CanvasSize(1334U, 564U),
            ),
        ),
        developer = CompanyFixtures.ubisoftEntertainment,
        publisher = CompanyFixtures.ubisoftMontpellier,
        releaseDate = Date.Year(2024),
        releaseStatus = NOT_YET_RELEASED,
        genres = persistentSetOf(GameGenreFixtures.adventure),
        tags = listOf(
            "action-adventure",
            "anthropomorphism",
            "development hell",
            "domz",
            "e3 2017",
            "fantasy",
            "female protagonists",
            "interspecies duos",
            "lyn engine",
            "pig",
            "prequel",
            "sci-fi",
            "vaporware",
        ).map(::GameTag).toImmutableSet(),
        ratings = RatingsSummary(
            gameId = beyondGoodEvil2GameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("http://bgegame.com/")),
            ExternalLink(TWITCH, Url("https://www.twitch.tv/directory/game/Beyond%20Good%20&%20Evil%202")),
            ExternalLink(FACEBOOK, Url("https://www.facebook.com/beyondgoodandevil")),
            ExternalLink(TWITTER, Url("https://twitter.com/bgegame")),
            ExternalLink(INSTAGRAM, Url("https://www.instagram.com/bgegame")),
            ExternalLink(WIKIPEDIA, Url("https://en.wikipedia.org/wiki/Beyond_Good_and_Evil_2")),
        ),
        category = MainGame,
        parentGame = null,
        series = FullObject(GameSeriesSummaryFixtures.beyondGoodEvil),
        platforms = persistentSetOf(FullObject(XboxOne), FullObject(PlayStation4), FullObject(Windows)),
        ageRanking = AgeRating(
            esrbRating = RATING_PENDING,
            pegiRating = null,
        ),
        localizations = null,
        gameMode = listOf(GameMode.SinglePlayer, GameMode.CoOperative)
            .map(::FullObject).toImmutableSet(),
        playerPerspectives = persistentSetOf(FullObject(PlayerPerspective.ThirdPerson)),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
