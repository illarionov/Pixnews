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
package ru.pixnews.library.igdb

import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.internal.multiquery.MultiQueryArrayParser
import ru.pixnews.library.igdb.model.AgeRating
import ru.pixnews.library.igdb.model.AgeRatingContentDescription
import ru.pixnews.library.igdb.model.AgeRatingContentDescriptionResult
import ru.pixnews.library.igdb.model.AgeRatingResult
import ru.pixnews.library.igdb.model.AlternativeName
import ru.pixnews.library.igdb.model.AlternativeNameResult
import ru.pixnews.library.igdb.model.Artwork
import ru.pixnews.library.igdb.model.ArtworkResult
import ru.pixnews.library.igdb.model.Character
import ru.pixnews.library.igdb.model.CharacterMugShot
import ru.pixnews.library.igdb.model.CharacterMugShotResult
import ru.pixnews.library.igdb.model.CharacterResult
import ru.pixnews.library.igdb.model.Collection
import ru.pixnews.library.igdb.model.CollectionResult
import ru.pixnews.library.igdb.model.Company
import ru.pixnews.library.igdb.model.CompanyLogo
import ru.pixnews.library.igdb.model.CompanyLogoResult
import ru.pixnews.library.igdb.model.CompanyResult
import ru.pixnews.library.igdb.model.CompanyWebsite
import ru.pixnews.library.igdb.model.CompanyWebsiteResult
import ru.pixnews.library.igdb.model.Count
import ru.pixnews.library.igdb.model.Cover
import ru.pixnews.library.igdb.model.CoverResult
import ru.pixnews.library.igdb.model.ExternalGame
import ru.pixnews.library.igdb.model.ExternalGameResult
import ru.pixnews.library.igdb.model.Franchise
import ru.pixnews.library.igdb.model.FranchiseResult
import ru.pixnews.library.igdb.model.Game
import ru.pixnews.library.igdb.model.GameEngine
import ru.pixnews.library.igdb.model.GameEngineLogo
import ru.pixnews.library.igdb.model.GameEngineLogoResult
import ru.pixnews.library.igdb.model.GameEngineResult
import ru.pixnews.library.igdb.model.GameLocalization
import ru.pixnews.library.igdb.model.GameLocalizationResult
import ru.pixnews.library.igdb.model.GameMode
import ru.pixnews.library.igdb.model.GameModeResult
import ru.pixnews.library.igdb.model.GameResult
import ru.pixnews.library.igdb.model.GameVersion
import ru.pixnews.library.igdb.model.GameVersionFeature
import ru.pixnews.library.igdb.model.GameVersionFeatureResult
import ru.pixnews.library.igdb.model.GameVersionFeatureValue
import ru.pixnews.library.igdb.model.GameVersionFeatureValueResult
import ru.pixnews.library.igdb.model.GameVersionResult
import ru.pixnews.library.igdb.model.GameVideo
import ru.pixnews.library.igdb.model.GameVideoResult
import ru.pixnews.library.igdb.model.Genre
import ru.pixnews.library.igdb.model.GenreResult
import ru.pixnews.library.igdb.model.IgdbWebhookId
import ru.pixnews.library.igdb.model.InvolvedCompany
import ru.pixnews.library.igdb.model.InvolvedCompanyResult
import ru.pixnews.library.igdb.model.Keyword
import ru.pixnews.library.igdb.model.KeywordResult
import ru.pixnews.library.igdb.model.Language
import ru.pixnews.library.igdb.model.LanguageResult
import ru.pixnews.library.igdb.model.LanguageSupport
import ru.pixnews.library.igdb.model.LanguageSupportResult
import ru.pixnews.library.igdb.model.LanguageSupportType
import ru.pixnews.library.igdb.model.LanguageSupportTypeResult
import ru.pixnews.library.igdb.model.MultiQueryResultArray
import ru.pixnews.library.igdb.model.MultiplayerMode
import ru.pixnews.library.igdb.model.MultiplayerModeResult
import ru.pixnews.library.igdb.model.Platform
import ru.pixnews.library.igdb.model.PlatformFamily
import ru.pixnews.library.igdb.model.PlatformFamilyResult
import ru.pixnews.library.igdb.model.PlatformLogo
import ru.pixnews.library.igdb.model.PlatformLogoResult
import ru.pixnews.library.igdb.model.PlatformResult
import ru.pixnews.library.igdb.model.PlatformVersion
import ru.pixnews.library.igdb.model.PlatformVersionCompany
import ru.pixnews.library.igdb.model.PlatformVersionCompanyResult
import ru.pixnews.library.igdb.model.PlatformVersionReleaseDate
import ru.pixnews.library.igdb.model.PlatformVersionReleaseDateResult
import ru.pixnews.library.igdb.model.PlatformVersionResult
import ru.pixnews.library.igdb.model.PlatformWebsite
import ru.pixnews.library.igdb.model.PlatformWebsiteResult
import ru.pixnews.library.igdb.model.PlayerPerspective
import ru.pixnews.library.igdb.model.PlayerPerspectiveResult
import ru.pixnews.library.igdb.model.Region
import ru.pixnews.library.igdb.model.RegionResult
import ru.pixnews.library.igdb.model.ReleaseDate
import ru.pixnews.library.igdb.model.ReleaseDateResult
import ru.pixnews.library.igdb.model.ReleaseDateStatus
import ru.pixnews.library.igdb.model.ReleaseDateStatusResult
import ru.pixnews.library.igdb.model.Screenshot
import ru.pixnews.library.igdb.model.ScreenshotResult
import ru.pixnews.library.igdb.model.Search
import ru.pixnews.library.igdb.model.SearchResult
import ru.pixnews.library.igdb.model.Theme
import ru.pixnews.library.igdb.model.ThemeResult
import ru.pixnews.library.igdb.model.UnpackedMultiQueryResult
import ru.pixnews.library.igdb.model.Website
import ru.pixnews.library.igdb.model.WebsiteResult
import java.io.InputStream

/**
 * IGDB endpoints
 *
 * https://api-docs.igdb.com/
 */
public open class IgdbEndpoint<out R : Any>(
    public val endpoint: String,
    public val resultParser: (ApicalypseQuery, InputStream) -> R,
    public val singleItemParser: ((InputStream) -> Any)? = null,
) {
    public open val protobufPath: String
        get() = "$endpoint.pb"

    public open val jsonPath: String
        get() = endpoint

    public open val webhookPath: String
        get() = "$endpoint/webhooks"

    public open fun getTestWebhookPath(webhookId: IgdbWebhookId): String = "$endpoint/webhooks/test/${webhookId.value}"

    public companion object {
        /**
         * Age Rating according to various rating organisations
         */
        public val AGE_RATING: IgdbEndpoint<AgeRatingResult> = IgdbEndpoint(
            "age_ratings",
            AgeRatingResult.ADAPTER::decode,
            AgeRating.ADAPTER::decode,
        )

        /**
         * Age Rating Descriptors
         */
        public val AGE_RATING_CONTENT_DESCRIPTION: IgdbEndpoint<AgeRatingContentDescriptionResult> =
            IgdbEndpoint(
                "age_rating_content_descriptions",
                AgeRatingContentDescriptionResult.ADAPTER::decode,
                AgeRatingContentDescription.ADAPTER::decode,
            )

        /**
         * Alternative and international game titles
         */
        public val ALTERNATIVE_NAME: IgdbEndpoint<AlternativeNameResult> = IgdbEndpoint(
            "alternative_names",
            AlternativeNameResult.ADAPTER::decode,
            AlternativeName.ADAPTER::decode,
        )

        /**
         * Official artworks (resolution and aspect ratio may vary)
         */
        public val ARTWORK: IgdbEndpoint<ArtworkResult> = IgdbEndpoint(
            "artworks",
            ArtworkResult.ADAPTER::decode,
            Artwork.ADAPTER::decode,
        )

        /**
         * Video game characters
         */
        public val CHARACTER: IgdbEndpoint<CharacterResult> = IgdbEndpoint(
            "characters",
            CharacterResult.ADAPTER::decode,
            Character.ADAPTER::decode,
        )

        /**
         * Images depicting game characters
         */
        public val CHARACTER_MUG_SHOT: IgdbEndpoint<CharacterMugShotResult> = IgdbEndpoint(
            "character_mug_shots",
            CharacterMugShotResult.ADAPTER::decode,
            CharacterMugShot.ADAPTER::decode,
        )

        /**
         * Collection, AKA Series
         */
        public val COLLECTION: IgdbEndpoint<CollectionResult> = IgdbEndpoint(
            "collections",
            CollectionResult.ADAPTER::decode,
            Collection.ADAPTER::decode,
        )

        /**
         * Video game companies. Both publishers & developers
         */
        public val COMPANY: IgdbEndpoint<CompanyResult> = IgdbEndpoint(
            "companies",
            CompanyResult.ADAPTER::decode,
            Company.ADAPTER::decode,
        )

        /**
         * The logos of developers and publishers
         */
        public val COMPANY_LOGO: IgdbEndpoint<CompanyLogoResult> = IgdbEndpoint(
            "company_logos",
            CompanyLogoResult.ADAPTER::decode,
            CompanyLogo.ADAPTER::decode,
        )

        /**
         * Company Website
         */
        public val COMPANY_WEBSITE: IgdbEndpoint<CompanyWebsiteResult> = IgdbEndpoint(
            "company_websites",
            CompanyWebsiteResult.ADAPTER::decode,
            CompanyWebsite.ADAPTER::decode,
        )

        /**
         * The cover art of games
         */
        public val COVER: IgdbEndpoint<CoverResult> = IgdbEndpoint(
            "covers",
            CoverResult.ADAPTER::decode,
            Cover.ADAPTER::decode,
        )

        /**
         * Game IDs on other services
         */
        public val EXTERNAL_GAME: IgdbEndpoint<ExternalGameResult> = IgdbEndpoint(
            "external_games",
            ExternalGameResult.ADAPTER::decode,
            ExternalGame.ADAPTER::decode,
        )

        /**
         * A list of video game franchises such as Star Wars.
         */
        public val FRANCHISE: IgdbEndpoint<FranchiseResult> = IgdbEndpoint(
            "franchises",
            FranchiseResult.ADAPTER::decode,
            Franchise.ADAPTER::decode,
        )

        /**
         * Video game engines such as unreal engine.
         */
        public val GAME_ENGINE: IgdbEndpoint<GameEngineResult> = IgdbEndpoint(
            "game_engines",
            GameEngineResult.ADAPTER::decode,
            GameEngine.ADAPTER::decode,
        )

        /**
         * The logos of game engines
         */
        public val GAME_ENGINE_LOGO: IgdbEndpoint<GameEngineLogoResult> = IgdbEndpoint(
            "game_engine_logos",
            GameEngineLogoResult.ADAPTER::decode,
            GameEngineLogo.ADAPTER::decode,
        )

        /**
         * Video Games!
         */
        public val GAME: IgdbEndpoint<GameResult> = IgdbEndpoint(
            "games",
            GameResult.ADAPTER::decode,
            Game.ADAPTER::decode,
        )

        /**
         * Game localization for a game
         */
        public val GAME_LOCALIZATION: IgdbEndpoint<GameLocalizationResult> = IgdbEndpoint(
            "game_localizations",
            GameLocalizationResult.ADAPTER::decode,
            GameLocalization.ADAPTER::decode,
        )

        /**
         * Single player, Multiplayer etc
         */
        public val GAME_MODE: IgdbEndpoint<GameModeResult> = IgdbEndpoint(
            "game_modes",
            GameModeResult.ADAPTER::decode,
            GameMode.ADAPTER::decode,
        )

        /**
         * Details about game editions and versions
         */
        public val GAME_VERSION: IgdbEndpoint<GameVersionResult> = IgdbEndpoint(
            "game_versions",
            GameVersionResult.ADAPTER::decode,
            GameVersion.ADAPTER::decode,
        )

        /**
         * Features and descriptions of what makes each version/edition different from the main game
         */
        public val GAME_VERSION_FEATURE: IgdbEndpoint<GameVersionFeatureResult> = IgdbEndpoint(
            "game_version_features",
            GameVersionFeatureResult.ADAPTER::decode,
            GameVersionFeature.ADAPTER::decode,
        )

        /**
         * A video associated with a game
         */
        public val GAME_VIDEO: IgdbEndpoint<GameVideoResult> = IgdbEndpoint(
            "game_videos",
            GameVideoResult.ADAPTER::decode,
            GameVideo.ADAPTER::decode,
        )

        /**
         * The bool/text value of the feature
         */
        public val GAME_VERSION_FEATURE_VALUE: IgdbEndpoint<GameVersionFeatureValueResult> =
            IgdbEndpoint(
                "game_version_feature_values",
                GameVersionFeatureValueResult.ADAPTER::decode,
                GameVersionFeatureValue.ADAPTER::decode,
            )

        /**
         * Genres of video game
         */
        public val GENRE: IgdbEndpoint<GenreResult> = IgdbEndpoint(
            "genres",
            GenreResult.ADAPTER::decode,
            Genre.ADAPTER::decode,
        )

        /**
         * Involved Company
         */
        public val INVOLVED_COMPANY: IgdbEndpoint<InvolvedCompanyResult> = IgdbEndpoint(
            "involved_companies",
            InvolvedCompanyResult.ADAPTER::decode,
            InvolvedCompany.ADAPTER::decode,
        )

        /**
         * Languages that are used in the Language Support endpoint
         */
        public val LANGUAGE: IgdbEndpoint<LanguageResult> = IgdbEndpoint(
            "languages",
            LanguageResult.ADAPTER::decode,
            Language.ADAPTER::decode,
        )

        /**
         * Keywords are words or phrases that get tagged to a game such as “world war 2” or “steampunk”
         */
        public val KEYWORD: IgdbEndpoint<KeywordResult> = IgdbEndpoint(
            "keywords",
            KeywordResult.ADAPTER::decode,
            Keyword.ADAPTER::decode,
        )

        /**
         * Games can be played with different languages for voice acting, subtitles, or the interface language.
         */
        public val LANGUAGE_SUPPORT: IgdbEndpoint<LanguageSupportResult> = IgdbEndpoint(
            "language_supports",
            LanguageSupportResult.ADAPTER::decode,
            LanguageSupport.ADAPTER::decode,
        )

        /**
         * Language Support Types contains the identifiers for the support types that Language Support uses.
         */
        public val LANGUAGE_SUPPORT_TYPE: IgdbEndpoint<LanguageSupportTypeResult> = IgdbEndpoint(
            "language_support_types",
            LanguageSupportTypeResult.ADAPTER::decode,
            LanguageSupportType.ADAPTER::decode,
        )

        /**
         * Data about the supported multiplayer types
         */
        public val MULTIPLAYER_MODE: IgdbEndpoint<MultiplayerModeResult> = IgdbEndpoint(
            "multiplayer_modes",
            MultiplayerModeResult.ADAPTER::decode,
            MultiplayerMode.ADAPTER::decode,
        )

        /**
         * The hardware used to run the game or game delivery network
         */
        public val PLATFORM: IgdbEndpoint<PlatformResult> = IgdbEndpoint(
            "platforms",
            PlatformResult.ADAPTER::decode,
            Platform.ADAPTER::decode,
        )

        /**
         * Platform Version
         */
        public val PLATFORM_VERSION: IgdbEndpoint<PlatformVersionResult> = IgdbEndpoint(
            "platform_versions",
            PlatformVersionResult.ADAPTER::decode,
            PlatformVersion.ADAPTER::decode,
        )

        /**
         * A collection of closely related platforms
         */
        public val PLATFORM_FAMILY: IgdbEndpoint<PlatformFamilyResult> = IgdbEndpoint(
            "platform_families",
            PlatformFamilyResult.ADAPTER::decode,
            PlatformFamily.ADAPTER::decode,
        )

        /**
         * A platform developer
         */
        public val PLATFORM_VERSION_COMPANY: IgdbEndpoint<PlatformVersionCompanyResult> =
            IgdbEndpoint(
                "platform_version_companies",
                PlatformVersionCompanyResult.ADAPTER::decode,
                PlatformVersionCompany.ADAPTER::decode,
            )

        /**
         * A handy endpoint that extends platform release dates. Used to dig deeper into release dates, platforms and
         * versions
         */
        public val PLATFORM_VERSION_RELEASE_DATE: IgdbEndpoint<PlatformVersionReleaseDateResult> =
            IgdbEndpoint(
                "platform_version_release_dates",
                PlatformVersionReleaseDateResult.ADAPTER::decode,
                PlatformVersionReleaseDate.ADAPTER::decode,
            )

        /**
         * The main website for the platform
         */
        public val PLATFORM_WEBSITE: IgdbEndpoint<PlatformWebsiteResult> = IgdbEndpoint(
            "platform_websites",
            PlatformWebsiteResult.ADAPTER::decode,
            PlatformWebsite.ADAPTER::decode,
        )

        /**
         * Logo for a platform
         */
        public val PLATFORM_LOGO: IgdbEndpoint<PlatformLogoResult> = IgdbEndpoint(
            "platform_logos",
            PlatformLogoResult.ADAPTER::decode,
            PlatformLogo.ADAPTER::decode,
        )

        /**
         * Player perspectives describe the view/perspective of the player in a video game
         */
        public val PLAYER_PERSPECTIVE: IgdbEndpoint<PlayerPerspectiveResult> = IgdbEndpoint(
            "player_perspectives",
            PlayerPerspectiveResult.ADAPTER::decode,
            PlayerPerspective.ADAPTER::decode,
        )

        /**
         * Region for game localization
         */
        public val REGION: IgdbEndpoint<RegionResult> = IgdbEndpoint(
            "regions",
            RegionResult.ADAPTER::decode,
            Region.ADAPTER::decode,
        )

        /**
         * A handy endpoint that extends game release dates. Used to dig deeper into release dates, platforms
         * and versions
         */
        public val RELEASE_DATE: IgdbEndpoint<ReleaseDateResult> = IgdbEndpoint(
            "release_dates",
            ReleaseDateResult.ADAPTER::decode,
            ReleaseDate.ADAPTER::decode,
        )

        /**
         * An endpoint to provide definition of all of the current release date statuses
         */
        public val RELEASE_DATE_STATUS: IgdbEndpoint<ReleaseDateStatusResult> = IgdbEndpoint(
            "release_date_statuses",
            ReleaseDateStatusResult.ADAPTER::decode,
            ReleaseDateStatus.ADAPTER::decode,
        )

        /**
         * Screenshots of games
         */
        public val SCREENSHOT: IgdbEndpoint<ScreenshotResult> = IgdbEndpoint(
            "screenshots",
            ScreenshotResult.ADAPTER::decode,
            Screenshot.ADAPTER::decode,
        )

        /**
         * Search
         */
        public val SEARCH: IgdbEndpoint<SearchResult> = IgdbEndpoint(
            "search",
            SearchResult.ADAPTER::decode,
            Search.ADAPTER::decode,
        )

        /**
         * Video game themes
         */
        public val THEME: IgdbEndpoint<ThemeResult> = IgdbEndpoint(
            "themes",
            ThemeResult.ADAPTER::decode,
            Theme.ADAPTER::decode,
        )

        /**
         * A website url, usually associated with a game
         */
        public val WEBSITE: IgdbEndpoint<WebsiteResult> = IgdbEndpoint(
            "websites",
            WebsiteResult.ADAPTER::decode,
            Website.ADAPTER::decode,
        )

        /**
         * Endpoint to execute multi-queries
         */
        public val MULTIQUERY: IgdbEndpoint<List<UnpackedMultiQueryResult<*>>> = IgdbEndpoint(
            "multiquery",
            MultiQueryArrayParser(MultiQueryResultArray.ADAPTER::decode),
            null,
        )

        public fun IgdbEndpoint<*>.countEndpoint(): IgdbEndpoint<Count> = IgdbEndpoint<Count, Nothing>(
            this.endpoint + "/count",
            Count.ADAPTER::decode,
            null,
        )

        private operator fun <LR : Any, R : Any> invoke(
            endpoint: String,
            resultParser: (InputStream) -> LR,
            singleItemParser: ((InputStream) -> R)? = null,
        ): IgdbEndpoint<LR> = IgdbEndpoint(
            endpoint = endpoint,
            resultParser = { _, stream -> resultParser(stream) },
            singleItemParser = singleItemParser,
        )
    }
}