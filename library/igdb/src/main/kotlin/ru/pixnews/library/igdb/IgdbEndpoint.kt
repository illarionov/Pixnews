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

import androidx.annotation.StringDef

/**
 * IGDB endpoints
 *
 * https://api-docs.igdb.com/
 */
public object IgdbEndpoint {
    /**
     * Age Rating according to various rating organisations
     */
    public const val AGE_RATING: String = "age_ratings"

    /**
     * Age Rating Descriptors
     */
    public const val AGE_RATING_CONTENT_DESCRIPTION: String = "age_rating_content_descriptions"

    /**
     * Alternative and international game titles
     */
    public const val ALTERNATIVE_NAME: String = "alternative_names"

    /**
     * Official artworks (resolution and aspect ratio may vary)
     */
    public const val ARTWORK: String = "artworks"

    /**
     * Video game characters
     */
    public const val CHARACTER: String = "characters"

    /**
     * Images depicting game characters
     */
    public const val CHARACTER_MUG_SHOT: String = "character_mug_shots"

    /**
     * Collection, AKA Series
     */
    public const val COLLECTION: String = "collections"

    /**
     * Video game companies. Both publishers & developers
     */
    public const val COMPANY: String = "companies"

    /**
     * The logos of developers and publishers
     */
    public const val COMPANY_LOGO: String = "company_logos"

    /**
     * Company Website
     */
    public const val COMPANY_WEBSITE: String = "company_websites"

    /**
     * The cover art of games
     */
    public const val COVER: String = "covers"

    /**
     * Game IDs on other services
     */
    public const val EXTERNAL_GAME: String = "external_games"

    /**
     * A list of video game franchises such as Star Wars.
     */
    public const val FRANCHISE: String = "franchises"

    /**
     * Video game engines such as unreal engine.
     */
    public const val GAME_ENGINE: String = "game_engines"

    /**
     * The logos of game engines
     */
    public const val GAME_ENGINE_LOGO: String = "game_engine_logos"

    /**
     * Video Games!
     */
    public const val GAME: String = "games"

    /**
     * Game localization for a game
     */
    public const val GAME_LOCALIZATION: String = "game_localizations"

    /**
     * Single player, Multiplayer etc
     */
    public const val GAME_MODE: String = "game_modes"

    /**
     * Details about game editions and versions
     */
    public const val GAME_VERSION: String = "game_versions"

    /**
     * Features and descriptions of what makes each version/edition different from the main game
     */
    public const val GAME_VERSION_FEATURE: String = "game_version_features"

    /**
     * A video associated with a game
     */
    public const val GAME_VIDEO: String = "game_videos"

    /**
     * The bool/text value of the feature
     */
    public const val GAME_VERSION_FEATURE_VALUE: String = "game_version_feature_values"

    /**
     * Genres of video game
     */
    public const val GENRE: String = "genres"

    /**
     * Involved Company
     */
    public const val INVOLVED_COMPANY: String = "involved_companies"

    /**
     * Languages that are used in the Language Support endpoint
     */
    public const val LANGUAGE: String = "languages"

    /**
     * Keywords are words or phrases that get tagged to a game such as “world war 2” or “steampunk”
     */
    public const val KEYWORD: String = "keywords"

    /**
     * Games can be played with different languages for voice acting, subtitles, or the interface language.
     */
    public const val LANGUAGE_SUPPORT: String = "language_supports"

    /**
     * Language Support Types contains the identifiers for the support types that Language Support uses.
     */
    public const val LANGUAGE_SUPPORT_TYPE: String = "language_support_types"

    /**
     * Data about the supported multiplayer types
     */
    public const val MULTIPLAYER_MODE: String = "multiplayer_modes"

    /**
     * The hardware used to run the game or game delivery network
     */
    public const val PLATFORM: String = "platforms"

    /**
     * Platform Version
     */
    public const val PLATFORM_VERSION: String = "platform_versions"

    /**
     * A collection of closely related platforms
     */
    public const val PLATFORM_FAMILY: String = "platform_families"

    /**
     * A platform developer
     */
    public const val PLATFORM_VERSION_COMPANY: String = "platform_version_companies"

    /**
     * A handy endpoint that extends platform release dates. Used to dig deeper into release dates, platforms and
     * versions
     */
    public const val PLATFORM_VERSION_RELEASE_DATE: String = "platform_version_release_dates"

    /**
     * The main website for the platform
     */
    public const val PLATFORM_WEBSITE: String = "platform_websites"

    /**
     * Logo for a platform
     */
    public const val PLATFORM_LOGO: String = "platform_logos"

    /**
     * Player perspectives describe the view/perspective of the player in a video game
     */
    public const val PLAYER_PERSPECTIVE: String = "player_perspectives"

    /**
     * Region for game localization
     */
    public const val REGION: String = "regions"

    /**
     * A handy endpoint that extends game release dates. Used to dig deeper into release dates, platforms and versions
     */
    public const val RELEASE_DATE: String = "release_dates"

    /**
     * An endpoint to provide definition of all of the current release date statuses
     */
    public const val RELEASE_DATE_STATUS: String = "release_date_statuses"

    /**
     * Screenshots of games
     */
    public const val SCREENSHOT: String = "screenshots"

    /**
     * Search
     */
    public const val SEARCH: String = "search"

    /**
     * Video game themes
     */
    public const val THEME: String = "themes"

    /**
     * A website url, usually associated with a game
     */
    public const val WEBSITE: String = "websites"

    /**
     * Endpoint to get all registered webhooks
     */
    public const val WEBHOOKS: String = "webhooks"

    /**
     * Endpoint to execute multi-queries
     */
    public const val MULTIQUERY: String = "multiquery"

    public fun getProtobufEndpoint(@IgdbEndpoint endpoint: String): String = "$endpoint.pb"

    public fun getJsonEndpoint(@IgdbEndpoint endpoint: String): String = endpoint

    public fun getWebhookEndpoint(@IgdbEndpoint endpoint: String): String = "$endpoint/webhooks"

    public fun getTestWebhookEndpoint(
        @IgdbEndpoint endpoint: String,
        entityId: String,
    ): String = "$endpoint/webhooks/test/$entityId"

    public fun getRemoveWebhookEndpoint(webhookId: String): String = "$WEBHOOKS/$webhookId"

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        AGE_RATING,
        AGE_RATING_CONTENT_DESCRIPTION,
        ALTERNATIVE_NAME,
        ARTWORK,
        CHARACTER,
        CHARACTER_MUG_SHOT,
        COLLECTION,
        COMPANY,
        COMPANY_LOGO,
        COMPANY_WEBSITE,
        COVER,
        EXTERNAL_GAME,
        FRANCHISE,
        GAME_ENGINE,
        GAME_ENGINE_LOGO,
        GAME,
        GAME_LOCALIZATION,
        GAME_MODE,
        GAME_VERSION,
        GAME_VERSION_FEATURE,
        GAME_VIDEO,
        GAME_VERSION_FEATURE_VALUE,
        GENRE,
        INVOLVED_COMPANY,
        LANGUAGE,
        KEYWORD,
        LANGUAGE_SUPPORT,
        LANGUAGE_SUPPORT_TYPE,
        MULTIPLAYER_MODE,
        PLATFORM,
        PLATFORM_VERSION,
        PLATFORM_FAMILY,
        PLATFORM_VERSION_COMPANY,
        PLATFORM_VERSION_RELEASE_DATE,
        PLATFORM_WEBSITE,
        PLATFORM_LOGO,
        PLAYER_PERSPECTIVE,
        REGION,
        RELEASE_DATE,
        RELEASE_DATE_STATUS,
        SCREENSHOT,
        SEARCH,
        THEME,
        WEBSITE,
        WEBHOOKS,
        MULTIQUERY,
    )
    public annotation class IgdbEndpoint
}
