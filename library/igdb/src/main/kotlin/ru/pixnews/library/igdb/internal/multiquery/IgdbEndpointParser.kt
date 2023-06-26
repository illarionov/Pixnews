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
package ru.pixnews.library.igdb.internal.multiquery

import ru.pixnews.library.igdb.IgdbEndpoint
import ru.pixnews.library.igdb.model.AgeRating
import ru.pixnews.library.igdb.model.AgeRatingContentDescription
import ru.pixnews.library.igdb.model.AlternativeName
import ru.pixnews.library.igdb.model.Artwork
import ru.pixnews.library.igdb.model.Character
import ru.pixnews.library.igdb.model.CharacterMugShot
import ru.pixnews.library.igdb.model.Collection
import ru.pixnews.library.igdb.model.Company
import ru.pixnews.library.igdb.model.CompanyLogo
import ru.pixnews.library.igdb.model.CompanyWebsite
import ru.pixnews.library.igdb.model.Cover
import ru.pixnews.library.igdb.model.ExternalGame
import ru.pixnews.library.igdb.model.Franchise
import ru.pixnews.library.igdb.model.Game
import ru.pixnews.library.igdb.model.GameEngine
import ru.pixnews.library.igdb.model.GameEngineLogo
import ru.pixnews.library.igdb.model.GameLocalization
import ru.pixnews.library.igdb.model.GameMode
import ru.pixnews.library.igdb.model.GameVersion
import ru.pixnews.library.igdb.model.GameVersionFeature
import ru.pixnews.library.igdb.model.GameVersionFeatureValue
import ru.pixnews.library.igdb.model.GameVideo
import ru.pixnews.library.igdb.model.Genre
import ru.pixnews.library.igdb.model.InvolvedCompany
import ru.pixnews.library.igdb.model.Keyword
import ru.pixnews.library.igdb.model.Language
import ru.pixnews.library.igdb.model.LanguageSupport
import ru.pixnews.library.igdb.model.LanguageSupportType
import ru.pixnews.library.igdb.model.MultiplayerMode
import ru.pixnews.library.igdb.model.Platform
import ru.pixnews.library.igdb.model.PlatformFamily
import ru.pixnews.library.igdb.model.PlatformLogo
import ru.pixnews.library.igdb.model.PlatformVersion
import ru.pixnews.library.igdb.model.PlatformVersionCompany
import ru.pixnews.library.igdb.model.PlatformVersionReleaseDate
import ru.pixnews.library.igdb.model.PlatformWebsite
import ru.pixnews.library.igdb.model.PlayerPerspective
import ru.pixnews.library.igdb.model.Region
import ru.pixnews.library.igdb.model.ReleaseDate
import ru.pixnews.library.igdb.model.ReleaseDateStatus
import ru.pixnews.library.igdb.model.Screenshot
import ru.pixnews.library.igdb.model.Search
import ru.pixnews.library.igdb.model.Theme
import ru.pixnews.library.igdb.model.Website
import java.io.InputStream

internal object IgdbEndpointParser {
    @SuppressWarnings("CyclomaticComplexMethod")
    internal fun <T : Any> getProtobufParserForEndpoint(endpoint: String): (InputStream) -> T {
        val parser: (InputStream) -> Any = when (endpoint) {
            IgdbEndpoint.AGE_RATING -> AgeRating.ADAPTER::decode
            IgdbEndpoint.AGE_RATING_CONTENT_DESCRIPTION -> AgeRatingContentDescription.ADAPTER::decode
            IgdbEndpoint.ALTERNATIVE_NAME -> AlternativeName.ADAPTER::decode
            IgdbEndpoint.ARTWORK -> Artwork.ADAPTER::decode
            IgdbEndpoint.CHARACTER -> Character.ADAPTER::decode
            IgdbEndpoint.CHARACTER_MUG_SHOT -> CharacterMugShot.ADAPTER::decode
            IgdbEndpoint.COLLECTION -> Collection.ADAPTER::decode
            IgdbEndpoint.COMPANY -> Company.ADAPTER::decode
            IgdbEndpoint.COMPANY_LOGO -> CompanyLogo.ADAPTER::decode
            IgdbEndpoint.COMPANY_WEBSITE -> CompanyWebsite.ADAPTER::decode
            IgdbEndpoint.COVER -> Cover.ADAPTER::decode
            IgdbEndpoint.EXTERNAL_GAME -> ExternalGame.ADAPTER::decode
            IgdbEndpoint.FRANCHISE -> Franchise.ADAPTER::decode
            IgdbEndpoint.GAME_ENGINE -> GameEngine.ADAPTER::decode
            IgdbEndpoint.GAME_ENGINE_LOGO -> GameEngineLogo.ADAPTER::decode
            IgdbEndpoint.GAME -> Game.ADAPTER::decode
            IgdbEndpoint.GAME_LOCALIZATION -> GameLocalization.ADAPTER::decode
            IgdbEndpoint.GAME_MODE -> GameMode.ADAPTER::decode
            IgdbEndpoint.GAME_VERSION -> GameVersion.ADAPTER::decode
            IgdbEndpoint.GAME_VERSION_FEATURE -> GameVersionFeature.ADAPTER::decode
            IgdbEndpoint.GAME_VIDEO -> GameVideo.ADAPTER::decode
            IgdbEndpoint.GAME_VERSION_FEATURE_VALUE -> GameVersionFeatureValue.ADAPTER::decode
            IgdbEndpoint.GENRE -> Genre.ADAPTER::decode
            IgdbEndpoint.INVOLVED_COMPANY -> InvolvedCompany.ADAPTER::decode
            IgdbEndpoint.LANGUAGE -> Language.ADAPTER::decode
            IgdbEndpoint.KEYWORD -> Keyword.ADAPTER::decode
            IgdbEndpoint.LANGUAGE_SUPPORT -> LanguageSupport.ADAPTER::decode
            IgdbEndpoint.LANGUAGE_SUPPORT_TYPE -> LanguageSupportType.ADAPTER::decode
            IgdbEndpoint.MULTIPLAYER_MODE -> MultiplayerMode.ADAPTER::decode
            IgdbEndpoint.PLATFORM -> Platform.ADAPTER::decode
            IgdbEndpoint.PLATFORM_VERSION -> PlatformVersion.ADAPTER::decode
            IgdbEndpoint.PLATFORM_FAMILY -> PlatformFamily.ADAPTER::decode
            IgdbEndpoint.PLATFORM_VERSION_COMPANY -> PlatformVersionCompany.ADAPTER::decode
            IgdbEndpoint.PLATFORM_VERSION_RELEASE_DATE -> PlatformVersionReleaseDate.ADAPTER::decode
            IgdbEndpoint.PLATFORM_WEBSITE -> PlatformWebsite.ADAPTER::decode
            IgdbEndpoint.PLATFORM_LOGO -> PlatformLogo.ADAPTER::decode
            IgdbEndpoint.PLAYER_PERSPECTIVE -> PlayerPerspective.ADAPTER::decode
            IgdbEndpoint.REGION -> Region.ADAPTER::decode
            IgdbEndpoint.RELEASE_DATE -> ReleaseDate.ADAPTER::decode
            IgdbEndpoint.RELEASE_DATE_STATUS -> ReleaseDateStatus.ADAPTER::decode
            IgdbEndpoint.SCREENSHOT -> Screenshot.ADAPTER::decode
            IgdbEndpoint.SEARCH -> Search.ADAPTER::decode
            IgdbEndpoint.THEME -> Theme.ADAPTER::decode
            IgdbEndpoint.WEBSITE -> Website.ADAPTER::decode
            else -> error("No parser for `$endpoint`")
        }
        @Suppress("UNCHECKED_CAST")
        return parser as (InputStream) -> T
    }
}
