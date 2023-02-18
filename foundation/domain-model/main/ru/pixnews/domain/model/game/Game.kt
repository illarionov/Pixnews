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
package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.domain.model.datasource.DataSources
import ru.pixnews.domain.model.developer.GameDeveloper
import ru.pixnews.domain.model.links.ExternalLinks
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.ranking.GameAgeRanking
import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url
import ru.pixnews.libraries.kotlin.utils.isNotWhitespaceOnly

public data class Game(
    val id: GameId,
    val name: Localized<String>,
    val description: Localized<RichText>,

    val trailerUrls: ImmutableList<Url>,
    val screenshots: ImmutableList<ImageUrl>,

    val developer: GameDeveloper?,
    val publisher: GameDeveloper?,

    val releaseDate: GameReleaseDate,

    val genres: ImmutableSet<GameGenre>,
    val tags: ImmutableSet<GameTag>,

    val ratings: RatingsSummary,
    val links: ExternalLinks<GameId>,

    val dataSources: DataSources,

    val platforms: ImmutableSet<GamePlatform>,
    val ageRanking: GameAgeRanking?,

    val series: GameSeriesSummary?,
    val supportedLanguages: SupportedLanguages?,
    val gameMode: ImmutableSet<GameMode>,
    val systemRequirements: GameSystemRequirements?,
) {
    init {
        require(name.value.isNotWhitespaceOnly())
        require(description.value.raw.isNotWhitespaceOnly())
    }
}
