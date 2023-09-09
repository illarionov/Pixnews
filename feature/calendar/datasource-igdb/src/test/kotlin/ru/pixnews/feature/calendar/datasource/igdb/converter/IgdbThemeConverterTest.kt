/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbThemeFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.theme.action
import ru.pixnews.igdbclient.model.Theme as IgdbTheme

class IgdbThemeConverterTest {
    @Test
    fun `toGameTag() should convert tags`() {
        val theme = IgdbThemeFixtures.action
        val result = theme.toGameTag()
        result.shouldBe(GameTag("Action"))
    }

    @Test
    fun `toGameTag() should convert tags with minimal fields defined`() {
        val theme = IgdbTheme(name = "Sandbox")
        val result = theme.toGameTag()
        result.shouldBe(GameTag("Sandbox"))
    }

    @Test
    fun `toGameTag() should fail if required fields are not requested`() {
        val theme = IgdbTheme(id = 38)

        shouldThrow<IllegalArgumentException> {
            theme.toGameTag()
        }
    }
}
