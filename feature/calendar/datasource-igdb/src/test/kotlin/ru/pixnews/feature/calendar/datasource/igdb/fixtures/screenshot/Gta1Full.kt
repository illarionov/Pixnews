/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.fixtures.screenshot

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbScreenshotFixtures
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.Screenshot

internal val IgdbScreenshotFixtures.gta1Full get() = Screenshot(
    id = 46106,
    alpha_channel = false,
    animated = false,
    game = Game(id = 980),
    height = 480,
    image_id = "issnz2fcwsf6mr2x0pgu",
    url = "//images.igdb.com/igdb/image/upload/t_thumb/issnz2fcwsf6mr2x0pgu.jpg",
    width = 640,
    checksum = "a8fc88f3-f8b5-db87-6be6-a6ed0aad6a96",
)
