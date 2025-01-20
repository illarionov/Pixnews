/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.model.Collection
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.game.GameSeriesSummary
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.domain.model.util.Ref.Id
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCollectionFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.collection.finalFantasyCollection
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameId
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameSeriesSummaryId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.library.internationalization.language.LanguageCode
import at.released.igdbclient.model.Game as IgdbGame

class IgdbGameSeriesConverterTest {
    @Test
    fun `should convert collections`() {
        val result = IgdbGameSeriesConverter.convert(
            IgdbGame(collection = IgdbCollectionFixtures.finalFantasyCollection),
        )

        result shouldBe FullObject(
            GameSeriesSummary(
                id = IgdbGameSeriesSummaryId(5134),
                name = Localized("Compilation of Final Fantasy VII", LanguageCode.ENGLISH),
                totalGamesCount = 9U,
                games = listOf(
                    2407L,
                    11169L,
                    18068L,
                    80033L,
                    141504L,
                    144024L,
                    144038L,
                    144040L,
                    146428L,
                ).map { Id<Game, GameId>(IgdbGameId(it)) }.toImmutableList(),
                attribution = persistentListOf(igdbDataSource),
            ),
        )
    }

    @Test
    fun `should convert collections with minimal fields`() {
        val collection = Collection(
            id = 5134,
            name = "Compilation of Final Fantasy VII",
        )

        val result = IgdbGameSeriesConverter.convert(
            IgdbGame(collection = collection),
        )

        result shouldBe FullObject(
            GameSeriesSummary(
                id = IgdbGameSeriesSummaryId(5134),
                name = Localized("Compilation of Final Fantasy VII", LanguageCode.ENGLISH),
                totalGamesCount = null,
                games = persistentListOf(),
                attribution = persistentListOf(igdbDataSource),
            ),
        )
    }

    @Test
    fun `should fail when required fields are not requested`() {
        listOf(
            Collection(id = 0, name = "Compilation of Final Fantasy VII"),
        ).forEach {
            shouldThrow<IllegalArgumentException> {
                IgdbGameSeriesConverter.convert(
                    IgdbGame(collection = it),
                )
            }
        }
    }
}
