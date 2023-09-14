/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.game.GameSeriesSummary
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.Ref.FullObject
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCollectionFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.collection.finalFantasyCollection
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameId
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameSeriesSummaryId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.igdbclient.model.Collection as IgdbCollection

class IgdbCollectionConverterTest {
    @Test
    fun `toGamesSeriesSummaryRef() should convert collections`() {
        val result = IgdbCollectionFixtures.finalFantasyCollection.toGamesSeriesSummaryRef()

        result shouldBeEqual FullObject(
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
                ).map { Ref.Id(IgdbGameId(it)) }.toImmutableList(),
                attribution = persistentListOf(igdbDataSource),
            ),
        )
    }

    @Test
    fun `toGamesSeriesSummary should convert collections with minimal fields`() {
        val collection = IgdbCollection(
            id = 5134,
            name = "Compilation of Final Fantasy VII",
        )

        val result = collection.toGamesSeriesSummaryRef()
        result shouldBeEqual FullObject(
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
    fun `toGamesSeriesSummary should fail when required fields are not requested`() {
        listOf(
            IgdbCollection(id = 0, name = "Compilation of Final Fantasy VII"),
        ).forEach {
            shouldThrow<IllegalArgumentException> { it.toGamesSeriesSummaryRef() }
        }
    }
}
