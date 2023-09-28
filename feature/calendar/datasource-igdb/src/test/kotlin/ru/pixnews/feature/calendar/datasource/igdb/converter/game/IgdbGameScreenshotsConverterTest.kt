/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test
import ru.pixnews.domain.model.util.CanvasSize
import ru.pixnews.domain.model.util.Color
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameScreenshotsConverter.convertCoverToImageUrl
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameScreenshotsConverter.convertScreenshotToImageUrl
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCoverFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbScreenshotFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.cover.immortalsOfAveum
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.screenshot.gta1Full
import ru.pixnews.igdbclient.model.Cover
import ru.pixnews.igdbclient.model.Screenshot

class IgdbGameScreenshotsConverterTest {
    @Test
    fun `convertCoverToImageUrl should convert image urls`() {
        val result = convertCoverToImageUrl(IgdbCoverFixtures.immortalsOfAveum)

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size
            .shouldNotBeNull()
            .shouldBeEqual(CanvasSize(width = 1200U, height = 1600U))
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/co6dtk.webp",
        )
    }

    @Test
    fun `convertCoverToImageUrl should convert image urls with minimal fields`() {
        val cover = Cover(image_id = "co6dtk")

        val result = convertCoverToImageUrl(cover)

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size.shouldBeNull()
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/co6dtk.webp",
        )
    }

    @Test
    fun `convertCoverToImageUrl should fail on no fields`() {
        shouldThrow<IllegalArgumentException> { convertCoverToImageUrl(Cover(id = 1L)) }
    }

    @Test
    fun `should convert screenshot image urls`() {
        val result = convertScreenshotToImageUrl(IgdbScreenshotFixtures.gta1Full)

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size
            .shouldNotBeNull()
            .shouldBeEqual(CanvasSize(width = 640U, height = 480U))
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/issnz2fcwsf6mr2x0pgu.webp",
        )
    }

    @Test
    fun `should convert screenshot image urls with minimal fields`() {
        val result = convertScreenshotToImageUrl(
            Screenshot(image_id = "vyg5gb8whxurxjyfqfnf"),
        )

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size.shouldBeNull()
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/vyg5gb8whxurxjyfqfnf.webp",
        )
    }

    @Test
    fun `should fail on no fields on screenshot`() {
        val screenshot = Screenshot(id = 46108)
        shouldThrow<IllegalArgumentException> { convertScreenshotToImageUrl(screenshot) }
    }
}
