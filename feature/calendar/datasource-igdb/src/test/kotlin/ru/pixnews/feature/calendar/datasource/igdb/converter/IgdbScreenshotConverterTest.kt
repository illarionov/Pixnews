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
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbScreenshotFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.screenshot.gta1Full
import ru.pixnews.igdbclient.model.Screenshot as IgdbScreenshot

class IgdbScreenshotConverterTest {
    @Test
    fun `toIgdbImageUrl() should convert image urls`() {
        val result = IgdbScreenshotFixtures.gta1Full.toIgdbImageUrl()

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size
            .shouldNotBeNull()
            .shouldBeEqual(CanvasSize(width = 640U, height = 480U))
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/issnz2fcwsf6mr2x0pgu.webp",
        )
    }

    @Test
    fun `toIgdbImageUrl() should convert image urls with minimal fields`() {
        val result = IgdbScreenshot(image_id = "vyg5gb8whxurxjyfqfnf").toIgdbImageUrl()

        result.prevailingColor shouldBeEqual Color.Unspecified
        result.size.shouldBeNull()
        result.getUrl().shouldBeEqual(
            "https://images.igdb.com/igdb/image/upload/t_thumb/vyg5gb8whxurxjyfqfnf.webp",
        )
    }

    @Test
    fun `toIgdbImageUrl() should fail on no fields`() {
        val screenshot = IgdbScreenshot(id = 46108)
        shouldThrow<IllegalArgumentException> { screenshot.toIgdbImageUrl() }
    }
}
