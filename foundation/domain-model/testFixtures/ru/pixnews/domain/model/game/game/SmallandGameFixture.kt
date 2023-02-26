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
import ru.pixnews.domain.model.company.company.mergeGames
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
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
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.DISCORD
import ru.pixnews.domain.model.links.ExternalLinkType.EPICGAMES_STORE
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.links.ExternalLinkType.STEAM
import ru.pixnews.domain.model.links.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.links.ExternalLinkType.YOUTUBE
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.DefaultVideoUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

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
        releaseDate = ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
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
        platforms = persistentSetOf(Windows),
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
        gameMode = persistentSetOf(SinglePlayer, CoOperative, Multiplayer),
        playerPerspectives = persistentSetOf(ThirdPerson),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
