/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.fixtures.language

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbLanguageFixtures
import ru.pixnews.igdbclient.model.Language
import java.time.Instant

internal val IgdbLanguageFixtures.japanese
    get() = Language(
        id = 16,
        name = "Japanese",
        native_name = "日本語",
        locale = "ja-JP",
        created_at = Instant.ofEpochSecond(16_6480_3639),
        updated_at = Instant.ofEpochSecond(16_6480_3639),
        checksum = "6e46a214-1fc9-e097-bf2b-b5d3f1187a48",
    )
internal val IgdbLanguageFixtures.english
    get() = Language(
        id = 7,
        name = "English",
        native_name = "English (US)",
        locale = "en-US",
        created_at = Instant.ofEpochSecond(16_6480_3639),
        updated_at = Instant.ofEpochSecond(16_6480_3639),
        checksum = "e2f64b8d-425f-28a3-3552-217aa1a955eb",
    )
internal val IgdbLanguageFixtures.spanish
    get() = Language(
        id = 10,
        name = "Spanish (Mexico)",
        native_name = "Español (Mexico)",
        locale = "es-MX",
        created_at = Instant.ofEpochSecond(16_6480_3639),
        updated_at = Instant.ofEpochSecond(16_6480_3639),
        checksum = "e2f64b8d-425f-28a3-3552-217aa1a955eb",
    )
