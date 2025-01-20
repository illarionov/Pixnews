/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.model.Genre
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameGenresConverter.convertGenreToGameGenre
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGenreFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.genre.shooter

class IgdbGameGenresConverterTest {
    @Test
    fun `IgdbGenreConverter should convert genres`() {
        val genre = IgdbGenreFixtures.shooter
        val result = convertGenreToGameGenre(genre)
        result.shouldBe(GameGenre("Shooter"))
    }

    @Test
    fun `IgdbGenreConverter should convert genres with minimal fields defined`() {
        val genre = Genre(name = "Adventure")
        val result = convertGenreToGameGenre(genre)
        result.shouldBe(GameGenre("Adventure"))
    }

    @Test
    fun `IgdbGenreConverter should fail if required fields are not requested`() {
        shouldThrow<IllegalArgumentException> { convertGenreToGameGenre(Genre(id = 1)) }
    }
}
