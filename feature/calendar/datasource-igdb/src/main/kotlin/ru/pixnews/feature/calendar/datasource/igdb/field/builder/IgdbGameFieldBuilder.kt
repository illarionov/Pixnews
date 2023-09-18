/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field.builder

import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbFieldDsl
import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbRequestField
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.AGE_RATINGS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.AGGREGATED_RATING
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.AGGREGATED_RATING_COUNT
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.ALTERNATIVE_NAMES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.ARTWORKS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.BUNDLES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.CATEGORY
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.CHECKSUM
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.COLLECTION
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.COVER
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.CREATED_AT
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.DSLCS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.EXPANDED_GAMES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.EXPANSIONS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.EXTERNAL_GAMES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.FIRST_RELEASE_DATE
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.FOLLOWS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.FORKS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.FRANCHISE
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.FRANCHISES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.GAME_ENGINES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.GAME_LOCALIZATIONS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.GAME_MODES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.GENRES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.HYPES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.ID
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.INVOLVED_COMPANIES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.KEYWORDS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.LANGUAGE_SUPPORTS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.MULTIPLAYER_MODES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.NAME
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.PARENT_GAME
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.PLATFORMS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.PLAYER_PERSPECTIVES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.PORTS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.RATING
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.RATING_COUNT
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.RELEASE_DATES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.REMAKES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.REMASTERS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.SCREENSHOTS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.SIMILAR_GAMES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.SLUG
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.STANDALONE_EXPANSIONS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.STATUS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.STORYLINE
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.SUMMARY
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.TAGS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.THEMES
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.TOTAL_RATING
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.TOTAL_RATING_COUNT
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.UPDATED_AT
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.URL
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.VERSION_PARENT
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.VERSION_TITLE
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.VIDEOS
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbGameField.WEBSITES
import ru.pixnews.igdbclient.model.Game as IgdbGame

private val rootIgdbGameFieldBuilder: IgdbGameFieldBuilder = IgdbGameFieldBuilder()
public val IgdbGame.Companion.field: IgdbGameFieldBuilder
    get() = rootIgdbGameFieldBuilder

@Suppress("VariableNaming")
@IgdbFieldDsl
public class IgdbGameFieldBuilder internal constructor(
    parent: IgdbRequestField<*>? = null,
) : IgdbRequestFieldBuilder<IgdbGame>(parent) {
    public val id: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(ID, parent)

    public val age_ratings: IgdbAgeRatingsFieldBuilder
        get() = IgdbAgeRatingsFieldBuilder(IgdbRequestField(AGE_RATINGS, parent))

    public val aggregated_rating: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(AGGREGATED_RATING, parent)

    public val aggregated_rating_count: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(AGGREGATED_RATING_COUNT, parent)

    public val alternative_names: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(ALTERNATIVE_NAMES, parent)

    public val artworks: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(ARTWORKS, parent)

    public val bundles: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(BUNDLES, parent)

    public val category: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(CATEGORY, parent)

    public val collection: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(COLLECTION, parent)

    public val cover: IgdbRequestField<IgdbGame> get() = IgdbRequestField(COVER, parent)
    public val created_at: IgdbRequestField<IgdbGame> get() = IgdbRequestField(CREATED_AT, parent)
    public val dlcs: IgdbRequestField<IgdbGame> get() = IgdbRequestField(DSLCS, parent)
    public val expansions: IgdbRequestField<IgdbGame> get() = IgdbRequestField(EXPANSIONS, parent)
    public val external_games: IgdbRequestField<IgdbGame> get() = IgdbRequestField(EXTERNAL_GAMES, parent)
    public val first_release_date: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            FIRST_RELEASE_DATE,
            parent,
        )
    public val follows: IgdbRequestField<IgdbGame> get() = IgdbRequestField(FOLLOWS, parent)
    public val franchise: IgdbRequestField<IgdbGame> get() = IgdbRequestField(FRANCHISE, parent)
    public val franchises: IgdbRequestField<IgdbGame> get() = IgdbRequestField(FRANCHISES, parent)
    public val game_engines: IgdbRequestField<IgdbGame> get() = IgdbRequestField(GAME_ENGINES, parent)
    public val game_modes: IgdbRequestField<IgdbGame> get() = IgdbRequestField(GAME_MODES, parent)
    public val genres: IgdbRequestField<IgdbGame> get() = IgdbRequestField(GENRES, parent)
    public val hypes: IgdbRequestField<IgdbGame> get() = IgdbRequestField(HYPES, parent)
    public val involved_companies: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            INVOLVED_COMPANIES,
            parent,
        )
    public val keywords: IgdbRequestField<IgdbGame> get() = IgdbRequestField(KEYWORDS, parent)
    public val multiplayer_modes: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            MULTIPLAYER_MODES,
            parent,
        )
    public val name: IgdbRequestField<IgdbGame> get() = IgdbRequestField(NAME, parent)
    public val parent_game: IgdbRequestField<IgdbGame> get() = IgdbRequestField(PARENT_GAME, parent)
    public val platforms: IgdbRequestField<IgdbGame> get() = IgdbRequestField(PLATFORMS, parent)
    public val player_perspectives: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            PLAYER_PERSPECTIVES,
            parent,
        )
    public val rating: IgdbRequestField<IgdbGame> get() = IgdbRequestField(RATING, parent)
    public val rating_count: IgdbRequestField<IgdbGame> get() = IgdbRequestField(RATING_COUNT, parent)
    public val release_dates: IgdbRequestField<IgdbGame> get() = IgdbRequestField(RELEASE_DATES, parent)
    public val screenshots: IgdbRequestField<IgdbGame> get() = IgdbRequestField(SCREENSHOTS, parent)
    public val similar_games: IgdbRequestField<IgdbGame> get() = IgdbRequestField(SIMILAR_GAMES, parent)
    public val standalone_expansions: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            STANDALONE_EXPANSIONS,
            parent,
        )
    public val slug: IgdbRequestField<IgdbGame> get() = IgdbRequestField(SLUG, parent)
    public val status: IgdbRequestField<IgdbGame> get() = IgdbRequestField(STATUS, parent)
    public val storyline: IgdbRequestField<IgdbGame> get() = IgdbRequestField(STORYLINE, parent)
    public val summary: IgdbRequestField<IgdbGame> get() = IgdbRequestField(SUMMARY, parent)
    public val tags: IgdbRequestField<IgdbGame> get() = IgdbRequestField(TAGS, parent)
    public val themes: IgdbRequestField<IgdbGame> get() = IgdbRequestField(THEMES, parent)
    public val total_rating: IgdbRequestField<IgdbGame> get() = IgdbRequestField(TOTAL_RATING, parent)
    public val total_rating_count: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            TOTAL_RATING_COUNT,
            parent,
        )
    public val updated_at: IgdbRequestField<IgdbGame> get() = IgdbRequestField(UPDATED_AT, parent)
    public val url: IgdbRequestField<IgdbGame> get() = IgdbRequestField(URL, parent)
    public val version_parent: IgdbRequestField<IgdbGame> get() = IgdbRequestField(VERSION_PARENT, parent)
    public val version_title: IgdbRequestField<IgdbGame> get() = IgdbRequestField(VERSION_TITLE, parent)
    public val videos: IgdbRequestField<IgdbGame> get() = IgdbRequestField(VIDEOS, parent)
    public val websites: IgdbRequestField<IgdbGame> get() = IgdbRequestField(WEBSITES, parent)
    public val checksum: IgdbRequestField<IgdbGame> get() = IgdbRequestField(CHECKSUM, parent)
    public val remakes: IgdbRequestField<IgdbGame> get() = IgdbRequestField(REMAKES, parent)
    public val remasters: IgdbRequestField<IgdbGame> get() = IgdbRequestField(REMASTERS, parent)
    public val expanded_games: IgdbRequestField<IgdbGame> get() = IgdbRequestField(EXPANDED_GAMES, parent)
    public val ports: IgdbRequestField<IgdbGame> get() = IgdbRequestField(PORTS, parent)
    public val forks: IgdbRequestField<IgdbGame> get() = IgdbRequestField(FORKS, parent)
    public val language_supports: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            LANGUAGE_SUPPORTS,
            parent,
        )
    public val game_localizations: IgdbRequestField<IgdbGame>
        get() = IgdbRequestField(
            GAME_LOCALIZATIONS,
            parent,
        )
}
