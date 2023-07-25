/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.domain.model.company.Company
import ru.pixnews.domain.model.datasource.DataSource
import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.rating.AgeRating
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.VideoUrl
import ru.pixnews.library.kotlin.utils.isNotWhitespaceOnly

public data class Game(
    val id: GameId,
    val name: Localized<String>,
    val summary: Localized<RichText>,
    val description: Localized<RichText>,

    val videoUrls: ImmutableList<VideoUrl>,
    val screenshots: ImmutableList<ImageUrl>,

    val developer: Company?,
    val publisher: Company?,

    val releaseDate: ApproximateDate,
    val releaseStatus: GameReleaseStatus?,

    val genres: ImmutableSet<GameGenre>,
    val tags: ImmutableSet<GameTag>,

    val ratings: RatingsSummary,
    val links: ImmutableList<ExternalLink>,

    val category: GameReleaseCategory?,
    val parentGame: GameId?,
    val series: GameSeriesSummary?,

    val platforms: ImmutableSet<GamePlatform>,
    val ageRanking: AgeRating?,

    val localizations: GameLocalizations?,
    val gameMode: ImmutableSet<GameMode>,
    val playerPerspectives: ImmutableSet<PlayerPerspective>,
    val systemRequirements: GameSystemRequirements?,

    val dataSources: DataSource,
) {
    init {
        require(name.value.isNotWhitespaceOnly())
        require(description.value.raw.isNotWhitespaceOnly())
    }
}
