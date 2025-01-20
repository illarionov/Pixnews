/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamevideo

import at.released.igdbclient.model.Game
import at.released.igdbclient.model.GameVideo
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameVideoFixtures

internal val IgdbGameVideoFixtures.immortalsOfAvenumTeaser
    get() = GameVideo(
        id = 80634,
        game = Game(id = 228531),
        name = "Teaser",
        video_id = "O2yGRgEO1nQ",
        checksum = "080b8238-ba2a-9a59-1052-54b5e97f5420",
    )
internal val IgdbGameVideoFixtures.immortalsOfAvenumTrailer
    get() = GameVideo(
        id = 87576,
        game = Game(id = 228531),
        name = "Trailer",
        video_id = "mNP3ztvNBFI",
        checksum = "5007096a-60f7-6246-6749-2dc9e1bb3471",
    )
