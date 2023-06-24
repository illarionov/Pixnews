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
@file:Suppress("TooManyFunctions")

package ru.pixnews.library.igdb

import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery.Companion.apicalypseQuery
import ru.pixnews.library.igdb.apicalypse.ApicalypseQueryBuilder
import ru.pixnews.library.igdb.dsl.IgdbClientConfig
import ru.pixnews.library.igdb.dsl.IgdbClientDsl
import ru.pixnews.library.igdb.internal.okhttp.OkhttpIgdbClientFactory
import ru.pixnews.library.igdb.model.AgeRatingContentDescriptionResult
import ru.pixnews.library.igdb.model.AgeRatingResult
import ru.pixnews.library.igdb.model.AlternativeNameResult
import ru.pixnews.library.igdb.model.ArtworkResult
import ru.pixnews.library.igdb.model.CharacterMugShotResult
import ru.pixnews.library.igdb.model.CharacterResult
import ru.pixnews.library.igdb.model.CollectionResult
import ru.pixnews.library.igdb.model.CompanyLogoResult
import ru.pixnews.library.igdb.model.CompanyResult
import ru.pixnews.library.igdb.model.CompanyWebsiteResult
import ru.pixnews.library.igdb.model.CoverResult
import ru.pixnews.library.igdb.model.ExternalGameResult
import ru.pixnews.library.igdb.model.FranchiseResult
import ru.pixnews.library.igdb.model.GameEngineLogoResult
import ru.pixnews.library.igdb.model.GameEngineResult
import ru.pixnews.library.igdb.model.GameLocalizationResult
import ru.pixnews.library.igdb.model.GameModeResult
import ru.pixnews.library.igdb.model.GameResult
import ru.pixnews.library.igdb.model.GameVersionFeatureResult
import ru.pixnews.library.igdb.model.GameVersionFeatureValueResult
import ru.pixnews.library.igdb.model.GameVersionResult
import ru.pixnews.library.igdb.model.GameVideoResult
import ru.pixnews.library.igdb.model.GenreResult
import ru.pixnews.library.igdb.model.InvolvedCompanyResult
import ru.pixnews.library.igdb.model.KeywordResult
import ru.pixnews.library.igdb.model.LanguageResult
import ru.pixnews.library.igdb.model.LanguageSupportResult
import ru.pixnews.library.igdb.model.LanguageSupportTypeResult
import ru.pixnews.library.igdb.model.MultiplayerModeResult
import ru.pixnews.library.igdb.model.PlatformFamilyResult
import ru.pixnews.library.igdb.model.PlatformLogoResult
import ru.pixnews.library.igdb.model.PlatformResult
import ru.pixnews.library.igdb.model.PlatformVersionCompany
import ru.pixnews.library.igdb.model.PlatformVersionReleaseDateResult
import ru.pixnews.library.igdb.model.PlatformVersionResult
import ru.pixnews.library.igdb.model.PlatformWebsiteResult
import ru.pixnews.library.igdb.model.PlayerPerspectiveResult
import ru.pixnews.library.igdb.model.RegionResult
import ru.pixnews.library.igdb.model.ReleaseDateResult
import ru.pixnews.library.igdb.model.ReleaseDateStatusResult
import ru.pixnews.library.igdb.model.ScreenshotResult
import ru.pixnews.library.igdb.model.SearchResult
import ru.pixnews.library.igdb.model.ThemeResult
import ru.pixnews.library.igdb.model.WebsiteResult

/**
 * Creates a [IgdbClient] with the specified [block] configuration.
 *
 * Sample:
 * ```kotlin
 * val client = IgdbClient {
 *     httpClient {
 *         callFactory = <okhttp call.factory>
 *     }
 *
 *     twitchAuth {
 *         clientId = <Twitch OAuth app Client ID>
 *         clientSecret = <Twitch OAuth app Client Secret>
 *     }
 * }
 * ```
 */
@IgdbClientDsl
public fun IgdbClient(
    block: IgdbClientConfig.() -> Unit,
): IgdbClient {
    val config = IgdbClientConfig().apply(block)
    return OkhttpIgdbClientFactory(config).build()
}

/**
 * IGDB Client which allows you to call the [IGDB API](https://api-docs.igdb.com/#endpoints) methods.
 */
public interface IgdbClient {
    /**
     * Age Rating according to various rating organisations
     */
    public suspend fun ageRating(query: ApicalypseQuery): AgeRatingResult

    /**
     * Age Rating Descriptors
     */
    public suspend fun ageRatingContentDescription(query: ApicalypseQuery): AgeRatingContentDescriptionResult

    /**
     * Alternative and international game titles
     */
    public suspend fun alternativeName(query: ApicalypseQuery): AlternativeNameResult

    /**
     * Official artworks (resolution and aspect ratio may vary)
     */
    public suspend fun artwork(query: ApicalypseQuery): ArtworkResult

    /**
     * Video game characters
     */
    public suspend fun character(query: ApicalypseQuery): CharacterResult

    /**
     * Images depicting game characters
     */
    public suspend fun characterMugShot(query: ApicalypseQuery): CharacterMugShotResult

    /**
     * Collection, AKA Series
     */
    public suspend fun collection(query: ApicalypseQuery): CollectionResult

    /**
     * Video game companies. Both publishers & developers
     */
    public suspend fun company(query: ApicalypseQuery): CompanyResult

    /**
     * The logos of developers and publishers
     */
    public suspend fun companyLogo(query: ApicalypseQuery): CompanyLogoResult

    /**
     * Company Website
     */
    public suspend fun companyWebsite(query: ApicalypseQuery): CompanyWebsiteResult

    /**
     * The cover art of games
     */
    public suspend fun cover(query: ApicalypseQuery): CoverResult

    /**
     * Game IDs on other services
     */
    public suspend fun externalGame(query: ApicalypseQuery): ExternalGameResult

    /**
     * A list of video game franchises such as Star Wars.
     */
    public suspend fun franchise(query: ApicalypseQuery): FranchiseResult

    /**
     * Video game engines such as unreal engine.
     */
    public suspend fun gameEngine(query: ApicalypseQuery): GameEngineResult

    /**
     * The logos of game engines
     */
    public suspend fun gameEngineLogo(query: ApicalypseQuery): GameEngineLogoResult

    /**
     * Video Games!
     */
    public suspend fun game(query: ApicalypseQuery): GameResult

    /**
     * Game localization for a game
     */
    public suspend fun gameLocalization(query: ApicalypseQuery): GameLocalizationResult

    /**
     * Single player, Multiplayer etc
     */
    public suspend fun gameMode(query: ApicalypseQuery): GameModeResult

    /**
     * Details about game editions and versions
     */
    public suspend fun gameVersion(query: ApicalypseQuery): GameVersionResult

    /**
     * Features and descriptions of what makes each version/edition different from the main game
     */
    public suspend fun gameVersionFeature(query: ApicalypseQuery): GameVersionFeatureResult

    /**
     * A video associated with a game
     */
    public suspend fun gameVideo(query: ApicalypseQuery): GameVideoResult

    /**
     * The bool/text value of the feature
     */
    public suspend fun gameVersionFeatureValue(query: ApicalypseQuery): GameVersionFeatureValueResult

    /**
     * Genres of video game
     */
    public suspend fun genre(query: ApicalypseQuery): GenreResult

    /**
     * Involved Company
     */
    public suspend fun involvedCompany(query: ApicalypseQuery): InvolvedCompanyResult

    /**
     * Languages that are used in the Language Support endpoint
     */
    public suspend fun language(query: ApicalypseQuery): LanguageResult

    /**
     * Keywords are words or phrases that get tagged to a game such as “world war 2” or “steampunk”
     */
    public suspend fun keyword(query: ApicalypseQuery): KeywordResult

    /**
     * Games can be played with different languages for voice acting, subtitles, or the interface language.
     */
    public suspend fun languageSupport(query: ApicalypseQuery): LanguageSupportResult

    /**
     * Language Support Types contains the identifiers for the support types that Language Support uses.
     */
    public suspend fun languageSupportType(query: ApicalypseQuery): LanguageSupportTypeResult

    /**
     * Data about the supported multiplayer types
     */
    public suspend fun multiplayerMode(query: ApicalypseQuery): MultiplayerModeResult

    /**
     * The hardware used to run the game or game delivery network
     */
    public suspend fun platform(query: ApicalypseQuery): PlatformResult

    /**
     * Platform Version
     */
    public suspend fun platformVersion(query: ApicalypseQuery): PlatformVersionResult

    /**
     * A collection of closely related platforms
     */
    public suspend fun platformFamily(query: ApicalypseQuery): PlatformFamilyResult

    /**
     * A platform developer
     */
    public suspend fun platformVersionCompany(query: ApicalypseQuery): PlatformVersionCompany

    /**
     * A handy endpoint that extends platform release dates. Used to dig deeper into release dates, platforms and
     * versions
     */
    public suspend fun platformVersionReleaseDate(query: ApicalypseQuery): PlatformVersionReleaseDateResult

    /**
     * The main website for the platform
     */
    public suspend fun platformWebsite(query: ApicalypseQuery): PlatformWebsiteResult

    /**
     * Logo for a platform
     */
    public suspend fun platformLogo(query: ApicalypseQuery): PlatformLogoResult

    /**
     * Player perspectives describe the view/perspective of the player in a video game
     */
    public suspend fun playerPerspective(query: ApicalypseQuery): PlayerPerspectiveResult

    /**
     * Region for game localization
     */
    public suspend fun region(query: ApicalypseQuery): RegionResult

    /**
     * A handy endpoint that extends game release dates. Used to dig deeper into release dates, platforms and versions
     */
    public suspend fun releaseDate(query: ApicalypseQuery): ReleaseDateResult

    /**
     * An endpoint to provide definition of all of the current release date statuses
     */
    public suspend fun releaseDateStatus(query: ApicalypseQuery): ReleaseDateStatusResult

    /**
     * Screenshots of games
     */
    public suspend fun screenshot(query: ApicalypseQuery): ScreenshotResult

    /**
     * Search
     */
    public suspend fun search(query: ApicalypseQuery): SearchResult

    /**
     * Video game themes
     */
    public suspend fun theme(query: ApicalypseQuery): ThemeResult

    /**
     * A website url, usually associated with a game
     */
    public suspend fun website(query: ApicalypseQuery): WebsiteResult
}

/**
 * Age Rating according to various rating organisations
 */
public suspend fun IgdbClient.ageRating(builder: ApicalypseQueryBuilder.() -> Unit): AgeRatingResult =
    ageRating(apicalypseQuery(builder))

/**
 * Age Rating Descriptors
 */
public suspend fun IgdbClient.ageRatingContentDescription(
    builder: ApicalypseQueryBuilder.() -> Unit,
): AgeRatingContentDescriptionResult = ageRatingContentDescription(apicalypseQuery(builder))

/**
 * Alternative and international game titles
 */
public suspend fun IgdbClient.alternativeName(builder: ApicalypseQueryBuilder.() -> Unit): AlternativeNameResult =
    alternativeName(apicalypseQuery(builder))

/**
 * Official artworks (resolution and aspect ratio may vary)
 */
public suspend fun IgdbClient.artwork(builder: ApicalypseQueryBuilder.() -> Unit): ArtworkResult =
    artwork(apicalypseQuery(builder))

/**
 * Video game characters
 */
public suspend fun IgdbClient.character(builder: ApicalypseQueryBuilder.() -> Unit): CharacterResult =
    character(apicalypseQuery(builder))

/**
 * Images depicting game characters
 */
public suspend fun IgdbClient.characterMugShot(builder: ApicalypseQueryBuilder.() -> Unit): CharacterMugShotResult =
    characterMugShot(apicalypseQuery(builder))

/**
 * Collection, AKA Series
 */
public suspend fun IgdbClient.collection(builder: ApicalypseQueryBuilder.() -> Unit): CollectionResult =
    collection(apicalypseQuery(builder))

/**
 * Video game companies. Both publishers & developers
 */
public suspend fun IgdbClient.company(builder: ApicalypseQueryBuilder.() -> Unit): CompanyResult =
    company(apicalypseQuery(builder))

/**
 * The logos of developers and publishers
 */
public suspend fun IgdbClient.companyLogo(builder: ApicalypseQueryBuilder.() -> Unit): CompanyLogoResult =
    companyLogo(apicalypseQuery(builder))

/**
 * Company Website
 */
public suspend fun IgdbClient.companyWebsite(builder: ApicalypseQueryBuilder.() -> Unit): CompanyWebsiteResult =
    companyWebsite(apicalypseQuery(builder))

/**
 * The cover art of games
 */
public suspend fun IgdbClient.cover(builder: ApicalypseQueryBuilder.() -> Unit): CoverResult =
    cover(apicalypseQuery(builder))

/**
 * Game IDs on other services
 */
public suspend fun IgdbClient.externalGame(builder: ApicalypseQueryBuilder.() -> Unit): ExternalGameResult =
    externalGame(apicalypseQuery(builder))

/**
 * A list of video game franchises such as Star Wars.
 */
public suspend fun IgdbClient.franchise(builder: ApicalypseQueryBuilder.() -> Unit): FranchiseResult =
    franchise(apicalypseQuery(builder))

/**
 * Video game engines such as unreal engine.
 */
public suspend fun IgdbClient.gameEngine(builder: ApicalypseQueryBuilder.() -> Unit): GameEngineResult =
    gameEngine(apicalypseQuery(builder))

/**
 * The logos of game engines
 */
public suspend fun IgdbClient.gameEngineLogo(builder: ApicalypseQueryBuilder.() -> Unit): GameEngineLogoResult =
    gameEngineLogo(apicalypseQuery(builder))

/**
 * Video Games!
 */
public suspend fun IgdbClient.game(builder: ApicalypseQueryBuilder.() -> Unit): GameResult =
    game(apicalypseQuery(builder))

/**
 * Game localization for a game
 */
public suspend fun IgdbClient.gameLocalization(builder: ApicalypseQueryBuilder.() -> Unit): GameLocalizationResult =
    gameLocalization(apicalypseQuery(builder))

/**
 * Single player, Multiplayer etc
 */
public suspend fun IgdbClient.gameMode(builder: ApicalypseQueryBuilder.() -> Unit): GameModeResult =
    gameMode(apicalypseQuery(builder))

/**
 * Details about game editions and versions
 */
public suspend fun IgdbClient.gameVersion(builder: ApicalypseQueryBuilder.() -> Unit): GameVersionResult =
    gameVersion(apicalypseQuery(builder))

/**
 * Features and descriptions of what makes each version/edition different from the main game
 */
public suspend fun IgdbClient.gameVersionFeature(
    builder: ApicalypseQueryBuilder.() -> Unit,
): GameVersionFeatureResult = gameVersionFeature(apicalypseQuery(builder))

/**
 * A video associated with a game
 */
public suspend fun IgdbClient.gameVideo(builder: ApicalypseQueryBuilder.() -> Unit): GameVideoResult =
    gameVideo(apicalypseQuery(builder))

/**
 * The bool/text value of the feature
 */
public suspend fun IgdbClient.gameVersionFeatureValue(
    builder: ApicalypseQueryBuilder.() -> Unit,
): GameVersionFeatureValueResult = gameVersionFeatureValue(apicalypseQuery(builder))

/**
 * Genres of video game
 */
public suspend fun IgdbClient.genre(builder: ApicalypseQueryBuilder.() -> Unit): GenreResult =
    genre(apicalypseQuery(builder))

/**
 * Involved Company
 */
public suspend fun IgdbClient.involvedCompany(builder: ApicalypseQueryBuilder.() -> Unit): InvolvedCompanyResult =
    involvedCompany(apicalypseQuery(builder))

/**
 * Languages that are used in the Language Support endpoint
 */
public suspend fun IgdbClient.language(builder: ApicalypseQueryBuilder.() -> Unit): LanguageResult =
    language(apicalypseQuery(builder))

/**
 * Keywords are words or phrases that get tagged to a game such as “world war 2” or “steampunk”
 */
public suspend fun IgdbClient.keyword(builder: ApicalypseQueryBuilder.() -> Unit): KeywordResult =
    keyword(apicalypseQuery(builder))

/**
 * Games can be played with different languages for voice acting, subtitles, or the interface language.
 */
public suspend fun IgdbClient.languageSupport(builder: ApicalypseQueryBuilder.() -> Unit): LanguageSupportResult =
    languageSupport(apicalypseQuery(builder))

/**
 * Language Support Types contains the identifiers for the support types that Language Support uses.
 */
public suspend fun IgdbClient.languageSupportType(
    builder: ApicalypseQueryBuilder.() -> Unit,
): LanguageSupportTypeResult = languageSupportType(apicalypseQuery(builder))

/**
 * Data about the supported multiplayer types
 */
public suspend fun IgdbClient.multiplayerMode(builder: ApicalypseQueryBuilder.() -> Unit): MultiplayerModeResult =
    multiplayerMode(apicalypseQuery(builder))

/**
 * The hardware used to run the game or game delivery network
 */
public suspend fun IgdbClient.platform(builder: ApicalypseQueryBuilder.() -> Unit): PlatformResult =
    platform(apicalypseQuery(builder))

/**
 * Platform Version
 */
public suspend fun IgdbClient.platformVersion(builder: ApicalypseQueryBuilder.() -> Unit): PlatformVersionResult =
    platformVersion(apicalypseQuery(builder))

/**
 * A collection of closely related platforms
 */
public suspend fun IgdbClient.platformFamily(builder: ApicalypseQueryBuilder.() -> Unit): PlatformFamilyResult =
    platformFamily(apicalypseQuery(builder))

/**
 * A platform developer
 */
public suspend fun IgdbClient.platformVersionCompany(
    builder: ApicalypseQueryBuilder.() -> Unit,
): PlatformVersionCompany = platformVersionCompany(apicalypseQuery(builder))

/**
 * A handy endpoint that extends platform release dates. Used to dig deeper into release dates, platforms and
 * versions
 */
public suspend fun IgdbClient.platformVersionReleaseDate(
    builder: ApicalypseQueryBuilder.() -> Unit,
): PlatformVersionReleaseDateResult = platformVersionReleaseDate(apicalypseQuery(builder))

/**
 * The main website for the platform
 */
public suspend fun IgdbClient.platformWebsite(builder: ApicalypseQueryBuilder.() -> Unit): PlatformWebsiteResult =
    platformWebsite(apicalypseQuery(builder))

/**
 * Logo for a platform
 */
public suspend fun IgdbClient.platformLogo(builder: ApicalypseQueryBuilder.() -> Unit): PlatformLogoResult =
    platformLogo(apicalypseQuery(builder))

/**
 * Player perspectives describe the view/perspective of the player in a video game
 */
public suspend fun IgdbClient.playerPerspective(builder: ApicalypseQueryBuilder.() -> Unit): PlayerPerspectiveResult =
    playerPerspective(apicalypseQuery(builder))

/**
 * Region for game localization
 */
public suspend fun IgdbClient.region(builder: ApicalypseQueryBuilder.() -> Unit): RegionResult =
    region(apicalypseQuery(builder))

/**
 * A handy endpoint that extends game release dates. Used to dig deeper into release dates, platforms and versions
 */
public suspend fun IgdbClient.releaseDate(builder: ApicalypseQueryBuilder.() -> Unit): ReleaseDateResult =
    releaseDate(apicalypseQuery(builder))

/**
 * An endpoint to provide definition of all of the current release date statuses
 */
public suspend fun IgdbClient.releaseDateStatus(builder: ApicalypseQueryBuilder.() -> Unit): ReleaseDateStatusResult =
    releaseDateStatus(apicalypseQuery(builder))

/**
 * Screenshots of games
 */
public suspend fun IgdbClient.screenshot(builder: ApicalypseQueryBuilder.() -> Unit): ScreenshotResult =
    screenshot(apicalypseQuery(builder))

/**
 * Search
 */
public suspend fun IgdbClient.search(builder: ApicalypseQueryBuilder.() -> Unit): SearchResult =
    search(apicalypseQuery(builder))

/**
 * Video game themes
 */
public suspend fun IgdbClient.theme(builder: ApicalypseQueryBuilder.() -> Unit): ThemeResult =
    theme(apicalypseQuery(builder))

/**
 * A website url, usually associated with a game
 */
public suspend fun IgdbClient.website(builder: ApicalypseQueryBuilder.() -> Unit): WebsiteResult =
    website(apicalypseQuery(builder))
