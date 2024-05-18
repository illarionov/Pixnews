/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.config.igdb

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.Properties

class IgdbConfigReaderTest {
    @ParameterizedTest
    @MethodSource("configReaderTestSource")
    fun `Config reader should read properties`(source: ConfigReaderTest) {
        val properties = Properties().apply { putAll(source.properties) }
        val reader = IgdbConfigReader(properties = properties)

        val parsedOptions = reader.read()

        parsedOptions shouldBe source.expectedResult
    }

    data class ConfigReaderTest(
        val properties: Map<String, String>,
        val expectedResult: LocalIgdbOptions,
    ) {
        override fun toString(): String {
            return "props: $properties"
        }
    }

    companion object {
        @JvmStatic
        fun configReaderTestSource(): List<ConfigReaderTest> = listOf(
            ConfigReaderTest(
                properties = mapOf(),
                expectedResult = LocalIgdbOptions.empty,
            ),
            ConfigReaderTest(
                properties = mapOf(
                    "non_igdb_property" to "other_value",
                    "igdb_base_url" to "https://example.com/v4/",
                ),
                expectedResult = LocalIgdbOptions.empty.copy(
                    igdbBaseUrl = "https://example.com/v4/",
                ),
            ),
            ConfigReaderTest(
                properties = mapOf(
                    "igdb_base_url" to "https://example.com/v4/",
                    "igdb_api_key" to "test api key",
                    "igdb_twitch_client_id" to "test twitch client id",
                    "igdb_twitch_client_secret" to "test twitch client secret",
                    "igdb_twitch_token" to "test twitch token",
                ),
                expectedResult = LocalIgdbOptions(
                    igdbBaseUrl = "https://example.com/v4/",
                    igdbApiKey = "test api key",
                    igdbTwitchClientId = "test twitch client id",
                    igdbTwitchClientSecret = "test twitch client secret",
                    igdbTwitchToken = "test twitch token",
                ),
            ),
        )
    }
}
