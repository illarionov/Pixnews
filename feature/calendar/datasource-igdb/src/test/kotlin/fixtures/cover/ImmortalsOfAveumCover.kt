/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.cover

import at.released.igdbclient.model.Cover
import at.released.igdbclient.model.Game
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCoverFixtures

internal val IgdbCoverFixtures.immortalsOfAveum get() = Cover(
    id = 297848,
    alpha_channel = false,
    animated = false,
    game = Game(id = 228531),
    height = 1600,
    image_id = "co6dtk",
    url = "//images.igdb.com/igdb/image/upload/t_thumb/co6dtk.jpg",
    width = 1200,
    checksum = "b143d932-58bb-c526-4878-28c1c10e7f19",
)
