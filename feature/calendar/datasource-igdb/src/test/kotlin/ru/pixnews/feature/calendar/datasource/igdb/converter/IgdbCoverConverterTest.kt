/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Color
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCoverFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.cover.immortalsOfAveum
import ru.pixnews.igdbclient.model.Cover

class IgdbCoverConverterTest {
    @Test
    fun `toIgdbImageUrl() should convert image urls`() {
        val result = IgdbCoverFixtures.immortalsOfAveum.toIgdbImageUrl()

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size
            .shouldNotBeNull()
            .shouldBeEqual(CanvasSize(width = 1200U, height = 1600U))
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/co6dtk.webp",
        )
    }

    @Test
    fun `toIgdbImageUrl() should convert image urls with minimal fields`() {
        val cover = Cover(image_id = "co6dtk")

        val result = cover.toIgdbImageUrl()

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size.shouldBeNull()
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/co6dtk.webp",
        )
    }

    @Test
    fun `toIgdbImageUrl() should fail on no fields`() {
        shouldThrow<IllegalArgumentException> { Cover(id = 1L).toIgdbImageUrl() }
    }
}
