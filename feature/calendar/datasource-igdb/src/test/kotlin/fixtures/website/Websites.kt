/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.website

import at.released.igdbclient.model.Game
import at.released.igdbclient.model.Website
import at.released.igdbclient.model.WebsiteCategoryEnum.WEBSITE_WIKIPEDIA
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbWebsiteFixtures

internal val IgdbWebsiteFixtures.gtaWikipedia: Website get() = Website(
    id = 107692,
    category = WEBSITE_WIKIPEDIA,
    game = Game(id = 980L),
    trusted = true,
    url = "https://en.wikipedia.org/wiki/Grand_Theft_Auto_(video_game)",
    checksum = "913f739b-0dc0-ee83-ad45-62d6c0655a50",
)
