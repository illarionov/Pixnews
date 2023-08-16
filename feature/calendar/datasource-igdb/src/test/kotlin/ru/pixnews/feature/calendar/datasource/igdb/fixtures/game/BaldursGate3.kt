/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.fixtures.game

import com.squareup.wire.Instant
import com.squareup.wire.ofEpochSecond
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGameModeFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbGenreFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbPlatformFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbThemeFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.coOperative
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.multiplayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.gamemode.singlePlayer
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.genre.adventure
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.mac
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.ps5
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.win
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.platform.xBoxSeriesX
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.theme.action
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.theme.fantasy
import ru.pixnews.igdbclient.model.AgeRating
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.ESRB
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.PEGI
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.USK
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.EIGHTEEN
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.M
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.USK_18
import ru.pixnews.igdbclient.model.AlternativeName
import ru.pixnews.igdbclient.model.Artwork
import ru.pixnews.igdbclient.model.Collection
import ru.pixnews.igdbclient.model.Company
import ru.pixnews.igdbclient.model.Cover
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYMMMMDD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYYQ4
import ru.pixnews.igdbclient.model.ExternalGame
import ru.pixnews.igdbclient.model.Franchise
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.GameCategoryEnum.MAIN_GAME
import ru.pixnews.igdbclient.model.GameEngine
import ru.pixnews.igdbclient.model.GameLocalization
import ru.pixnews.igdbclient.model.GameMode
import ru.pixnews.igdbclient.model.GameStatusEnum.RELEASED
import ru.pixnews.igdbclient.model.GameVideo
import ru.pixnews.igdbclient.model.Genre
import ru.pixnews.igdbclient.model.InvolvedCompany
import ru.pixnews.igdbclient.model.Keyword
import ru.pixnews.igdbclient.model.Language
import ru.pixnews.igdbclient.model.LanguageSupport
import ru.pixnews.igdbclient.model.LanguageSupportType
import ru.pixnews.igdbclient.model.MultiplayerMode
import ru.pixnews.igdbclient.model.Platform
import ru.pixnews.igdbclient.model.PlayerPerspective
import ru.pixnews.igdbclient.model.ReleaseDate
import ru.pixnews.igdbclient.model.Screenshot
import ru.pixnews.igdbclient.model.Theme
import ru.pixnews.igdbclient.model.Website
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_GOG
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_OFFICIAL
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_REDDIT
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_STEAM
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_TWITCH
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_TWITTER
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_WIKIA
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_WIKIPEDIA

internal val IgdbGameFixtures.baldursGate3 get() = baldursGate3Instance
internal val IgdbGameFixtures.baldursGate3Shallow get() = baldursGate3Instance.copy(
    age_ratings = baldursGate3Instance.age_ratings.map { AgeRating(id = it.id) },
    cover = Cover(id = baldursGate3Instance.cover!!.id),
    collection = Collection(id = baldursGate3Instance.collection!!.id),
    videos = baldursGate3Instance.videos.map { GameVideo(id = it.id) },
    platforms = baldursGate3Instance.platforms.map { Platform(id = it.id) },
    screenshots = baldursGate3Instance.screenshots.map { Screenshot(id = it.id) },
    involved_companies = baldursGate3Instance.involved_companies.map { InvolvedCompany(id = it.id) },
    release_dates = baldursGate3Instance.release_dates.map { ReleaseDate(id = it.id) },
    game_modes = baldursGate3Instance.game_modes.map { GameMode(id = it.id) },
    genres = baldursGate3Instance.genres.map { Genre(id = it.id) },
    themes = baldursGate3Instance.themes.map { Theme(id = it.id) },
    websites = baldursGate3Instance.websites.map { Website(id = it.id) },
    language_supports = baldursGate3Instance.language_supports.map { LanguageSupport(id = it.id) },
)

private val baldursGate3Instance = Game(
    id = 119171L,
    age_ratings = listOf(
        AgeRating(id = 31797L, category = PEGI, rating = EIGHTEEN),
        AgeRating(id = 127567L, category = ESRB, rating = M),
        AgeRating(id = 148088L, category = USK, rating = USK_18),
    ),
    aggregated_rating = 92.0,
    aggregated_rating_count = 11,
    alternative_names = listOf(
        50217L,
        78595L,
        78596L,
        78597L,
        139162L,
    ).map { AlternativeName(id = it) },
    artworks = listOf(21036L, 101139L).map { Artwork(id = it) },
    bundles = emptyList(),
    category = MAIN_GAME,
    collection = Collection(
        id = 7,
        name = "Baldur\u0027s Gate",
        games = listOf(
            5L,
            6,
            81,
            82,
            83,
            84,
            1880,
            5613,
            14292,
            21813,
            52617,
            91241,
            119171,
            124786,
            210699,
            243015,
        ).map { Game(id = it) },
        slug = "baldur-s-gate",
        url = "https://www.igdb.com/collections/baldur-s-gate",
    ),
    cover = Cover(id = 289025, image_id = "co670h", width = 600, height = 800),
    created_at = Instant.ofEpochSecond(1_559_228_938),
    dlcs = emptyList(),
    expansions = emptyList(),
    external_games = listOf(
        1725312L,
        1775824L,
        1914473L,
        2070558L,
        2678929L,
    ).map { ExternalGame(id = it) },
    first_release_date = Instant.ofEpochSecond(1_601_942_400),
    follows = 24,
    franchise = null,
    franchises = listOf(Franchise(id = 43)),
    game_engines = listOf(GameEngine(id = 255)),
    game_modes = listOf(
        IgdbGameModeFixtures.singlePlayer,
        IgdbGameModeFixtures.multiplayer,
        IgdbGameModeFixtures.coOperative,
    ),
    genres = listOf(
        Genre(id = 12L, name = "Role-playing (RPG)"),
        Genre(id = 15L, name = "Strategy"),
        Genre(id = 16L, name = "Turn-based strategy (TBS)"),
        Genre(id = 24L, name = "Tactical"),
        IgdbGenreFixtures.adventure,
    ),
    hypes = 39,
    involved_companies = listOf(
        InvolvedCompany(
            id = 207626L,
            developer = true,
            publisher = true,
            porting = false,
            supporting = false,
            company = Company(
                id = 510,
                name = "Larian Studios",
            ),
        ),
        InvolvedCompany(
            id = 214383L,
            developer = false,
            publisher = false,
            porting = false,
            supporting = true,
            company = Company(
                id = 47197,
                name = "Wushu Studios",
            ),
        ),
    ),
    keywords = listOf(182L, 4272L, 4823L, 27054L).map { Keyword(id = it) },
    multiplayer_modes = listOf(MultiplayerMode(id = 17326)),
    name = "Baldur's Gate 3",
    parent_game = null,
    platforms = listOf(
        IgdbPlatformFixtures.win,
        IgdbPlatformFixtures.mac,
        IgdbPlatformFixtures.ps5,
        IgdbPlatformFixtures.xBoxSeriesX,
        Platform(
            id = 170L,
            name = "Stadia",
            slug = "stadia",
        ),
    ),
    player_perspectives = listOf(PlayerPerspective(id = 3)),
    rating = 88.37_476_628_582_442,
    rating_count = 50,
    release_dates = listOf(
        ReleaseDate(
            id = 474559L,
            category = YYYYMMMMDD,
            date = ofEpochSecond(1_601_942_400, 0),
            y = 2020,
            m = 10,
            updated_at = ofEpochSecond(1_683_721_788, 0),
        ),
        ReleaseDate(
            id = 487025L,
            category = YYYYMMMMDD,
            date = ofEpochSecond(1_693_958_400, 0),
            y = 2023,
            m = 9,
            updated_at = ofEpochSecond(1_688_064_635, 0),
        ),
        ReleaseDate(
            id = 507358L,
            category = YYYYQ4,
            date = ofEpochSecond(1_703_980_800, 0),
            y = 2023,
            m = 12,
            updated_at = ofEpochSecond(1_692_909_337, 0),
        ),
        ReleaseDate(
            id = 508258L,
            category = YYYYMMMMDD,
            date = ofEpochSecond(1_691_020_800, 0),
            y = 2023,
            m = 8,
            updated_at = ofEpochSecond(1_693_186_806, 0),
        ),
        ReleaseDate(
            id = 508259L,
            category = YYYYMMMMDD,
            date = ofEpochSecond(1_601_942_400, 0),
            y = 2020,
            m = 10,
            updated_at = ofEpochSecond(1_693_186_806, 0),
        ),
        ReleaseDate(
            id = 510464L,
            category = YYYYMMMMDD,
            date = ofEpochSecond(1_693_958_400, 0),
            y = 2023,
            m = 9,
            updated_at = ofEpochSecond(1_693_476_823, 0),
        ),
        ReleaseDate(
            id = 511548L,
            category = YYYYMMMMDD,
            date = ofEpochSecond(1_691_020_800, 0),
            y = 2023,
            m = 8,
            updated_at = ofEpochSecond(1_693_832_153, 0),
        ),
        ReleaseDate(
            id = 511549L,
            category = YYYYMMMMDD,
            date = ofEpochSecond(1_601_942_400, 0),
            y = 2020,
            m = 10,
            updated_at = ofEpochSecond(1_693_832_153, 0),
        ),
    ),
    screenshots = listOf(
        Screenshot(id = 375098L, image_id = "sc81fe", width = 1920, height = 1080),
        Screenshot(id = 375099L, image_id = "sc81ff", width = 1920, height = 1080),
        Screenshot(id = 375100L, image_id = "sc81fg", width = 1920, height = 1080),
        Screenshot(id = 375101L, image_id = "sc81fh", width = 1920, height = 1080),
        Screenshot(id = 375102L, image_id = "sc81fi", width = 1920, height = 1080),
        Screenshot(id = 375103L, image_id = "sc81fj", width = 1920, height = 1080),
        Screenshot(id = 375104L, image_id = "sc81fk", width = 1920, height = 1080),
        Screenshot(id = 375105L, image_id = "sc81fl", width = 1920, height = 1080),
        Screenshot(id = 375106L, image_id = "sc81fm", width = 1920, height = 1080),
        Screenshot(id = 375107L, image_id = "sc81fn", width = 1920, height = 1080),
    ),
    similar_games = listOf(
        13196L,
        26845L,
        27092L,
        28182L,
        55199L,
        81249L,
        96217L,
        105049L,
        106987L,
        115653L,
    ).map { Game(id = it) },
    slug = "baldurs-gate-3",
    standalone_expansions = emptyList(),
    status = RELEASED,
    storyline = "The land of Faerûn is in turmoil. Refugees cross the wilds, fleeing the " +
            "helltorn stronghold of Elturel. A vicious cult marches across the Sword " +
            "Coast, uniting every race of monsters and men under the banner of a cryptic" +
            " god they call the Absolute. Chaos strikes at Faerûn's foundations, and none" +
            " may escape its talons. Not even you.\n\n" +
            "The grotesque nautiloid ship" +
            " appears out of nowhere, blotting out the sun. Its writhing tentacles" +
            " snatch you from where you stand. The mind flayers have come, " +
            "imprisoning you on their ship, infecting you with their horrid parasite. " +
            "You will become one of them.\n\n" +
            "By fate or fortune, you survive when the " +
            "nautiloid crashes in the Sword Coast outlands. You set out for" +
            " civilisation, desperate for a cure for the parasite festering in your " +
            "brain, only to take centre stage in a conspiracy that runs as deep as the " +
            "Nine Hells. New enemies await.\n\n" +
            "As for old foes... the shadows stir.\n" +
            "And all roads lead to the legendary city of Baldur's Gate.",
    summary = "An ancient evil has returned to Baldur's Gate, intent on devouring it " +
            "from the inside out. The fate of Faerun lies in your hands. Alone, you may" +
            " resist. But together, you can overcome.",
    tags = listOf(
        1,
        17,
        268_435_468,
        268_435_471,
        268_435_472,
        268_435_480,
        268_435_487,
        536_871_094,
        536_875_184,
        536_875_735,
        536_897_966,
    ),
    themes = listOf(IgdbThemeFixtures.action, IgdbThemeFixtures.fantasy),
    total_rating = 90.18_738_314_291_221,
    total_rating_count = 61,
    updated_at = Instant.ofEpochSecond(1_693_974_103),
    url = "https://www.igdb.com/games/baldurs-gate-3",
    version_parent = null,
    version_title = "",
    videos = listOf(
        GameVideo(id = 27759L, video_id = "OcP0WdH7rTs"),
        GameVideo(id = 34304L, video_id = "jNY7AEQ59-8"),
        GameVideo(id = 37259L, video_id = "o5xpbAsly48"),
        GameVideo(id = 80611L, video_id = "tavPnYeFrV4"),
        GameVideo(id = 84917L, video_id = "Ks-KF9iueE0"),
        GameVideo(id = 91735L, video_id = "zTX79cgruXE"),
        GameVideo(id = 91736L, video_id = "t0uYhTLPGLQ"),
        GameVideo(id = 95037, video_id = "XuCfkgaaa08"),
    ),
    websites = listOf(
        Website(
            id = 108147L,
            category = WEBSITE_OFFICIAL,
            url = "https://baldursgate3.game/",
        ),
        Website(
            id = 113817L,
            category = WEBSITE_WIKIPEDIA,
            url = "https://en.wikipedia.org/wiki/Baldur%27s_Gate_III",
        ),
        Website(
            id = 120631L,
            category = WEBSITE_GOG,
            url = "https://www.gog.com/game/baldurs_gate_iii",
        ),
        Website(
            id = 137060L,
            category = WEBSITE_TWITTER,
            url = "https://twitter.com/baldursgate3",
        ),
        Website(
            id = 246552L,
            category = WEBSITE_STEAM,
            url = "https://store.steampowered.com/app/1086940",
        ),
        Website(
            id = 388947L,
            category = WEBSITE_TWITCH,
            url = "https://www.twitch.tv/directory/game/Baldur\u0027s%20Gate%203",
        ),
        Website(
            id = 538222L,
            category = WEBSITE_WIKIA,
            url = "https://baldursgate3.wiki.fextralife.com/Baldur\u0027s+Gate+3+Wiki",
        ),
        Website(
            id = 578549L,
            category = WEBSITE_REDDIT,
            url = "https://www.reddit.com/r/BaldursGate3/",
        ),
    ),
    checksum = "8a56b73c-6846-4e65-7496-b84270c3e3cb",
    remakes = emptyList(),
    remasters = emptyList(),
    expanded_games = emptyList(),
    ports = emptyList(),
    forks = emptyList(),
    language_supports = listOf(
        LanguageSupport(
            id = 233666L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 7, locale = "en-US"),
        ),
        LanguageSupport(
            id = 233668L,
            language_support_type = LanguageSupportType(id = 1),
            language = Language(id = 7, locale = "en-US"),
        ),
        LanguageSupport(
            id = 233670L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 7, locale = "en-US"),
        ),
        LanguageSupport(
            id = 233672L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 12, locale = "fr-FR"),
        ),
        LanguageSupport(
            id = 233674L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 12, locale = "fr-FR"),
        ),
        LanguageSupport(
            id = 233676L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 27, locale = "de-DE"),
        ),
        LanguageSupport(
            id = 233678L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 27, locale = "de-DE"),
        ),
        LanguageSupport(
            id = 233680L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 9, locale = "es-ES"),
        ),
        LanguageSupport(
            id = 233682L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 9, locale = "es-ES"),
        ),
        LanguageSupport(
            id = 233684L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 19, locale = "pl-PL"),
        ),
        LanguageSupport(
            id = 233685L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 19, locale = "pl-PL"),
        ),
        LanguageSupport(
            id = 233686L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 22, locale = "ru-RU"),
        ),
        LanguageSupport(
            id = 233687L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 22, locale = "ru-RU"),
        ),
        LanguageSupport(
            id = 233688L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 2, locale = "zh-CN"),
        ),
        LanguageSupport(
            id = 233689L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 2, locale = "zh-CN"),
        ),
        LanguageSupport(
            id = 233690L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 24, locale = "tr-TR"),
        ),
        LanguageSupport(
            id = 233691L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 24, locale = "tr-TR"),
        ),
        LanguageSupport(
            id = 233692L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 21, locale = "pt-BR"),
        ),
        LanguageSupport(
            id = 233693L,
            language_support_type = LanguageSupportType(id = 2),
            language = Language(id = 21, locale = "pt-BR"),
        ),
        LanguageSupport(
            id = 744315L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 15, locale = "it-IT"),
        ),
        LanguageSupport(
            id = 744316L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 28, locale = "uk-UA"),
        ),
        LanguageSupport(
            id = 744317L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 3, locale = "zh-TW"),
        ),
        LanguageSupport(
            id = 793408L,
            language_support_type = LanguageSupportType(id = 3),
            language = Language(id = 10, locale = "es-MX"),
        ),
    ),
    game_localizations = listOf(GameLocalization(id = 20906)),
)
