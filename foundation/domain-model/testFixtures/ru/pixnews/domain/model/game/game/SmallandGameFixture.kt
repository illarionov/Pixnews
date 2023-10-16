/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game.game

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.mergeGames
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.datetime.Date.Year
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.game.GameMode.CoOperative
import ru.pixnews.domain.model.game.GameMode.Multiplayer
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.EARLY_ACCESS
import ru.pixnews.domain.model.game.PlayerPerspective.ThirdPerson
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.indie
import ru.pixnews.domain.model.game.rpg
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.DefaultImageUrl
import ru.pixnews.domain.model.url.DefaultVideoUrl
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.DISCORD
import ru.pixnews.domain.model.url.ExternalLinkType.EPICGAMES_STORE
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.STEAM
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.ExternalLinkType.YOUTUBE
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.internationalization.language.LanguageCode

internal val smallandGameId = GameId("smalland")

public val GameFixtures.smalland: Game
    get() = Game(
        id = smallandGameId,
        name = Localized(
            value = "Smalland",
            language = LanguageCode.ENGLISH,
        ),
        summary = Localized(
            value = RichText(
                """
                | Smalland is a big adventure on a tiny scale! Enjoy multiplayer survival in a vast, hazardous world.
                | Preparation is key when you're this small, surrounded by massive creatures & at the bottom of the
                | food chain. Craft weapons & armour, build encampments & explore a strange new land.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        videoUrls = persistentListOf(
            DefaultVideoUrl("https://www.youtube.com/watch?v=zViO38TNVJY"),
        ),
        screenshots = persistentListOf(
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/co3p5t.webp",
                size = CanvasSize(600U, 800U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmn.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmm.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrml.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmk.webp",
                size = CanvasSize(2560U, 1440U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmj.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmi.webp",
                size = CanvasSize(2560U, 1440U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmh.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmg.webp",
                size = CanvasSize(1600U, 900U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmf.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrme.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmd.webp",
                size = CanvasSize(2560U, 1440U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmc.webp",
                size = CanvasSize(2560U, 1440U),
            ),
        ),
        developer = CompanyFixtures.mergeGames,
        publisher = CompanyFixtures.mergeGames,
        releaseDate = Year(2024),
        releaseStatus = EARLY_ACCESS,
        genres = persistentSetOf(
            GameGenreFixtures.adventure,
            GameGenreFixtures.indie,
            GameGenreFixtures.rpg,
        ),
        tags = persistentSetOf(),
        ratings = RatingsSummary(
            gameId = smallandGameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://www.mergegames.com/games/smalland")),
            ExternalLink(STEAM, Url("https://store.steampowered.com/app/768200/SMALLAND")),
            ExternalLink(EPICGAMES_STORE, Url("https://www.epicgames.com/store/p/smalland")),
            ExternalLink(TWITTER, Url("https://twitter.com/playsmalland")),
            ExternalLink(YOUTUBE, Url("https://www.youtube.com/channel/UC8Qkz9-7O3mRu4QUaidgPGg")),
            ExternalLink(DISCORD, Url("https://discord.gg/smalland")),
        ),
        category = MainGame,
        parentGame = null,
        series = null,
        platforms = persistentSetOf(FullObject(Windows)),
        ageRanking = null,
        localizations = GameLocalizations(
            sound = persistentSetOf(),
            text = persistentSetOf(
                LanguageCode.ENGLISH,
                LanguageCode.CHINESE,
                LanguageCode.SPANISH,
                LanguageCode.FRENCH,
                LanguageCode.JAPANESE,
                LanguageCode.PORTUGUESE,
                LanguageCode.RUSSIAN,
                LanguageCode.GERMAN,
            ),
        ),
        gameMode = listOf(SinglePlayer, CoOperative, Multiplayer).map(::FullObject).toImmutableSet(),
        playerPerspectives = persistentSetOf(FullObject(ThirdPerson)),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
