/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.datasource.DataSource
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.url.VideoUrl
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.library.kotlin.utils.isNotWhitespaceOnly

public data class Game(
    val id: GameId,
    val name: Localized<String> = Localized.EMPTY_STRING,
    val summary: Localized<RichText> = Localized.EMPTY_RICH_TEXT,
    val description: Localized<RichText> = Localized.EMPTY_RICH_TEXT,

    val videoUrls: ImmutableList<VideoUrl> = persistentListOf(),
    val screenshots: ImmutableList<ImageUrl> = persistentListOf(),

    val developer: Company? = null,
    val publisher: Company? = null,

    val releaseDate: ApproximateDate = ApproximateDate.Unknown(),
    val releaseStatus: GameReleaseStatus? = null,

    val genres: ImmutableSet<GameGenre> = persistentSetOf(),
    val tags: ImmutableSet<GameTag> = persistentSetOf(),

    val ratings: RatingsSummary? = null,
    val links: ImmutableList<ExternalLink> = persistentListOf(),

    val category: GameReleaseCategory? = null,
    val parentGame: Ref<Game>? = null,
    val series: Ref<GameSeriesSummary>? = null,

    val platforms: ImmutableSet<Ref<GamePlatform>> = persistentSetOf(),
    val ageRanking: AgeRating? = null,

    val localizations: GameLocalizations? = null,
    val gameMode: ImmutableSet<Ref<GameMode>> = persistentSetOf(),
    val playerPerspectives: ImmutableSet<Ref<PlayerPerspective>> = persistentSetOf(),
    val systemRequirements: GameSystemRequirements? = null,

    val dataSources: ImmutableList<DataSource> = persistentListOf(),
) {
    init {
        require(name.value.isNotWhitespaceOnly())
        require(description.value.raw.isNotWhitespaceOnly())
    }

    public companion object {
        public enum class GameField {
            ID,
            NAME,
            SUMMARY,
            DESCRIPTION,
            VIDE_URLS,
            SCREENSHOTS,
            DEVELOPER,
            PUBLISHER,
            RELEASE_DATE,
            RELEASE_STATUS,
            GENRES,
            TAGS,
            RATINGS,
            LINKS,
            CATEGORY,
            PARENT_GAME,
            SERIES,
            PLATFORMS,
            AGE_RANKING,
            LOCALIZATIONS,
            GAME_MODE,
            PLAYER_PERSPECTIVES,
            SYSTEM_REQUIREMENTS,
        }
    }
}
