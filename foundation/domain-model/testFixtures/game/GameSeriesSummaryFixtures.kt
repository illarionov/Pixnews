/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.game

import kotlinx.collections.immutable.persistentListOf
import ru.pixnews.domain.model.datasource.DataSourceFixtures
import ru.pixnews.domain.model.datasource.igdb
import ru.pixnews.domain.model.id.GameSeriesSummaryId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.library.internationalization.language.LanguageCode

public object GameSeriesSummaryFixtures

public val GameSeriesSummaryFixtures.halfLife: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesSummaryId(stringValue = "half-life"),
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
        id = GameSeriesSummaryId("grand-theft-auto"),
        name = Localized("Grand Theft Auto", LanguageCode.ENGLISH),
        totalGamesCount = 20U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.sims: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesSummaryId("the-sims"),
        name = Localized("The Sims", LanguageCode.ENGLISH),
        totalGamesCount = 84U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.beyondGoodEvil: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesSummaryId("beyond-good-evil"),
        name = Localized("Beyond Good & Evil", LanguageCode.ENGLISH),
        totalGamesCount = 3U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.slimeRancher: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesSummaryId("slime-rancher"),
        name = Localized("Slime Rancher", LanguageCode.ENGLISH),
        totalGamesCount = 3U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )

public val GameSeriesSummaryFixtures.jamesBond: GameSeriesSummary
    get() = GameSeriesSummary(
        id = GameSeriesSummaryId("james-bond"),
        name = Localized("James Bond", LanguageCode.ENGLISH),
        totalGamesCount = 37U,
        games = persistentListOf(),
        attribution = DataSourceFixtures.igdb,
    )
