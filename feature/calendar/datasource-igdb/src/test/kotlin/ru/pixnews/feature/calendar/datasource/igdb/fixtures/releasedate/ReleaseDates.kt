/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.fixtures.releasedate

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbReleaseDateFixtures
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMM
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMMDD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ1
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ2
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ3
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ4
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.Platform
import ru.pixnews.igdbclient.model.RegionRegionEnum.BRAZIL
import ru.pixnews.igdbclient.model.RegionRegionEnum.CHINA
import ru.pixnews.igdbclient.model.RegionRegionEnum.EUROPE
import ru.pixnews.igdbclient.model.RegionRegionEnum.NORTH_AMERICA
import ru.pixnews.igdbclient.model.RegionRegionEnum.WORLDWIDE
import ru.pixnews.igdbclient.model.ReleaseDate
import ru.pixnews.igdbclient.model.ReleaseDateStatus
import java.time.Instant

internal val IgdbReleaseDateFixtures.gta21Oct1997
    get() = ReleaseDate(
        id = 170059,
        category = YYYYMMMMDD,
        created_at = Instant.ofEpochSecond(15_5880_6610),
        date = Instant.ofEpochSecond(8_7739_2000),
        game = Game(id = 980),
        human = "Oct 21, 1997",
        m = 10,
        platform = Platform(id = 13),
        region = EUROPE,
        updated_at = Instant.ofEpochSecond(15_5891_1533),
        y = 1997,
        checksum = "6a2bebcc-2ecb-9051-5990-8a25548bd352",
    )
internal val IgdbReleaseDateFixtures.gtaCategory1YyyyMmmm
    get() = ReleaseDate(
        id = 170068,
        category = YYYYMMMM,
        created_at = Instant.ofEpochSecond(15_5880_6610),
        date = Instant.ofEpochSecond(10_4647_6800),
        game = Game(id = 980),
        human = "Mar 2003",
        m = 3,
        platform = Platform(id = 6),
        region = CHINA,
        updated_at = Instant.ofEpochSecond(15_5891_1533),
        y = 2003,
        checksum = "3989c9fd-9a6c-74eb-f002-e7667bc38c9e",
    )
internal val IgdbReleaseDateFixtures.thief2Category2Yyyy
    get() = ReleaseDate(
        id = 25217,
        category = YYYY,
        created_at = Instant.ofEpochSecond(14_2097_5720),
        date = Instant.ofEpochSecond(9_7822_0800),
        game = Game(id = 1, name = "Thief II: The Metal Age"),
        human = "2000",
        m = 12,
        platform = Platform(id = 6, name = "PC (Microsoft Windows)"),
        region = EUROPE,
        updated_at = Instant.ofEpochSecond(14_2097_6965),
        y = 2000,
        checksum = "c883ee5e-664c-0f82-9496-d9c99d75e28d",
    )
internal val IgdbReleaseDateFixtures.xxComCategory3Q1
    get() = ReleaseDate(
        id = 180537,
        category = YYYYQ1,
        created_at = Instant.ofEpochSecond(15_7476_7492),
        date = Instant.ofEpochSecond(7_6507_2000),
        game = Game(id = 24, name = "X-COM: UFO Defense"),
        human = "Q1 1994",
        m = 3,
        platform = Platform(id = 13),
        region = WORLDWIDE,
        updated_at = Instant.ofEpochSecond(15_7478_1688),
        y = 1994,
        checksum = "3a0c3ee3-568c-d45f-a4bf-a19bc56bf6de",
    )
internal val IgdbReleaseDateFixtures.xxComCategory4Q2
    get() = ReleaseDate(
        id = 152609,
        category = YYYYQ2,
        created_at = Instant.ofEpochSecond(15_2651_2489),
        date = Instant.ofEpochSecond(7_7293_4400),
        game = Game(id = 24, name = "X-COM: UFO Defense"),
        human = "Q2 1994",
        m = 6,
        platform = Platform(id = 16),
        region = WORLDWIDE,
        updated_at = Instant.ofEpochSecond(15_2652_6835),
        y = 1994,
        checksum = "3a0c3ee3-568c-d45f-a4bf-a19bc56bf6de",
    )
internal val IgdbReleaseDateFixtures.zooTycoonCategory5Q3
    get() = ReleaseDate(
        id = 97333,
        category = YYYYQ3,
        created_at = Instant.ofEpochSecond(15_0042_0616),
        date = Instant.ofEpochSecond(10_0180_8000),
        game = Game(id = 334, name = "Zoo Tycoon"),
        human = "Q3 2001",
        m = 9,
        platform = Platform(id = 14, name = "Mac"),
        region = NORTH_AMERICA,
        updated_at = Instant.ofEpochSecond(15_0042_0643),
        y = 2001,
        checksum = "ac97dd1f-4de3-a469-ff79-b0bea8518706",
    )
internal val IgdbReleaseDateFixtures.nightShiftCategory6Q4
    get() = ReleaseDate(
        id = 177194,
        category = YYYYQ4,
        created_at = Instant.ofEpochSecond(15_7063_7926),
        date = Instant.ofEpochSecond(6_6260_1600),
        game = Game(id = 195, name = "Night Shift"),
        human = "Q4 1990",
        m = 12,
        platform = Platform(id = 13, name = "DOS"),
        region = WORLDWIDE,
        updated_at = Instant.ofEpochSecond(15_7065_2188),
        y = 1990,
        checksum = "0e40b765-657e-5c92-5597-7c222d1083f9",
    )
internal val IgdbReleaseDateFixtures.belowTheStoneTbdCategory6
    get() = ReleaseDate(
        id = 510648,
        category = YYYYQ4,
        created_at = Instant.ofEpochSecond(16_9349_9509),
        date = Instant.ofEpochSecond(17_0398_0800),
        game = Game(id = 123877, name = "Below the Stone"),
        human = "Q4 2023",
        m = 12,
        platform = Platform(id = 6, name = "PC (Microsoft Windows)"),
        region = WORLDWIDE,
        updated_at = Instant.ofEpochSecond(16_9349_9522),
        y = 2023,
        checksum = "b0002bf0-68e9-0f47-1f99-9b8ba9247025",
    )
internal val IgdbReleaseDateFixtures.starWarsCategory7Tbd
    get() = ReleaseDate(
        id = 500318,
        category = TBD,
        created_at = Instant.ofEpochSecond(16_9108_0913),
        date = null,
        game = Game(id = 156, name = "Star Wars: Episode I - Battle for Naboo"),
        human = "TBD",
        m = 0,
        platform = Platform(id = 4, name = "Nintendo 64"),
        region = BRAZIL,
        updated_at = Instant.ofEpochSecond(16_9110_8327),
        y = 0,
        checksum = "3dfae0e5-618f-b8f5-2ba9-474174aa95ea",
    )
internal val IgdbReleaseDateFixtures.portalStatus6
    get() = ReleaseDate(
        id = 485079,
        category = YYYYMMMMDD,
        created_at = Instant.ofEpochSecond(16_8732_4969),
        date = Instant.ofEpochSecond(16_5637_4400),
        game = Game(id = 71, name = "Portal"),
        human = "Jun 28, 2022",
        m = 6,
        platform = Platform(id = 130, name = "Nintendo Switch"),
        region = WORLDWIDE,
        updated_at = Instant.ofEpochSecond(16_8733_6011),
        y = 2022,
        status = ReleaseDateStatus(
            id = 6,
            name = "Full Release",
            description = "A status of release where the game has gone gold. Typically this is referred to as 1.0.",
            created_at = Instant.ofEpochSecond(16_7768_8062),
            updated_at = Instant.ofEpochSecond(16_7768_8062),
            checksum = "592d3a03-d379-5494-af90-01cf40b3cc6f",
        ),
        checksum = "3dfae0e5-618f-b8f5-2ba9-474174aa95ea",
    )
