/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.game.GameGenre
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGenreFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.genre.shooter
import ru.pixnews.igdbclient.model.Genre as IgdbGenre

class IgdbGenreConverterTest {
    @Test
    fun `toGameGenre() should convert genres`() {
        val genre = IgdbGenreFixtures.shooter
        val result = genre.toGameGenre()
        result.shouldBe(GameGenre("Shooter"))
    }

    @Test
    fun `toGameGenre() should convert genres with minimal fields defined`() {
        val genre = IgdbGenre(name = "Adventure")
        val result = genre.toGameGenre()
        result.shouldBe(GameGenre("Adventure"))
    }

    @Test
    fun `toGameGenre() should fail if required fields are not requested`() {
        shouldThrow<IllegalArgumentException> { IgdbGenre(id = 1).toGameGenre() }
    }
}
