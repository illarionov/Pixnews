/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbPlatformFixtures
import ru.pixnews.igdbclient.model.Platform
import ru.pixnews.igdbclient.model.PlatformCategoryEnum
import ru.pixnews.igdbclient.model.PlatformCategoryEnum.COMPUTER
import ru.pixnews.igdbclient.model.PlatformCategoryEnum.CONSOLE
import ru.pixnews.igdbclient.model.PlatformCategoryEnum.OPERATING_SYSTEM
import ru.pixnews.igdbclient.model.PlatformCategoryEnum.PORTABLE_CONSOLE
import ru.pixnews.igdbclient.model.PlatformFamily
import ru.pixnews.igdbclient.model.PlatformLogo
import ru.pixnews.igdbclient.model.PlatformVersion
import ru.pixnews.igdbclient.model.PlatformWebsite
import java.time.Instant

internal val IgdbPlatformFixtures.linux
    get() = Platform(
        id = 3L,
        abbreviation = "Linux",
        alternative_name = "GNU/Linux",
        category = OPERATING_SYSTEM,
        created_at = Instant.ofEpochSecond(12_9755_5200),
        name = "Linux",
        platform_logo = PlatformLogo(id = 380),
        platform_family = PlatformFamily(id = 4),
        slug = "linux",
        summary = "Linux is a free and open-source (FOSS/FLOSS) Operating System. In a more " +
                "precise and complex definition, Linux is an open-source OS kernel on which " +
                "a large variety of Operating Systems (known as Linux distributions) are " +
                "built.",
        updated_at = Instant.ofEpochSecond(13_9294_0800),
        url = "https://www.igdb.com/platforms/linux",
        versions = listOf(44L).map { PlatformVersion(id = it) },
        websites = listOf(1L).map { PlatformWebsite(id = it) },
        checksum = "b810fb9c-65f7-b297-3658-da9c4d52bf11",
    )
internal val IgdbPlatformFixtures.win
    get() = Platform(
        id = 6L,
        abbreviation = "PC",
        alternative_name = "mswin",
        category = OPERATING_SYSTEM,
        created_at = Instant.ofEpochSecond(12_9755_5200),
        name = "PC (Microsoft Windows)",
        platform_logo = PlatformLogo(id = 203),
        slug = "win",
        updated_at = Instant.ofEpochSecond(14_7000_9600),
        url = "https://www.igdb.com/platforms/win",
        versions = listOf(1L, 13L, 14L, 15L, 124L).map { PlatformVersion(id = it) },
        websites = listOf(2L).map { PlatformWebsite(id = it) },
        checksum = "5aae54d0-390e-a4ec-a9ee-4ad4cc346992",
    )
internal val IgdbPlatformFixtures.mac
    get() = Platform(
        id = 14L,
        abbreviation = "Mac",
        alternative_name = "Mac OS",
        category = OPERATING_SYSTEM,
        created_at = Instant.ofEpochSecond(12_9764_1600),
        name = "Mac",
        platform_logo = PlatformLogo(id = 100),
        slug = "mac",
        updated_at = Instant.ofEpochSecond(13_9423_6800),
        url = "https://www.igdb.com/platforms/mac",
        versions = listOf(
            45L,
            141L,
            142L,
            143L,
            144L,
            145L,
            146L,
            147L,
            148L,
            149L,
            150L,
            151L,
        ).map { PlatformVersion(id = it) },
        websites = listOf(5L).map { PlatformWebsite(id = it) },
        checksum = "19c9dcae-80a2-e137-50ff-11b823738827",
    )
internal val IgdbPlatformFixtures.commodoreC64
    get() = Platform(
        id = 15L,
        abbreviation = "C64",
        alternative_name = "C64/C128/MAX",
        category = COMPUTER,
        created_at = Instant.ofEpochSecond(12_9788_0639),
        name = "Commodore C64/128/MAX",
        platform_logo = PlatformLogo(id = 300),
        slug = "c64",
        updated_at = Instant.ofEpochSecond(16_6021_2364),
        url = "https://www.igdb.com/platforms/c64",
        versions = listOf(36L).map { PlatformVersion(id = it) },
        checksum = "9a1db5f9-83b3-257e-9ae6-785a4e65d919",
    )
internal val IgdbPlatformFixtures.android
    get() = Platform(
        id = 34L,
        abbreviation = "Android",
        alternative_name = "Infocusa3",
        category = OPERATING_SYSTEM,
        created_at = Instant.ofEpochSecond(13_0256_6400),
        name = "Android",
        platform_logo = PlatformLogo(id = 376),
        slug = "android",
        updated_at = Instant.ofEpochSecond(15_5615_0400),
        url = "https://www.igdb.com/platforms/android",
        versions = listOf(
            7L,
            8L,
            9L,
            10L,
            11L,
            12L,
            236L,
            237L,
            238L,
            239L,
            320L,
        ).map { PlatformVersion(id = it) },
        websites = listOf(7L).map { PlatformWebsite(id = it) },
        checksum = "fe27cf28-ec61-df1a-e378-ae233b2eea73",
    )
internal val IgdbPlatformFixtures.n3ds
    get() = Platform(
        id = 37L,
        abbreviation = "3DS",
        alternative_name = "3DS",
        category = PORTABLE_CONSOLE,
        created_at = Instant.ofEpochSecond(13_1760_0000),
        generation = 8,
        name = "Nintendo 3DS",
        platform_logo = PlatformLogo(id = 240),
        platform_family = PlatformFamily(id = 5),
        slug = "3ds",
        updated_at = Instant.ofEpochSecond(14_7398_4000),
        url = "https://www.igdb.com/platforms/3ds",
        versions = listOf(50L).map { PlatformVersion(id = it) },
        websites = listOf(9L).map { PlatformWebsite(id = it) },
        checksum = "acee0d28-d3b8-b891-fc1c-768782c775a4",
    )
internal val IgdbPlatformFixtures.ios
    get() = Platform(
        id = 39L,
        abbreviation = "iOS",
        alternative_name = "",
        category = OPERATING_SYSTEM,
        created_at = Instant.ofEpochSecond(13_1768_6400),
        name = "iOS",
        platform_logo = PlatformLogo(id = 248),
        slug = "ios",
        updated_at = Instant.ofEpochSecond(13_9164_4800),
        url = "https://www.igdb.com/platforms/ios",
        versions = listOf(43L).map { PlatformVersion(id = it) },
        checksum = "fefe4a2b-7c90-0e89-e811-902ea1cf2b58",
    )
internal val IgdbPlatformFixtures.psVita
    get() = Platform(
        id = 46L,
        abbreviation = "Vita",
        alternative_name = "PS Vita",
        category = PORTABLE_CONSOLE,
        created_at = Instant.ofEpochSecond(13_2166_0800),
        generation = 8,
        name = "PlayStation Vita",
        platform_logo = PlatformLogo(id = 232),
        platform_family = PlatformFamily(id = 1),
        slug = "psvita",
        updated_at = Instant.ofEpochSecond(15_5278_0800),
        url = "https://www.igdb.com/platforms/psvita",
        versions = listOf(60L, 274L, 275L).map { PlatformVersion(id = it) },
        websites = listOf(34L).map { PlatformWebsite(id = it) },
        checksum = "7185535c-37aa-02e0-1764-b81181c990cb",
    )
internal val IgdbPlatformFixtures.ps4
    get() = Platform(
        id = 48L,
        abbreviation = "PS4",
        alternative_name = "PS4",
        category = PlatformCategoryEnum.fromValue(1)!!,
        created_at = Instant.ofEpochSecond(13_2649_9200),
        generation = 8,
        name = "PlayStation 4",
        platform_logo = PlatformLogo(id = 231),
        platform_family = PlatformFamily(id = 1),
        slug = "ps4--1",
        summary = "The PlayStation 4 system opens the door to an incredible journey through " +
                "immersive new gaming worlds and a deeply connected gaming community. PS4 " +
                "puts gamers first with an astounding launch line-up and over 180 games in " +
                "development. Play amazing top-tier blockbusters and innovative indie hits " +
                "on PS4. Developer inspired, gamer focused.",
        updated_at = Instant.ofEpochSecond(14_3311_6800),
        url = "https://www.igdb.com/platforms/ps4--1",
        versions = listOf(17L, 178L, 179L).map { PlatformVersion(id = it) },
        websites = listOf(11L).map { PlatformWebsite(id = it) },
        checksum = "1dfa9b83-5f1d-56ce-01a1-06fcdfbb5bc0",
    )
internal val IgdbPlatformFixtures.xboxOne
    get() = Platform(
        id = 49L,
        abbreviation = "XONE",
        alternative_name = "XONE",
        category = PlatformCategoryEnum.fromValue(1)!!,
        created_at = Instant.ofEpochSecond(13_2654_5022),
        generation = 8,
        name = "Xbox One",
        platform_logo = PlatformLogo(id = 329),
        platform_family = PlatformFamily(id = 2),
        slug = "xboxone",
        summary = "Welcome to a new generation of games and entertainment. " +
                "Where games push the boundaries of realism. And television obeys your every " +
                "command. Where listening to music while playing a game is a snap. And you " +
                "can jump from TV to movies to music to a game in an instant. Where your " +
                "experience is custom tailored to you. And the entertainment you love is all " +
                "in one place. Welcome to the all-in-one, Xbox One",
        updated_at = Instant.ofEpochSecond(16_3311_7143),
        url = "https://www.igdb.com/platforms/xboxone",
        versions = listOf(78L, 180L, 185L, 188L).map { PlatformVersion(id = it) },
        websites = listOf(12L).map { PlatformWebsite(id = it) },
        checksum = "1e7829d1-ecc1-967f-d605-ceec12f53c86",
    )
internal val IgdbPlatformFixtures.switch
    get() = Platform(
        id = 130L,
        abbreviation = "Switch",
        alternative_name = "NX",
        category = CONSOLE,
        created_at = Instant.ofEpochSecond(14_6594_8800),
        generation = 8,
        name = "Nintendo Switch",
        platform_logo = PlatformLogo(id = 534),
        platform_family = PlatformFamily(id = 5),
        slug = "switch",
        updated_at = Instant.ofEpochSecond(15_5053_4400),
        url = "https://www.igdb.com/platforms/switch",
        versions = listOf(173L, 282L).map { PlatformVersion(id = it) },
        websites = listOf(16L).map { PlatformWebsite(id = it) },
        checksum = "d28b6757-c24a-73c6-50e7-f8e6487c78af",
    )
internal val IgdbPlatformFixtures.ps5
    get() = Platform(
        id = 167L,
        abbreviation = "PS5",
        alternative_name = "PS5",
        category = CONSOLE,
        created_at = Instant.ofEpochSecond(15_4350_1750),
        generation = 9,
        name = "PlayStation 5",
        platform_logo = PlatformLogo(id = 463),
        platform_family = PlatformFamily(id = 1),
        slug = "ps5",
        updated_at = Instant.ofEpochSecond(16_2888_4745),
        url = "https://www.igdb.com/platforms/ps5",
        versions = listOf(273L).map { PlatformVersion(id = it) },
        websites = listOf(281L).map { PlatformWebsite(id = it) },
        checksum = "a7786ac6-fedb-da00-8bc5-3d91fbfda93a",
    )
internal val IgdbPlatformFixtures.xBoxSeriesX
    get() = Platform(
        id = 169L,
        abbreviation = "Series X",
        alternative_name = "XSX",
        category = CONSOLE,
        created_at = Instant.ofEpochSecond(15_6527_3347),
        generation = 9,
        name = "Xbox Series X|S",
        platform_logo = PlatformLogo(id = 561),
        platform_family = PlatformFamily(id = 2),
        slug = "series-x",
        updated_at = Instant.ofEpochSecond(16_3311_7145),
        url = "https://www.igdb.com/platforms/series-x",
        versions = listOf(284L, 489L).map { PlatformVersion(id = it) },
        websites = listOf(36L, 105L, 238L).map { PlatformWebsite(id = it) },
        checksum = "01a4b4f9-3f3a-a2f3-a7b8-0c5311e78df9",
    )
