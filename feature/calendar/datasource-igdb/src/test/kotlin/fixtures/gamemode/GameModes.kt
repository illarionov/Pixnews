/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode

import at.released.igdbclient.model.GameMode
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameModeFixtures
import java.time.Instant

internal val IgdbGameModeFixtures.singlePlayer get() = GameMode(
    id = 1,
    created_at = Instant.ofEpochSecond(12_9893_7600),
    name = "Single player",
    slug = "single-player",
    updated_at = Instant.ofEpochSecond(13_2321_6000),
    url = "https://www.igdb.com/game_modes/single-player",
    checksum = "a43b1688-f968-3541-0897-9735ffde1745",
)
internal val IgdbGameModeFixtures.multiplayer get() = GameMode(
    id = 2,
    created_at = Instant.ofEpochSecond(12_9893_7600),
    name = "Multiplayer",
    slug = "multiplayer",
    updated_at = Instant.ofEpochSecond(13_2321_6000),
    url = "https://www.igdb.com/game_modes/multiplayer",
    checksum = "288b548c-11e4-d910-f037-21d4e6a33b38",
)
internal val IgdbGameModeFixtures.coOperative get() = GameMode(
    id = 3,
    created_at = Instant.ofEpochSecond(12_9893_7600),
    name = "Co-operative",
    slug = "co-operative",
    updated_at = Instant.ofEpochSecond(13_2321_6000),
    url = "https://www.igdb.com/game_modes/co-operative",
    checksum = "e8329d55-33e6-f7ae-ae09-2848cb7ccc90",
)
internal val IgdbGameModeFixtures.splitScreen get() = GameMode(
    id = 4,
    created_at = Instant.ofEpochSecond(12_9893_7600),
    name = "Split screen",
    slug = "split-screen",
    updated_at = Instant.ofEpochSecond(13_2321_6000),
    url = "https://www.igdb.com/game_modes/split-screen",
    checksum = "98247b75-6f46-7c77-521f-0945c684d842",
)
internal val IgdbGameModeFixtures.massivelyMultiplayerOnlineMmo get() = GameMode(
    id = 5,
    created_at = Instant.ofEpochSecond(12_9893_7600),
    name = "Massively Multiplayer Online (MMO)",
    slug = "massively-multiplayer-online-mmo",
    updated_at = Instant.ofEpochSecond(13_2321_6000),
    url = "https://www.igdb.com/game_modes/massively-multiplayer-online-mmo",
    checksum = "307d1126-6e3b-9215-06f1-10c8ecce05b4",
)
internal val IgdbGameModeFixtures.battleRoyale get() = GameMode(
    id = 6,
    created_at = Instant.ofEpochSecond(15_8880_9600),
    name = "Battle Royale",
    slug = "battle-royale",
    updated_at = Instant.ofEpochSecond(15_8880_9600),
    url = "https://www.igdb.com/game_modes/battle-royale",
    checksum = "bfc388ac-0502-ba05-af25-3dcc94a0acf3",
)
