/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameVideoFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamevideo.immortalsOfAvenumTeaser
import ru.pixnews.igdbclient.model.GameVideo as IgdbGameVideo

class IgdbGameVideoConverterTest {
    @Test
    fun `toIgdbVideoUrl() should convert video urls`() {
        val videoUrl = IgdbGameVideoFixtures.immortalsOfAvenumTeaser
        val result = videoUrl.toIgdbVideoUrl()

        result.getUrl() shouldBe "https://www.youtube.com/watch?v=O2yGRgEO1nQ"
    }

    @Test
    fun `toIgdbVideoUrl() should convert video urls with minimal fields`() {
        val videoUrl = IgdbGameVideo(video_id = "mNP3ztvNBFI")
        val result = videoUrl.toIgdbVideoUrl()

        result.getUrl() shouldBe "https://www.youtube.com/watch?v=mNP3ztvNBFI"
    }

    @Test
    fun `toIgdbVideoUrl() should fail if required fields are not requested`() {
        shouldThrow<IllegalArgumentException> { IgdbGameVideo(id = 100).toIgdbVideoUrl() }
    }
}
