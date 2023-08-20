/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.gradle.config.igdb

import java.util.Properties

internal class IgdbConfigReader(
    private val properties: Properties,
    ) {
    fun read(): LocalIgdbOptions {
        return LocalIgdbOptions(
            igdbBaseUrl = properties.getProperty("igdb_base_url", null),
            igdbApiKey = properties.getProperty("igdb_api_key", null),
            igdbTwitchClientId = properties.getProperty("igdb_twitch_client_id", null),
            igdbTwitchClientSecret = properties.getProperty("igdb_twitch_client_secret", null),
            igdbTwitchToken = properties.getProperty("igdb_twitch_token", null),
        )
    }
}
