/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.game.AverageRating
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.domain.model.game.GameLocalizations
import ru.pixnews.domain.model.game.GameMode
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.GameReleaseCategory
import ru.pixnews.domain.model.game.GameReleaseStatus
import ru.pixnews.domain.model.game.GameSeriesSummary
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.domain.model.game.PlayerPerspective
import ru.pixnews.domain.model.game.RatingsSummary
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.rating.EsrbRating
import ru.pixnews.domain.model.rating.PegiRating
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.url.VideoUrl
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.game.baldursGate3
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbCompanyId
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameId
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameSeriesId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import java.time.Month.AUGUST

class IgdbGameConverterTest {
    @Suppress("LongMethod")
    @Test
    fun `toGame() should convert games`() {
        val source = IgdbGameFixtures.baldursGate3

        val result = source.toGame()

        result.id shouldBe IgdbGameId(119171)
        result.name shouldBe Localized(
            value = "Baldur's Gate 3",
            language = LanguageCode.ENGLISH,
        )
        result.summary shouldBe Localized(
            value = RichText(IgdbGameFixtures.baldursGate3.summary),
            language = LanguageCode.ENGLISH,
        )
        result.description shouldBe Localized(
            value = RichText(IgdbGameFixtures.baldursGate3.storyline),
            language = LanguageCode.ENGLISH,
        )
        result.videoUrls.map(VideoUrl::getUrl) shouldBe listOf(
            "OcP0WdH7rTs",
            "jNY7AEQ59-8",
            "o5xpbAsly48",
            "tavPnYeFrV4",
            "Ks-KF9iueE0",
            "zTX79cgruXE",
            "t0uYhTLPGLQ",
            "XuCfkgaaa08",
        ).map { "https://www.youtube.com/watch?v=$it" }
        result.screenshots.map(ImageUrl::getUrl) shouldContainInOrder listOf(
            "co670h",
            "sc81fe",
            "sc81ff",
            "sc81fg",
            "sc81fh",
            "sc81fi",
            "sc81fj",
            "sc81fk",
            "sc81fl",
            "sc81fm",
            "sc81fn",
        ).map { "https://images.igdb.com/igdb/image/upload/t_thumb/$it.webp" }
        val larianStudioCompany = Company(
            id = IgdbCompanyId(510),
            name = "Larian Studios",
            description = Localized.EMPTY_RICH_TEXT,
            avatar = null,
            foundingDate = null,
            country = null,
            parentCompany = null,
            dataSources = persistentListOf(igdbDataSource),
            links = persistentListOf(),
        )
        result.developer shouldBe larianStudioCompany
        result.publisher shouldBe larianStudioCompany
        result.releaseDate shouldBe ApproximateDate.YearMonthDay(2023, AUGUST, 3)
        result.releaseStatus shouldBe GameReleaseStatus.RELEASED
        result.genres.shouldContainExactlyInAnyOrder(
            listOf(
                "Role-playing (RPG)",
                "Strategy",
                "Turn-based strategy (TBS)",
                "Tactical",
                "Adventure",
            ).map(::GameGenre),
        )
        result.tags.shouldContainExactlyInAnyOrder(
            GameTag("Action"),
            GameTag("Fantasy"),
        )
        result.ratings shouldBe RatingsSummary(
            gameId = IgdbGameId(119171),
            numberOfVotes = 50U,
            averageRating = AverageRating(8.84f),
            distribution = null,
        )
        result.category shouldBe GameReleaseCategory.MainGame
        result.parentGame shouldBe null
        result.series shouldBe FullObject(
            GameSeriesSummary(
                id = IgdbGameSeriesId(7),
                name = Localized(value = "Baldur's Gate", language = LanguageCode.ENGLISH),
                totalGamesCount = 16U,
                games = sequenceOf(
                    5L,
                    6,
                    81,
                    82,
                    83,
                    84,
                    1880,
                    5613,
                    14292,
                    21813,
                    52617,
                    91241,
                    119171,
                    124786,
                    210699,
                    243015,
                ).map { Ref.Id(IgdbGameId(it)) }.toPersistentList(),
                attribution = persistentListOf(igdbDataSource),
            ),
        )
        result.platforms.shouldContainExactlyInAnyOrder(
            FullObject(GamePlatform.Windows),
            FullObject(GamePlatform.Macos),
            FullObject(GamePlatform.PlayStation5),
            FullObject(GamePlatform.XboxSeriesXs),
            FullObject(GamePlatform.Other(name = "Stadia")),
        )
        result.ageRanking shouldBe AgeRating(
            esrbRating = EsrbRating.MATURE_17PLUS,
            pegiRating = PegiRating.PEGI_18,
        )
        result.localizations shouldBe GameLocalizations(
            sound = persistentSetOf(
                LanguageCode.ENGLISH,
            ),
            text = persistentSetOf(
                LanguageCode.ENGLISH,
                LanguageCode.FRENCH,
                LanguageCode.GERMAN,
                LanguageCode.SPANISH,
                LanguageCode.POLISH,
                LanguageCode.RUSSIAN,
                LanguageCode.CHINESE,
                LanguageCode.TURKISH,
                LanguageCode.PORTUGUESE,
                LanguageCode.ITALIAN,
                LanguageCode.from("ukr"),
            ),
        )
        result.gameMode.shouldContainExactlyInAnyOrder(
            FullObject(GameMode.SinglePlayer),
            FullObject(GameMode.Multiplayer),
            FullObject(GameMode.CoOperative),
        )
        result.playerPerspectives.shouldContainExactlyInAnyOrder(
            FullObject(PlayerPerspective.Isometric),
        )
        result.systemRequirements shouldBe null
        result.dataSources shouldBe persistentListOf(igdbDataSource)
    }
}
