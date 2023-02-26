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
import kotlinx.collections.immutable.toImmutableList
import ru.pixnews.domain.model.company.CompanyFixtures
import ru.pixnews.domain.model.company.company.monomiPark
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.game.AverageRating
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GameGenreFixtures
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.game.GameMode.SinglePlayer
import ru.pixnews.domain.model.game.GamePlatform.Windows
import ru.pixnews.domain.model.game.GamePlatform.XboxOne
import ru.pixnews.domain.model.game.GamePlatform.XboxSeriesXs
import ru.pixnews.domain.model.game.GameReleaseCategory.MainGame
import ru.pixnews.domain.model.game.GameReleaseStatus.EARLY_ACCESS
import ru.pixnews.domain.model.game.GameSeriesSummaryFixtures
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.domain.model.game.PlayerPerspective.FirstPerson
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.game.VotesDistribution
import ru.pixnews.domain.model.game.adventure
import ru.pixnews.domain.model.game.indie
import ru.pixnews.domain.model.game.shooter
import ru.pixnews.domain.model.game.simulator
import ru.pixnews.domain.model.game.slimeRancher
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.links.ExternalLinkType.DISCORD
import ru.pixnews.domain.model.links.ExternalLinkType.EPICGAMES_STORE
import ru.pixnews.domain.model.links.ExternalLinkType.FACEBOOK
import ru.pixnews.domain.model.links.ExternalLinkType.INSTAGRAM
import ru.pixnews.domain.model.links.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.links.ExternalLinkType.REDDIT
import ru.pixnews.domain.model.links.ExternalLinkType.STEAM
import ru.pixnews.domain.model.links.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.links.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.links.ExternalLinkType.YOUTUBE
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating.EVERYONE_10PLUS
import ru.pixnews.domain.model.rating.PegiRating.PEGI_7
import ru.pixnews.domain.model.util.ApproximateDate.ToBeDeterminedYear
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.DefaultImageUrl
import ru.pixnews.domain.model.util.DefaultVideoUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

private val slimeRancher2GameId = GameId("slime-rancher-2")

public val GameFixtures.slimeRancher2: Game
    get() = Game(
        id = slimeRancher2GameId,
        name = Localized(value = "Slime Rancher 2", language = LanguageCode.ENGLISH),
        summary = Localized(
            value = RichText(
                """Continue the adventures of Beatrix LeBeau as she journeys across the Slime Sea
                | to Rainbow Island, a land brimming with ancient mysteries, and bursting with wiggly, new slimes to
                | wrangle in this sequel to the smash-hit, Slime Rancher.
                """.trimMargin(),
            ),
            language = LanguageCode.ENGLISH,
        ),
        description = Localized(value = RichText(""), language = LanguageCode.ENGLISH),
        videoUrls = persistentListOf(
            DefaultVideoUrl("https://www.youtube.com/embed/0E_xgRMey1Q"),
            DefaultVideoUrl("https://www.youtube.com/embed/qPfm3IClc0s"),
        ),
        screenshots = (
                listOf(
                    DefaultImageUrl(
                        rawUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/co5bbt.png",
                        size = CanvasSize(width = 264U, height = 352U),
                    ),
                ) + listOf(
                    "https://images.igdb.com/igdb/image/upload/t_original/scahjc.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scahjb.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scahja.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scahj9.webp",
                    "https://images.igdb.com/igdb/image/upload/t_original/scahj8.webp",
                ).map {
                    DefaultImageUrl(
                        rawUrl = it,
                        size = CanvasSize(width = 1920U, height = 1080U),
                    )
                }
                ).toImmutableList(),
        developer = CompanyFixtures.monomiPark,
        publisher = CompanyFixtures.monomiPark,
        releaseDate = ToBeDeterminedYear(2024, Localized("", LanguageCode.ENGLISH)),
        releaseStatus = EARLY_ACCESS,
        genres = persistentSetOf(
            GameGenreFixtures.adventure,
            GameGenreFixtures.indie,
            GameGenreFixtures.shooter,
            GameGenreFixtures.simulator,
        ),
        tags = persistentSetOf(
            GameTag("building"),
            GameTag("colorful"),
            GameTag("relaxing"),
        ),
        ratings = RatingsSummary(
            gameId = slimeRancher2GameId,
            numberOfVotes = 5U,
            averageRating = AverageRating(6.7f),
            distribution = VotesDistribution(
                p1to2 = 0U,
                p3to4 = 0U,
                p5to6 = 3U,
                p7to8 = 1U,
                p9to10 = 1U,
            ),
        ),
        links = persistentListOf(
            ExternalLink(STEAM, Url("https://store.steampowered.com/app/1657630")),
            ExternalLink(EPICGAMES_STORE, Url("https://www.epicgames.com/p/slime-rancher-2")),
            ExternalLink(OFFICIAL, Url("http://www.slimerancher.com/")),
            ExternalLink(YOUTUBE, Url("https://www.youtube.com/channel/UCr4CsSQsFzaVTMR2HzPLUhw")),
            ExternalLink(FACEBOOK, Url("https://www.facebook.com/SlimeRancherGame/")),
            ExternalLink(TWITTER, Url("https://twitter.com/slimerancher")),
            ExternalLink(INSTAGRAM, Url("https://www.instagram.com/monomi_park")),
            ExternalLink(REDDIT, Url("https://www.reddit.com/r/slimerancher")),
            ExternalLink(DISCORD, Url("https://discord.gg/slimerancher")),
            ExternalLink(WIKIPEDIA, Url("https://en.wikipedia.org/wiki/Slime_Rancher_2")),
        ),
        category = MainGame,
        parentGame = null,
        series = GameSeriesSummaryFixtures.slimeRancher,
        platforms = persistentSetOf(XboxOne, Windows, XboxSeriesXs),
        ageRanking = AgeRating(
            gameId = slimeRancher2GameId,
            esrbRating = EVERYONE_10PLUS,
            pegiRating = PEGI_7,
        ),
        localizations = GameLocalizations(
            sound = persistentSetOf(LanguageCode.ENGLISH),
            text = persistentSetOf(
                LanguageCode.ENGLISH,
                LanguageCode.CHINESE,
                LanguageCode.SPANISH,
                LanguageCode.FRENCH,
                LanguageCode.JAPANESE,
                LanguageCode.KOREAN,
                LanguageCode.PORTUGUESE,
                LanguageCode.RUSSIAN,
                LanguageCode.GERMAN,
            ),
        ),
        gameMode = persistentSetOf(SinglePlayer),
        playerPerspectives = persistentSetOf(FirstPerson),
        systemRequirements = null,
        dataSources = DataSourceFixtures.igdb,
    )
