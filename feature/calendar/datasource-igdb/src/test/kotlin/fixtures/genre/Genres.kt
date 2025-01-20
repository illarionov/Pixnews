/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.genre

import at.released.igdbclient.model.Genre
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGenreFixtures
import java.time.Instant

internal val IgdbGenreFixtures.shooter get() = Genre(
    id = 5,
    created_at = Instant.ofEpochSecond(12_9755_5200),
    name = "Shooter",
    slug = "shooter",
    updated_at = Instant.ofEpochSecond(13_2321_6000),
    url = "https://www.igdb.com/genres/shooter",
    checksum = "bb15fd3f-0f46-e5f3-2b40-d046cf9bd2ef",
)
internal val IgdbGenreFixtures.adventure get() = Genre(
    id = 31,
    created_at = Instant.ofEpochSecond(13_2356_1600),
    name = "Adventure",
    slug = "adventure",
    updated_at = Instant.ofEpochSecond(13_2356_1600),
    url = "https://www.igdb.com/genres/adventure",
    checksum = "a6d85192-8d11-bad3-cc5c-dd89e2f94a47",
)
