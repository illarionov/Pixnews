/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.collection

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCollectionFixtures
import ru.pixnews.igdbclient.model.Collection
import ru.pixnews.igdbclient.model.Game
import java.time.Instant

internal val IgdbCollectionFixtures.finalFantasyCollection get() = Collection(
    id = 5134,
    created_at = Instant.ofEpochSecond(1_513_067_965),
    games = listOf(
        2407L,
        11169L,
        18068L,
        80033L,
        141504L,
        144024L,
        144038L,
        144040L,
        146428L,
    ).map { Game(id = it) },
    name = "Compilation of Final Fantasy VII",
    slug = "compilation-of-final-fantasy-vii",
    updated_at = Instant.ofEpochSecond(1_693_985_923),
    url = "https://www.igdb.com/collections/compilation-of-final-fantasy-vii",
    checksum = "1aae648e-1e45-7a11-e78e-8ed78ff98e9a",
)
