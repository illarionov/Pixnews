/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game.game

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.hypixelStudios
import ru.pixnews.domain.model.company.company.riotGames
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.domain.model.game.GamePlatform.Android
import ru.pixnews.domain.model.game.GamePlatform.Ios
import ru.pixnews.domain.model.game.GamePlatform.Macos
import ru.pixnews.domain.model.game.GamePlatform.NintendoSwitch
import ru.pixnews.domain.model.game.GamePlatform.PlayStation5
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GamePlatform.XboxSeriesXs
import ru.pixnews.domain.model.game.GameReleaseCategory
import ru.pixnews.domain.model.game.GameReleaseStatus.NOT_YET_RELEASED
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.domain.model.game.PlayerPerspective
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.indie
import ru.pixnews.domain.model.game.rpg
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.FACEBOOK
import ru.pixnews.domain.model.links.ExternalLinkType.INSTAGRAM
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.links.ExternalLinkType.REDDIT
import ru.pixnews.domain.model.links.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.links.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.links.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.links.ExternalLinkType.YOUTUBE
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.DefaultVideoUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val hytaleGameId = GameId("hytale")

public val GameFixtures.hytale: Game
    get() = Game(
        id = hytaleGameId,
        name = Localized(value = "하이테일", language = LanguageCode.KOREAN),
        summary = Localized(
            value = RichText(
                """
                |Hytale combines the scope of a sandbox with the depth of a roleplaying game,
                |immersing players in a procedurally generated world where teetering towers and deep dungeons promise
                |rich rewards throughout their adventures. Hytale supports everything from block-by-block construction
                |to scripting and minigame creation, delivered using easy to use and powerful tools."""
                    .trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        videoUrls = persistentListOf(
            DefaultVideoUrl("https://www.youtube.com/embed/p6aH25Jx3rs"),
            DefaultVideoUrl("https://www.youtube.com/embed/o77MzDQT1cg"),
        ),
        screenshots = (
                listOf(
                    DefaultImageUrl(
                        rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/co1r65.webp",
                        size = CanvasSize(width = 846U, height = 1128U),
                    ),
                ) + listOf(
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rik.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5ril.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rii.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rim.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rio.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rin.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5riq.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rip.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rir.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5rit.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/sc5ris.webp",
                ).map {
                    DefaultImageUrl(
                        rawUrl = it,
                        size = CanvasSize(width = 1920U, height = 1080U),
                    )
                }
                ).toImmutableList(),
        developer = CompanyFixtures.hypixelStudios,
        publisher = CompanyFixtures.riotGames,
        releaseDate = ApproximateDate.ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
        releaseStatus = NOT_YET_RELEASED,
        genres = persistentSetOf(
            GameGenreFixtures.adventure,
            GameGenreFixtures.indie,
            GameGenreFixtures.rpg,
        ),
        tags = persistentSetOf(
            GameTag("hypixel"),
            GameTag("hytale"),
        ),
        ratings = RatingsSummary(
            gameId = hytaleGameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://hytale.com/")),
            ExternalLink(TWITCH, Url("https://www.twitch.tv/directory/game/Hytale")),
            ExternalLink(YOUTUBE, Url("https://www.youtube.com/Hytale")),
            ExternalLink(FACEBOOK, Url("https://www.facebook.com/HytaleGame")),
            ExternalLink(TWITTER, Url("https://twitter.com/Hytale")),
            ExternalLink(INSTAGRAM, Url("https://www.instagram.com/HytaleGame")),
            ExternalLink(REDDIT, Url("https://www.reddit.com/r/Hytale")),
            ExternalLink(WIKIPEDIA, Url("https://en.wikipedia.org/wiki/Hytale")),
        ),
        category = GameReleaseCategory.MainGame,
        parentGame = null,
        series = null,
        platforms = persistentSetOf(Android, Macos, Ios, PlayStation5, Windows, NintendoSwitch, XboxSeriesXs),
        ageRanking = null,
        localizations = GameLocalizations(
            sound = persistentSetOf(LanguageCode.ENGLISH, LanguageCode.KOREAN),
            text = persistentSetOf(LanguageCode.ENGLISH, LanguageCode.KOREAN),
        ),
        gameMode = persistentSetOf(
            GameMode.BattleRoyale,
            GameMode.Multiplayer,
            GameMode.SinglePlayer,
        ),
        playerPerspectives = persistentSetOf(
            PlayerPerspective.FirstPerson,
            PlayerPerspective.ThirdPerson,
        ),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
