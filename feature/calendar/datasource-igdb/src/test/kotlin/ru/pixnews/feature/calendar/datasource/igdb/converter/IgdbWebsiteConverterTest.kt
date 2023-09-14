/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.url.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.url.Url
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbWebsiteFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.website.gtaWikipedia
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_TWITCH
import ru.pixnews.igdbclient.model.Website as IgdbWebsite

class IgdbWebsiteConverterTest {
    @ParameterizedTest
    @MethodSource("toExternalLinkTestSource")
    fun `toExternalLink() should convert external links`(testData: Pair<IgdbWebsite, ExternalLink>) {
        val result = testData.first.toExternalLink()
        result.shouldBeEqual(testData.second)
    }

    @Test
    fun `toExternalLink() should fail on no url`() {
        val igdbWebsite = IgdbWebsite()
        assertThrows<IllegalArgumentException> {
            igdbWebsite.toExternalLink()
        }
    }

    internal companion object {
        @JvmStatic
        fun toExternalLinkTestSource(): List<Pair<IgdbWebsite, ExternalLink>> = listOf(
            IgdbWebsiteFixtures.gtaWikipedia to ExternalLink(
                type = WIKIPEDIA,
                url = Url("https://en.wikipedia.org/wiki/Grand_Theft_Auto_(video_game)"),
            ),
            IgdbWebsite(
                category = WEBSITE_TWITCH,
                url = "https://www.twitch.tv/directory/game/Grand%20Theft%20Auto",
            ) to ExternalLink(
                type = TWITCH,
                url = Url("https://www.twitch.tv/directory/game/Grand%20Theft%20Auto"),
            ),
        )
    }
}
