/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import at.released.igdbclient.model.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.game.GameTag
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameTagsConverter.convertIgdbThemeToGameTag
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbThemeFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.theme.action

class IgdbGameTagsConverterTest {
    @Test
    fun `IgdbThemeConverter should convert tags`() {
        val theme = IgdbThemeFixtures.action
        val result = convertIgdbThemeToGameTag(theme)
        result.shouldBe(GameTag("Action"))
    }

    @Test
    fun `IgdbThemeConverter should convert tags with minimal fields defined`() {
        val theme = Theme(name = "Sandbox")
        val result = convertIgdbThemeToGameTag(theme)
        result.shouldBe(GameTag("Sandbox"))
    }

    @Test
    fun `IgdbThemeConverter should fail if required fields are not requested`() {
        val theme = Theme(id = 38)

        shouldThrow<IllegalArgumentException> {
            convertIgdbThemeToGameTag(theme)
        }
    }
}
