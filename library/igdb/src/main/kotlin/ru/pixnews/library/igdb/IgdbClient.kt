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

import ru.pixnews.library.igdb.IgdbResult.Failure.ApiFailure
import ru.pixnews.library.igdb.IgdbResult.Failure.HttpFailure
import ru.pixnews.library.igdb.IgdbResult.Failure.NetworkFailure
import ru.pixnews.library.igdb.IgdbResult.Failure.UnknownFailure
import ru.pixnews.library.igdb.IgdbResult.Failure.UnknownHttpCodeFailure
import ru.pixnews.library.igdb.IgdbResult.Success
import ru.pixnews.library.igdb.apicalypse.ApicalypseMultiQuery.Companion.apicalypseMultiQuery
import ru.pixnews.library.igdb.apicalypse.ApicalypseMultiQueryBuilder
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery.Companion.apicalypseQuery
import ru.pixnews.library.igdb.apicalypse.ApicalypseQueryBuilder
import ru.pixnews.library.igdb.dsl.IgdbClientConfig
import ru.pixnews.library.igdb.dsl.IgdbClientDsl
import ru.pixnews.library.igdb.error.IgdbApiFailureException
import ru.pixnews.library.igdb.error.IgdbException
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import ru.pixnews.library.igdb.error.IgdbHttpException
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
import ru.pixnews.library.igdb.model.PlatformVersionCompanyResult
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
import ru.pixnews.library.igdb.model.UnpackedMultiQueryResult
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
    public val webhookApi: IgdbWebhookApi

    /**
     * General method for making requests to the igdb server.
     *
     * For the given [endpoint] and [query], executes a network request and returns the result or error
     * as an [IgdbResult] object
     *
     */
    public suspend fun <T : Any> execute(
        endpoint: IgdbEndpoint<T>,
        query: ApicalypseQuery,
    ): IgdbResult<T, IgdbHttpErrorResponse>
}

/**
 * Age Rating according to various rating organisations
 */
public suspend fun IgdbClient.ageRating(builder: ApicalypseQueryBuilder.() -> Unit): AgeRatingResult =
    executeOrThrow(IgdbEndpoint.AGE_RATING, apicalypseQuery(builder))

/**
 * Age Rating Descriptors
 */
public suspend fun IgdbClient.ageRatingContentDescription(
    builder: ApicalypseQueryBuilder.() -> Unit,
): AgeRatingContentDescriptionResult =
    executeOrThrow(IgdbEndpoint.AGE_RATING_CONTENT_DESCRIPTION, apicalypseQuery(builder))

/**
 * Alternative and international game titles
 */
public suspend fun IgdbClient.alternativeName(builder: ApicalypseQueryBuilder.() -> Unit): AlternativeNameResult =
    executeOrThrow(IgdbEndpoint.ALTERNATIVE_NAME, apicalypseQuery(builder))

/**
 * Official artworks (resolution and aspect ratio may vary)
 */
public suspend fun IgdbClient.artwork(builder: ApicalypseQueryBuilder.() -> Unit): ArtworkResult =
    executeOrThrow(IgdbEndpoint.ARTWORK, apicalypseQuery(builder))

/**
 * Video game characters
 */
public suspend fun IgdbClient.character(builder: ApicalypseQueryBuilder.() -> Unit): CharacterResult =
    executeOrThrow(IgdbEndpoint.CHARACTER, apicalypseQuery(builder))

/**
 * Images depicting game characters
 */
public suspend fun IgdbClient.characterMugShot(builder: ApicalypseQueryBuilder.() -> Unit): CharacterMugShotResult =
    executeOrThrow(IgdbEndpoint.CHARACTER_MUG_SHOT, apicalypseQuery(builder))

/**
 * Collection, AKA Series
 */
public suspend fun IgdbClient.collection(builder: ApicalypseQueryBuilder.() -> Unit): CollectionResult =
    executeOrThrow(IgdbEndpoint.COLLECTION, apicalypseQuery(builder))

/**
 * Video game companies. Both publishers & developers
 */
public suspend fun IgdbClient.company(builder: ApicalypseQueryBuilder.() -> Unit): CompanyResult =
    executeOrThrow(IgdbEndpoint.COMPANY, apicalypseQuery(builder))

/**
 * The logos of developers and publishers
 */
public suspend fun IgdbClient.companyLogo(builder: ApicalypseQueryBuilder.() -> Unit): CompanyLogoResult =
    executeOrThrow(IgdbEndpoint.COMPANY_LOGO, apicalypseQuery(builder))

/**
 * Company Website
 */
public suspend fun IgdbClient.companyWebsite(builder: ApicalypseQueryBuilder.() -> Unit): CompanyWebsiteResult =
    executeOrThrow(IgdbEndpoint.COMPANY_WEBSITE, apicalypseQuery(builder))

/**
 * The cover art of games
 */
public suspend fun IgdbClient.cover(builder: ApicalypseQueryBuilder.() -> Unit): CoverResult =
    executeOrThrow(IgdbEndpoint.COVER, apicalypseQuery(builder))

/**
 * Game IDs on other services
 */
public suspend fun IgdbClient.externalGame(builder: ApicalypseQueryBuilder.() -> Unit): ExternalGameResult =
    executeOrThrow(IgdbEndpoint.EXTERNAL_GAME, apicalypseQuery(builder))

/**
 * A list of video game franchises such as Star Wars.
 */
public suspend fun IgdbClient.franchise(builder: ApicalypseQueryBuilder.() -> Unit): FranchiseResult =
    executeOrThrow(IgdbEndpoint.FRANCHISE, apicalypseQuery(builder))

/**
 * Video game engines such as unreal engine.
 */
public suspend fun IgdbClient.gameEngine(builder: ApicalypseQueryBuilder.() -> Unit): GameEngineResult =
    executeOrThrow(IgdbEndpoint.GAME_ENGINE, apicalypseQuery(builder))

/**
 * The logos of game engines
 */
public suspend fun IgdbClient.gameEngineLogo(builder: ApicalypseQueryBuilder.() -> Unit): GameEngineLogoResult =
    executeOrThrow(IgdbEndpoint.GAME_ENGINE_LOGO, apicalypseQuery(builder))

/**
 * Video Games!
 */
public suspend fun IgdbClient.game(builder: ApicalypseQueryBuilder.() -> Unit): GameResult =
    executeOrThrow(IgdbEndpoint.GAME, apicalypseQuery(builder))

/**
 * Game localization for a game
 */
public suspend fun IgdbClient.gameLocalization(builder: ApicalypseQueryBuilder.() -> Unit): GameLocalizationResult =
    executeOrThrow(IgdbEndpoint.GAME_LOCALIZATION, apicalypseQuery(builder))

/**
 * Single player, Multiplayer etc
 */
public suspend fun IgdbClient.gameMode(builder: ApicalypseQueryBuilder.() -> Unit): GameModeResult =
    executeOrThrow(IgdbEndpoint.GAME_MODE, apicalypseQuery(builder))

/**
 * Details about game editions and versions
 */
public suspend fun IgdbClient.gameVersion(builder: ApicalypseQueryBuilder.() -> Unit): GameVersionResult =
    executeOrThrow(IgdbEndpoint.GAME_VERSION, apicalypseQuery(builder))

/**
 * Features and descriptions of what makes each version/edition different from the main game
 */
public suspend fun IgdbClient.gameVersionFeature(
    builder: ApicalypseQueryBuilder.() -> Unit,
): GameVersionFeatureResult = executeOrThrow(IgdbEndpoint.GAME_VERSION_FEATURE, apicalypseQuery(builder))

/**
 * A video associated with a game
 */
public suspend fun IgdbClient.gameVideo(builder: ApicalypseQueryBuilder.() -> Unit): GameVideoResult =
    executeOrThrow(IgdbEndpoint.GAME_VIDEO, apicalypseQuery(builder))

/**
 * The bool/text value of the feature
 */
public suspend fun IgdbClient.gameVersionFeatureValue(
    builder: ApicalypseQueryBuilder.() -> Unit,
): GameVersionFeatureValueResult = executeOrThrow(IgdbEndpoint.GAME_VERSION_FEATURE_VALUE, apicalypseQuery(builder))

/**
 * Genres of video game
 */
public suspend fun IgdbClient.genre(builder: ApicalypseQueryBuilder.() -> Unit): GenreResult =
    executeOrThrow(IgdbEndpoint.GENRE, apicalypseQuery(builder))

/**
 * Involved Company
 */
public suspend fun IgdbClient.involvedCompany(builder: ApicalypseQueryBuilder.() -> Unit): InvolvedCompanyResult =
    executeOrThrow(IgdbEndpoint.INVOLVED_COMPANY, apicalypseQuery(builder))

/**
 * Languages that are used in the Language Support endpoint
 */
public suspend fun IgdbClient.language(builder: ApicalypseQueryBuilder.() -> Unit): LanguageResult =
    executeOrThrow(IgdbEndpoint.LANGUAGE, apicalypseQuery(builder))

/**
 * Keywords are words or phrases that get tagged to a game such as “world war 2” or “steampunk”
 */
public suspend fun IgdbClient.keyword(builder: ApicalypseQueryBuilder.() -> Unit): KeywordResult =
    executeOrThrow(IgdbEndpoint.KEYWORD, apicalypseQuery(builder))

/**
 * Games can be played with different languages for voice acting, subtitles, or the interface language.
 */
public suspend fun IgdbClient.languageSupport(builder: ApicalypseQueryBuilder.() -> Unit): LanguageSupportResult =
    executeOrThrow(IgdbEndpoint.LANGUAGE_SUPPORT, apicalypseQuery(builder))

/**
 * Language Support Types contains the identifiers for the support types that Language Support uses.
 */
public suspend fun IgdbClient.languageSupportType(
    builder: ApicalypseQueryBuilder.() -> Unit,
): LanguageSupportTypeResult = executeOrThrow(IgdbEndpoint.LANGUAGE_SUPPORT_TYPE, apicalypseQuery(builder))

/**
 * Data about the supported multiplayer types
 */
public suspend fun IgdbClient.multiplayerMode(builder: ApicalypseQueryBuilder.() -> Unit): MultiplayerModeResult =
    executeOrThrow(IgdbEndpoint.MULTIPLAYER_MODE, apicalypseQuery(builder))

/**
 * Allows you to execute multiple queries at once
 */
public suspend fun IgdbClient.multiquery(
    builder: ApicalypseMultiQueryBuilder.() -> Unit,
): List<UnpackedMultiQueryResult<*>> {
    return executeOrThrow(IgdbEndpoint.MULTIQUERY, apicalypseMultiQuery(builder))
}

/**
 * The hardware used to run the game or game delivery network
 */
public suspend fun IgdbClient.platform(builder: ApicalypseQueryBuilder.() -> Unit): PlatformResult =
    executeOrThrow(IgdbEndpoint.PLATFORM, apicalypseQuery(builder))

/**
 * Platform Version
 */
public suspend fun IgdbClient.platformVersion(builder: ApicalypseQueryBuilder.() -> Unit): PlatformVersionResult =
    executeOrThrow(IgdbEndpoint.PLATFORM_VERSION, apicalypseQuery(builder))

/**
 * A collection of closely related platforms
 */
public suspend fun IgdbClient.platformFamily(builder: ApicalypseQueryBuilder.() -> Unit): PlatformFamilyResult =
    executeOrThrow(IgdbEndpoint.PLATFORM_FAMILY, apicalypseQuery(builder))

/**
 * A platform developer
 */
public suspend fun IgdbClient.platformVersionCompany(
    builder: ApicalypseQueryBuilder.() -> Unit,
): PlatformVersionCompanyResult = executeOrThrow(IgdbEndpoint.PLATFORM_VERSION_COMPANY, apicalypseQuery(builder))

/**
 * A handy endpoint that extends platform release dates. Used to dig deeper into release dates, platforms and
 * versions
 */
public suspend fun IgdbClient.platformVersionReleaseDate(
    builder: ApicalypseQueryBuilder.() -> Unit,
): PlatformVersionReleaseDateResult =
    executeOrThrow(IgdbEndpoint.PLATFORM_VERSION_RELEASE_DATE, apicalypseQuery(builder))

/**
 * The main website for the platform
 */
public suspend fun IgdbClient.platformWebsite(builder: ApicalypseQueryBuilder.() -> Unit): PlatformWebsiteResult =
    executeOrThrow(IgdbEndpoint.PLATFORM_WEBSITE, apicalypseQuery(builder))

/**
 * Logo for a platform
 */
public suspend fun IgdbClient.platformLogo(builder: ApicalypseQueryBuilder.() -> Unit): PlatformLogoResult =
    executeOrThrow(IgdbEndpoint.PLATFORM_LOGO, apicalypseQuery(builder))

/**
 * Player perspectives describe the view/perspective of the player in a video game
 */
public suspend fun IgdbClient.playerPerspective(builder: ApicalypseQueryBuilder.() -> Unit): PlayerPerspectiveResult =
    executeOrThrow(IgdbEndpoint.PLAYER_PERSPECTIVE, apicalypseQuery(builder))

/**
 * Region for game localization
 */
public suspend fun IgdbClient.region(builder: ApicalypseQueryBuilder.() -> Unit): RegionResult =
    executeOrThrow(IgdbEndpoint.REGION, apicalypseQuery(builder))

/**
 * A handy endpoint that extends game release dates. Used to dig deeper into release dates, platforms and versions
 */
public suspend fun IgdbClient.releaseDate(builder: ApicalypseQueryBuilder.() -> Unit): ReleaseDateResult =
    executeOrThrow(IgdbEndpoint.RELEASE_DATE, apicalypseQuery(builder))

/**
 * An endpoint to provide definition of all of the current release date statuses
 */
public suspend fun IgdbClient.releaseDateStatus(builder: ApicalypseQueryBuilder.() -> Unit): ReleaseDateStatusResult =
    executeOrThrow(IgdbEndpoint.RELEASE_DATE_STATUS, apicalypseQuery(builder))

/**
 * Screenshots of games
 */
public suspend fun IgdbClient.screenshot(builder: ApicalypseQueryBuilder.() -> Unit): ScreenshotResult =
    executeOrThrow(IgdbEndpoint.SCREENSHOT, apicalypseQuery(builder))

/**
 * Search
 */
public suspend fun IgdbClient.search(builder: ApicalypseQueryBuilder.() -> Unit): SearchResult =
    executeOrThrow(IgdbEndpoint.SEARCH, apicalypseQuery(builder))

/**
 * Video game themes
 */
public suspend fun IgdbClient.theme(builder: ApicalypseQueryBuilder.() -> Unit): ThemeResult =
    executeOrThrow(IgdbEndpoint.THEME, apicalypseQuery(builder))

/**
 * A website url, usually associated with a game
 */
public suspend fun IgdbClient.website(builder: ApicalypseQueryBuilder.() -> Unit): WebsiteResult =
    executeOrThrow(IgdbEndpoint.WEBSITE, apicalypseQuery(builder))

public suspend fun <T : Any> IgdbClient.executeOrThrow(
    endpoint: IgdbEndpoint<T>,
    query: ApicalypseQuery,
): T {
    val result = this.execute(
        endpoint = endpoint,
        query = query,
    )
    @Suppress("ThrowsCount")
    if (result is Success) {
        return result.value
    } else {
        val exception = when (result) {
            is ApiFailure -> IgdbApiFailureException(result)
            is HttpFailure -> IgdbHttpException(result)
            is UnknownHttpCodeFailure -> IgdbHttpException(result)
            is NetworkFailure -> IgdbException(result.error)
            is UnknownFailure -> IgdbException(result.error)
            else -> error("Not an error")
        }
        throw exception
    }
}