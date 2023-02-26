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

import kotlinx.collections.immutable.persistentListOf
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized

public object GameSeriesSummaryFixtures

public val GameSeriesSummaryFixtures.halfLife: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesId(stringValue = "half-life"),
        name = Localized(
            value = "half-life",
            language = LanguageCode.ENGLISH,
        ),
        totalGamesCount = 14U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.gta: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesId("grand-theft-auto"),
        name = Localized("Grand Theft Auto", LanguageCode.ENGLISH),
        totalGamesCount = 20U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.sims: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesId("the-sims"),
        name = Localized("The Sims", LanguageCode.ENGLISH),
        totalGamesCount = 84U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.beyondGoodEvil: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesId("beyond-good-evil"),
        name = Localized("Beyond Good & Evil", LanguageCode.ENGLISH),
        totalGamesCount = 3U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.slimeRancher: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesId("slime-rancher"),
        name = Localized("Slime Rancher", LanguageCode.ENGLISH),
        totalGamesCount = 3U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.jamesBond: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesId("james-bond"),
        name = Localized("James Bond", LanguageCode.ENGLISH),
        totalGamesCount = 37U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )
