/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.field.builder

import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbFieldDsl
import ru.pixnews.feature.calendar.datasource.igdb.field.IgdbRequestField
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.Game as IgdbGame

private val rootIgdbGameFieldBuilder: IgdbGameFieldBuilder = IgdbGameFieldBuilder()
public val IgdbGame.Companion.field: IgdbGameFieldBuilder
    get() = rootIgdbGameFieldBuilder

@Suppress("VariableNaming")
@IgdbFieldDsl
public class IgdbGameFieldBuilder internal constructor(
    parent: IgdbRequestField<*>? = null,
) : IgdbRequestFieldBuilder<IgdbGame>(parent) {
    public val id: IgdbRequestField<IgdbGame> get() = named("id")
    public val age_ratings: IgdbAgeRatingsFieldBuilder
        get() = IgdbAgeRatingsFieldBuilder(named("age_ratings"))
    public val aggregated_rating: IgdbRequestField<IgdbGame> = named("aggregated_rating")
    public val aggregated_rating_count: IgdbRequestField<IgdbGame> = named("aggregated_rating_count")
    public val alternative_names: IgdbRequestField<IgdbGame> = named("alternative_names")
    public val artworks: IgdbRequestField<IgdbGame> = named("artworks")
    public val bundles: IgdbRequestField<IgdbGame> = named("bundles")
    public val category: IgdbRequestField<IgdbGame> = named("category")
    public val collection: IgdbRequestField<IgdbGame> = named("collection")
    public val cover: IgdbRequestField<IgdbGame> get() = named("cover")
    public val created_at: IgdbRequestField<IgdbGame> get() = named("created_at")
    public val dlcs: IgdbRequestField<IgdbGame> get() = named("dlcs")
    public val expansions: IgdbRequestField<IgdbGame> get() = named("expansions")
    public val external_games: IgdbRequestField<IgdbGame> get() = named("external_games")
    public val first_release_date: IgdbRequestField<IgdbGame> = named("first_release_date")
    public val follows: IgdbRequestField<IgdbGame> get() = named("follows")
    public val franchise: IgdbRequestField<IgdbGame> get() = named("franchise")
    public val franchises: IgdbRequestField<IgdbGame> get() = named("franchises")
    public val game_engines: IgdbRequestField<IgdbGame> get() = named("game_engines")
    public val game_modes: IgdbRequestField<IgdbGame> get() = named("game_modes")
    public val genres: IgdbRequestField<IgdbGame> get() = named("genres")
    public val hypes: IgdbRequestField<IgdbGame> get() = named("hypes")
    public val involved_companies: IgdbRequestField<IgdbGame> = named("involved_companies")
    public val keywords: IgdbRequestField<IgdbGame> get() = named("keywords")
    public val multiplayer_modes: IgdbRequestField<IgdbGame> get() = named("multiplayer_modes")
    public val name: IgdbRequestField<IgdbGame> get() = named("name")
    public val parent_game: IgdbRequestField<IgdbGame> get() = named("parent_game")
    public val platforms: IgdbRequestField<IgdbGame> get() = named("platforms")
    public val player_perspectives: IgdbRequestField<IgdbGame> get() = named("player_perspectives")
    public val rating: IgdbRequestField<IgdbGame> get() = named("rating")
    public val rating_count: IgdbRequestField<IgdbGame> get() = named("rating_count")
    public val release_dates: IgdbRequestField<IgdbGame> get() = named("release_dates")
    public val screenshots: IgdbRequestField<IgdbGame> get() = named("screenshots")
    public val similar_games: IgdbRequestField<IgdbGame> get() = named("similar_games")
    public val standalone_expansions: IgdbRequestField<IgdbGame> get() = named("standalone_expansions")
    public val slug: IgdbRequestField<IgdbGame> get() = named("slug")
    public val status: IgdbRequestField<IgdbGame> get() = named("status")
    public val storyline: IgdbRequestField<IgdbGame> get() = named("storyline")
    public val summary: IgdbRequestField<IgdbGame> get() = named("summary")
    public val tags: IgdbRequestField<IgdbGame> get() = named("tags")
    public val themes: IgdbRequestField<IgdbGame> get() = named("themes")
    public val total_rating: IgdbRequestField<IgdbGame> get() = named("total_rating")
    public val total_rating_count: IgdbRequestField<IgdbGame> = named("total_rating_count")
    public val updated_at: IgdbRequestField<IgdbGame> get() = named("updated_at")
    public val url: IgdbRequestField<IgdbGame> get() = named("url")
    public val version_parent: IgdbRequestField<IgdbGame> get() = named("version_parent")
    public val version_title: IgdbRequestField<IgdbGame> get() = named("version_title")
    public val videos: IgdbRequestField<IgdbGame> get() = named("videos")
    public val websites: IgdbRequestField<IgdbGame> get() = named("websites")
    public val checksum: IgdbRequestField<IgdbGame> get() = named("checksum")
    public val remakes: IgdbRequestField<IgdbGame> get() = named("remakes")
    public val remasters: IgdbRequestField<IgdbGame> get() = named("remasters")
    public val expanded_games: IgdbRequestField<IgdbGame> get() = named("expanded_games")
    public val ports: IgdbRequestField<IgdbGame> get() = named("ports")
    public val forks: IgdbRequestField<IgdbGame> get() = named("forks")
    public val language_supports: IgdbRequestField<IgdbGame> get() = named("language_supports")
    public val game_localizations: IgdbRequestField<IgdbGame> get() = named("game_localizations")

    private fun named(igdbFieldName: String): IgdbRequestField<Game> =
        IgdbRequestField(igdbFieldName, Game::class, parent)
}
