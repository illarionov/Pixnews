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
import ru.pixnews.domain.model.company.company.greatApeGames
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.ALPHA
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.domain.model.game.PlayerPerspective.FirstPerson
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.indie
import ru.pixnews.domain.model.game.shooter
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.links.ExternalLinkType.STEAM
import ru.pixnews.domain.model.links.ExternalLinkType.TWITCH
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

internal val theLostWildGameId = GameId("the-lost-wild")

public val GameFixtures.theLostWild: Game
    get() = Game(
        id = theLostWildGameId,
        name = Localized(
            value = "The Lost Wild",
            language = LanguageCode.ENGLISH,
        ),
        summary = Localized(
            value = RichText(
                """The Lost Wild is an upcoming immersive and cinematic dinosaur game that captures the reverence and
                | terror of nature's most magnificent animals. Our dinosaurs go well beyond simple monster
                | mechanics, with built-in self-preservation and reactive systemic AI behaviour.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(
            value = RichText(
                """Communicate with a mysterious voice over the radio. Use it to help navigate your
                |way through the facilities and find a way to escape. Hear a slow feed of dramatic points and
                |information that slowly builds a bigger picture and with it, increasing curiosity. Piece together the
                |history of the island, the events that lead to the situation you are in and face an emotional and
                |impactful dilemma at the conclusion.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        videoUrls = persistentListOf(
            DefaultVideoUrl("https://www.youtube.com/embed/fA1AkZn2PrE"),
            DefaultVideoUrl("https://www.youtube.com/embed/xjpWlBwHwek"),
        ),
        screenshots = persistentListOf(
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/co495n.webp",
                size = CanvasSize(2400U, 3200U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sccrmn.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/schyxb.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/schyxa.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/schyx9.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/schyx8.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/schyx7.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sceryo.webp",
                size = CanvasSize(1920U, 1080U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/sceryn.webp",
                size = CanvasSize(3200U, 1800U),
            ),
            DefaultImageUrl(
                rawUrl = "https://images.igdb.com/igdb/image/upload/t_original/scerym.webp",
                size = CanvasSize(3200U, 1800U),
            ),
        ),
        developer = CompanyFixtures.greatApeGames,
        publisher = null,
        releaseDate = ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
        releaseStatus = ALPHA,
        genres = persistentSetOf(
            GameGenreFixtures.adventure,
            GameGenreFixtures.indie,
            GameGenreFixtures.shooter,
        ),
        tags = persistentSetOf(
            GameTag("dinosaur"),
            GameTag("horror"),
            GameTag("survival"),
        ),
        ratings = RatingsSummary(
            gameId = theLostWildGameId,
            numberOfVotes = 0U,
            averageRating = null,
            distribution = null,
        ),
        links = persistentListOf(
            ExternalLink(OFFICIAL, Url("https://thelostwild.com/")),
            ExternalLink(STEAM, Url("https://store.steampowered.com/app/1952620/The_Lost_Wild")),
            ExternalLink(TWITTER, Url("https://twitter.com/greatapegames?s=21")),
            ExternalLink(YOUTUBE, Url("https://youtube.com/c/GreatApeGames")),
            ExternalLink(TWITCH, Url("https://www.twitch.tv/directory/game/The%20Lost%20Wild")),
        ),
        category = MainGame,
        parentGame = null,
        series = null,
        platforms = persistentSetOf(Windows),
        ageRanking = null,
        localizations = GameLocalizations(
            sound = persistentSetOf(
                LanguageCode.ENGLISH,
            ),
            text = persistentSetOf(
                LanguageCode.ENGLISH,
                LanguageCode.CHINESE,
                LanguageCode.SPANISH,
                LanguageCode.FRENCH,
                LanguageCode.ITALIAN,
                LanguageCode.JAPANESE,
                LanguageCode.KOREAN,
                LanguageCode.POLISH,
                LanguageCode.PORTUGUESE,
                LanguageCode.RUSSIAN,
                LanguageCode.TURKISH,
                LanguageCode.GERMAN,
            ),
        ),
        gameMode = persistentSetOf(),
        playerPerspectives = persistentSetOf(FirstPerson),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )