/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.feature.calendar.datasource.igdb.converter.game.IgdbGameVideoUrlsConverter.convertGameVideoToVideoUrl
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameVideoFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamevideo.immortalsOfAvenumTeaser
import ru.pixnews.igdbclient.model.GameVideo

class IgdbGameVideoUrlsConverterTest {
    @Test
    fun `should convert video urls`() {
        val videoUrl = IgdbGameVideoFixtures.immortalsOfAvenumTeaser
        val result = convertGameVideoToVideoUrl(videoUrl)

        result.getUrl() shouldBe "https://www.youtube.com/watch?v=O2yGRgEO1nQ"
    }

    @Test
    fun `should convert video urls with minimal fields`() {
        val videoUrl = GameVideo(video_id = "mNP3ztvNBFI")
        val result = convertGameVideoToVideoUrl(videoUrl)

        result.getUrl() shouldBe "https://www.youtube.com/watch?v=mNP3ztvNBFI"
    }

    @Test
    fun `should fail if required fields are not requested`() {
        shouldThrow<IllegalArgumentException> { convertGameVideoToVideoUrl(GameVideo(id = 100)) }
    }
}
