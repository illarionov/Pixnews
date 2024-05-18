/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.playerperspective

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbPlayerPerspectiveFixtures
import ru.pixnews.igdbclient.model.PlayerPerspective
import java.time.Instant

internal val IgdbPlayerPerspectiveFixtures.firstPerson
    get() = PlayerPerspective(
        id = 1,
        created_at = Instant.ofEpochSecond(12_9893_7600),
        name = "First person",
        slug = "first-person",
        updated_at = Instant.ofEpochSecond(13_2321_6000),
        url = "https://www.igdb.com/player_perspectives/first-person",
        checksum = "4e23cb22-7a70-effb-b8e1-151317c6cdbd",
    )
internal val IgdbPlayerPerspectiveFixtures.thirdPerson
    get() = PlayerPerspective(
        id = 2,
        created_at = Instant.ofEpochSecond(12_9893_7600),
        name = "Third person",
        slug = "third-person",
        updated_at = Instant.ofEpochSecond(13_2321_6000),
        url = "https://www.igdb.com/player_perspectives/third-person",
        checksum = "2788b856-580c-66d0-bef3-d6169034f175",
    )
internal val IgdbPlayerPerspectiveFixtures.isometric
    get() = PlayerPerspective(
        id = 3,
        created_at = Instant.ofEpochSecond(12_9893_7600),
        name = "Bird view / Isometric",
        slug = "bird-view-slash-isometric",
        updated_at = Instant.ofEpochSecond(15_8250_2400),
        url = "https://www.igdb.com/player_perspectives/bird-view-slash-isometric",
        checksum = "83c59132-6edd-150f-a25f-a86ddf6a0da3",
    )
internal val IgdbPlayerPerspectiveFixtures.sideView
    get() = PlayerPerspective(
        id = 4,
        created_at = Instant.ofEpochSecond(12_9893_7600),
        name = "Side view",
        slug = "side-view",
        updated_at = Instant.ofEpochSecond(13_2321_6000),
        url = "https://www.igdb.com/player_perspectives/side-view",
        checksum = "2d038ac6-36c5-af5b-83fd-11a88a638631",
    )
internal val IgdbPlayerPerspectiveFixtures.text
    get() = PlayerPerspective(
        id = 5,
        created_at = Instant.ofEpochSecond(13_2122_8800),
        name = "Text",
        slug = "text",
        updated_at = Instant.ofEpochSecond(13_2321_6000),
        url = "https://www.igdb.com/player_perspectives/side-view",
        checksum = "e2df3906-bca8-e0d1-ca3b-67e1c5cfb334",
    )
internal val IgdbPlayerPerspectiveFixtures.auditory
    get() = PlayerPerspective(
        id = 6,
        created_at = Instant.ofEpochSecond(14_1315_8400),
        name = "Auditory",
        slug = "auditory",
        updated_at = Instant.ofEpochSecond(15_8250_2400),
        url = "https://www.igdb.com/player_perspectives/auditory",
        checksum = "74528cf1-64c3-cd78-18df-fae1594ee664",
    )
internal val IgdbPlayerPerspectiveFixtures.virtualReality
    get() = PlayerPerspective(
        id = 7,
        created_at = Instant.ofEpochSecond(14_6223_3600),
        name = "Virtual Reality",
        slug = "virtual-reality",
        updated_at = Instant.ofEpochSecond(14_6223_3600),
        url = "https://www.igdb.com/player_perspectives/virtual-reality",
        checksum = "963f8a54-d40f-1c9d-3bf4-d15267043693",
    )
