/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.languagesupport

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbLanguageFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbLanguageSupportFixtures
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.language.english
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.language.japanese
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.language.spanish
import ru.pixnews.igdbclient.model.Game
import ru.pixnews.igdbclient.model.LanguageSupport
import ru.pixnews.igdbclient.model.LanguageSupportType
import java.time.Instant

internal val IgdbLanguageSupportFixtures.japaneseSoundSupport
    get() = LanguageSupport(
        id = 749230,
        game = Game(id = 144040),
        language = IgdbLanguageFixtures.japanese,
        language_support_type = LanguageSupportType(id = 1),
        created_at = Instant.ofEpochSecond(16_9167_5341),
        updated_at = Instant.ofEpochSecond(16_9167_5341),
        checksum = "a743eb0b-6dc0-dd7f-4823-aed5e25b83f7",
    )
internal val IgdbLanguageSupportFixtures.englishSubtitlesSupport
    get() = LanguageSupport(
        id = 749231,
        game = Game(id = 144040),
        language = IgdbLanguageFixtures.english,
        language_support_type = LanguageSupportType(id = 2),
        created_at = Instant.ofEpochSecond(16_9167_5341),
        updated_at = Instant.ofEpochSecond(16_9167_5341),
        checksum = "8c8ca32e-5d7c-8c4a-9c30-41ba75e4eab6",
    )
internal val IgdbLanguageSupportFixtures.spanishInterfaceSupport
    get() = LanguageSupport(
        id = 754725,
        game = Game(id = 228531),
        language = IgdbLanguageFixtures.spanish,
        language_support_type = LanguageSupportType(id = 2),
        created_at = Instant.ofEpochSecond(16_9167_5341),
        updated_at = Instant.ofEpochSecond(16_9167_5341),
        checksum = "8c8ca32e-5d7c-8c4a-9c30-41ba75e4eab6",
    )
