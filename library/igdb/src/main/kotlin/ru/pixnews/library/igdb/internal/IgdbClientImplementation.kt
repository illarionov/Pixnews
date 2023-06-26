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
package ru.pixnews.library.igdb.internal

import ru.pixnews.library.igdb.IgdbClient
import ru.pixnews.library.igdb.IgdbEndpoint
import ru.pixnews.library.igdb.apicalypse.ApicalypseMultiQuery
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.error.IgdbApiFailureException
import ru.pixnews.library.igdb.error.IgdbException
import ru.pixnews.library.igdb.error.IgdbHttpException
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.ApiFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.HttpFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.NetworkFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.UnknownFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.UnknownHttpCodeFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Success
import ru.pixnews.library.igdb.internal.multiquery.MultiQueryArrayParser
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
import ru.pixnews.library.igdb.multiquery.UnpackedMultiQueryResult
import java.io.InputStream

@Suppress("TooManyFunctions")
internal class IgdbClientImplementation(
    private val requestExecutor: RequestExecutor,
) : IgdbClient {
    override suspend fun ageRating(query: ApicalypseQuery): AgeRatingResult =
        executeRequest(
            endpoint = IgdbEndpoint.AGE_RATING,
            query = query,
            successResponseParser = AgeRatingResult.ADAPTER::decode,
        )

    override suspend fun ageRatingContentDescription(query: ApicalypseQuery): AgeRatingContentDescriptionResult =
        executeRequest(
            endpoint = IgdbEndpoint.AGE_RATING_CONTENT_DESCRIPTION,
            query = query,
            successResponseParser = AgeRatingContentDescriptionResult.ADAPTER::decode,
        )

    override suspend fun alternativeName(query: ApicalypseQuery): AlternativeNameResult =
        executeRequest(
            endpoint = IgdbEndpoint.ALTERNATIVE_NAME,
            query = query,
            successResponseParser = AlternativeNameResult.ADAPTER::decode,
        )

    override suspend fun artwork(query: ApicalypseQuery): ArtworkResult =
        executeRequest(
            endpoint = IgdbEndpoint.ARTWORK,
            query = query,
            successResponseParser = ArtworkResult.ADAPTER::decode,
        )

    override suspend fun character(query: ApicalypseQuery): CharacterResult =
        executeRequest(
            endpoint = IgdbEndpoint.CHARACTER,
            query = query,
            successResponseParser = CharacterResult.ADAPTER::decode,
        )

    override suspend fun characterMugShot(query: ApicalypseQuery): CharacterMugShotResult =
        executeRequest(
            endpoint = IgdbEndpoint.CHARACTER_MUG_SHOT,
            query = query,
            successResponseParser = CharacterMugShotResult.ADAPTER::decode,
        )

    override suspend fun collection(query: ApicalypseQuery): CollectionResult =
        executeRequest(
            endpoint = IgdbEndpoint.COLLECTION,
            query = query,
            successResponseParser = CollectionResult.ADAPTER::decode,
        )

    override suspend fun company(query: ApicalypseQuery): CompanyResult =
        executeRequest(
            endpoint = IgdbEndpoint.COMPANY,
            query = query,
            successResponseParser = CompanyResult.ADAPTER::decode,
        )

    override suspend fun companyLogo(query: ApicalypseQuery): CompanyLogoResult =
        executeRequest(
            endpoint = IgdbEndpoint.COMPANY_LOGO,
            query = query,
            successResponseParser = CompanyLogoResult.ADAPTER::decode,
        )

    override suspend fun companyWebsite(query: ApicalypseQuery): CompanyWebsiteResult =
        executeRequest(
            endpoint = IgdbEndpoint.COMPANY_WEBSITE,
            query = query,
            successResponseParser = CompanyWebsiteResult.ADAPTER::decode,
        )

    override suspend fun cover(query: ApicalypseQuery): CoverResult =
        executeRequest(
            endpoint = IgdbEndpoint.COVER,
            query = query,
            successResponseParser = CoverResult.ADAPTER::decode,
        )

    override suspend fun externalGame(query: ApicalypseQuery): ExternalGameResult =
        executeRequest(
            endpoint = IgdbEndpoint.EXTERNAL_GAME,
            query = query,
            successResponseParser = ExternalGameResult.ADAPTER::decode,
        )

    override suspend fun franchise(query: ApicalypseQuery): FranchiseResult =
        executeRequest(
            endpoint = IgdbEndpoint.FRANCHISE,
            query = query,
            successResponseParser = FranchiseResult.ADAPTER::decode,
        )

    override suspend fun gameEngine(query: ApicalypseQuery): GameEngineResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_ENGINE,
            query = query,
            successResponseParser = GameEngineResult.ADAPTER::decode,
        )

    override suspend fun gameEngineLogo(query: ApicalypseQuery): GameEngineLogoResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_ENGINE_LOGO,
            query = query,
            successResponseParser = GameEngineLogoResult.ADAPTER::decode,
        )

    override suspend fun game(query: ApicalypseQuery): GameResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME,
            query = query,
            successResponseParser = GameResult.ADAPTER::decode,
        )

    override suspend fun gameLocalization(query: ApicalypseQuery): GameLocalizationResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_LOCALIZATION,
            query = query,
            successResponseParser = GameLocalizationResult.ADAPTER::decode,
        )

    override suspend fun gameMode(query: ApicalypseQuery): GameModeResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_MODE,
            query = query,
            successResponseParser = GameModeResult.ADAPTER::decode,
        )

    override suspend fun gameVersion(query: ApicalypseQuery): GameVersionResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_VERSION,
            query = query,
            successResponseParser = GameVersionResult.ADAPTER::decode,
        )

    override suspend fun gameVersionFeature(query: ApicalypseQuery): GameVersionFeatureResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_VERSION_FEATURE,
            query = query,
            successResponseParser = GameVersionFeatureResult.ADAPTER::decode,
        )

    override suspend fun gameVideo(query: ApicalypseQuery): GameVideoResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_VIDEO,
            query = query,
            successResponseParser = GameVideoResult.ADAPTER::decode,
        )

    override suspend fun gameVersionFeatureValue(query: ApicalypseQuery): GameVersionFeatureValueResult =
        executeRequest(
            endpoint = IgdbEndpoint.GAME_VERSION_FEATURE_VALUE,
            query = query,
            successResponseParser = GameVersionFeatureValueResult.ADAPTER::decode,
        )

    override suspend fun genre(query: ApicalypseQuery): GenreResult =
        executeRequest(
            endpoint = IgdbEndpoint.GENRE,
            query = query,
            successResponseParser = GenreResult.ADAPTER::decode,
        )

    override suspend fun involvedCompany(query: ApicalypseQuery): InvolvedCompanyResult =
        executeRequest(
            endpoint = IgdbEndpoint.INVOLVED_COMPANY,
            query = query,
            successResponseParser = InvolvedCompanyResult.ADAPTER::decode,
        )

    override suspend fun language(query: ApicalypseQuery): LanguageResult =
        executeRequest(
            endpoint = IgdbEndpoint.LANGUAGE,
            query = query,
            successResponseParser = LanguageResult.ADAPTER::decode,
        )

    override suspend fun keyword(query: ApicalypseQuery): KeywordResult =
        executeRequest(
            endpoint = IgdbEndpoint.KEYWORD,
            query = query,
            successResponseParser = KeywordResult.ADAPTER::decode,
        )

    override suspend fun languageSupport(query: ApicalypseQuery): LanguageSupportResult =
        executeRequest(
            endpoint = IgdbEndpoint.LANGUAGE_SUPPORT,
            query = query,
            successResponseParser = LanguageSupportResult.ADAPTER::decode,
        )

    override suspend fun languageSupportType(query: ApicalypseQuery): LanguageSupportTypeResult =
        executeRequest(
            endpoint = IgdbEndpoint.LANGUAGE_SUPPORT_TYPE,
            query = query,
            successResponseParser = LanguageSupportTypeResult.ADAPTER::decode,
        )

    override suspend fun multiplayerMode(query: ApicalypseQuery): MultiplayerModeResult =
        executeRequest(
            endpoint = IgdbEndpoint.MULTIPLAYER_MODE,
            query = query,
            successResponseParser = MultiplayerModeResult.ADAPTER::decode,
        )

    override suspend fun multiquery(query: ApicalypseMultiQuery): List<UnpackedMultiQueryResult<*>> =
        executeRequest(
            endpoint = IgdbEndpoint.MULTIQUERY,
            query = query,
            successResponseParser = MultiQueryArrayParser(query),
        )

    override suspend fun platform(query: ApicalypseQuery): PlatformResult =
        executeRequest(
            endpoint = IgdbEndpoint.PLATFORM,
            query = query,
            successResponseParser = PlatformResult.ADAPTER::decode,
        )

    override suspend fun platformVersion(query: ApicalypseQuery): PlatformVersionResult =
        executeRequest(
            endpoint = IgdbEndpoint.PLATFORM_VERSION,
            query = query,
            successResponseParser = PlatformVersionResult.ADAPTER::decode,
        )

    override suspend fun platformFamily(query: ApicalypseQuery): PlatformFamilyResult =
        executeRequest(
            endpoint = IgdbEndpoint.PLATFORM_FAMILY,
            query = query,
            successResponseParser = PlatformFamilyResult.ADAPTER::decode,
        )

    override suspend fun platformVersionCompany(query: ApicalypseQuery): PlatformVersionCompany =
        executeRequest(
            endpoint = IgdbEndpoint.PLATFORM_VERSION_COMPANY,
            query = query,
            successResponseParser = PlatformVersionCompany.ADAPTER::decode,
        )

    override suspend fun platformVersionReleaseDate(query: ApicalypseQuery): PlatformVersionReleaseDateResult =
        executeRequest(
            endpoint = IgdbEndpoint.PLATFORM_VERSION_RELEASE_DATE,
            query = query,
            successResponseParser = PlatformVersionReleaseDateResult.ADAPTER::decode,
        )

    override suspend fun platformWebsite(query: ApicalypseQuery): PlatformWebsiteResult =
        executeRequest(
            endpoint = IgdbEndpoint.PLATFORM_WEBSITE,
            query = query,
            successResponseParser = PlatformWebsiteResult.ADAPTER::decode,
        )

    override suspend fun platformLogo(query: ApicalypseQuery): PlatformLogoResult =
        executeRequest(
            endpoint = IgdbEndpoint.PLATFORM_LOGO,
            query = query,
            successResponseParser = PlatformLogoResult.ADAPTER::decode,
        )

    override suspend fun playerPerspective(query: ApicalypseQuery): PlayerPerspectiveResult =
        executeRequest(
            endpoint = IgdbEndpoint.PLAYER_PERSPECTIVE,
            query = query,
            successResponseParser = PlayerPerspectiveResult.ADAPTER::decode,
        )

    override suspend fun region(query: ApicalypseQuery): RegionResult =
        executeRequest(
            endpoint = IgdbEndpoint.REGION,
            query = query,
            successResponseParser = RegionResult.ADAPTER::decode,
        )

    override suspend fun releaseDate(query: ApicalypseQuery): ReleaseDateResult =
        executeRequest(
            endpoint = IgdbEndpoint.RELEASE_DATE,
            query = query,
            successResponseParser = ReleaseDateResult.ADAPTER::decode,
        )

    override suspend fun releaseDateStatus(query: ApicalypseQuery): ReleaseDateStatusResult =
        executeRequest(
            endpoint = IgdbEndpoint.RELEASE_DATE_STATUS,
            query = query,
            successResponseParser = ReleaseDateStatusResult.ADAPTER::decode,
        )

    override suspend fun screenshot(query: ApicalypseQuery): ScreenshotResult =
        executeRequest(
            endpoint = IgdbEndpoint.SCREENSHOT,
            query = query,
            successResponseParser = ScreenshotResult.ADAPTER::decode,
        )

    override suspend fun search(query: ApicalypseQuery): SearchResult =
        executeRequest(
            endpoint = IgdbEndpoint.SEARCH,
            query = query,
            successResponseParser = SearchResult.ADAPTER::decode,
        )

    override suspend fun theme(query: ApicalypseQuery): ThemeResult =
        executeRequest(
            endpoint = IgdbEndpoint.THEME,
            query = query,
            successResponseParser = ThemeResult.ADAPTER::decode,
        )

    override suspend fun website(query: ApicalypseQuery): WebsiteResult =
        executeRequest(
            endpoint = IgdbEndpoint.WEBSITE,
            query = query,
            successResponseParser = WebsiteResult.ADAPTER::decode,
        )

    private suspend fun <T : Any> executeRequest(
        endpoint: String,
        query: ApicalypseQuery,
        successResponseParser: (InputStream) -> T,
    ): T {
        val result = requestExecutor(
            endpoint = IgdbEndpoint.getProtobufEndpoint(endpoint),
            query = query,
            successResponseParser = successResponseParser,
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
}
