/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.theme

import at.released.igdbclient.model.Theme
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbThemeFixtures
import java.time.Instant

internal val IgdbThemeFixtures.action
    get() = Theme(
        id = 1,
        created_at = Instant.ofEpochSecond(13_2252_4800),
        name = "Action",
        slug = "action",
        updated_at = Instant.ofEpochSecond(13_2321_6000),
        url = "https://www.igdb.com/themes/action",
        checksum = "c177e0ff-f29a-2a2e-fce5-f945258ceb59",
    )
internal val IgdbThemeFixtures.fantasy
    get() = Theme(
        id = 17,
        created_at = Instant.ofEpochSecond(1_322_524_800),
        name = "Fantasy",
        slug = "fantasy",
        updated_at = Instant.ofEpochSecond(1_323_216_000),
        url = "https://www.igdb.com/themes/action",
        checksum = "c177e0ff-f29a-2a2e-fce5-f945258ceb59",
    )
