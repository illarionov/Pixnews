/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.library.igdb.internal.twitch

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayInputStream

internal class TwitchTokenErrorResponseParserTest {
    private val parser = TwitchTokenErrorResponseParser

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("parserSource")
    fun `Parser should return correct result on correct data`(testSpec: ParserTestData) {
        val errorResponse = parser(testSpec.source.byteInputStream())
        errorResponse shouldBeEqual testSpec.expected
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("parserSourceMalformedData")
    fun `Parser should throw on malformed data`(malformedJson: String) {
        shouldThrowAny {
            parser(ByteArrayInputStream(malformedJson.encodeToByteArray()))
        }
    }

    class ParserTestData(
        val source: String,
        val expected: TwitchErrorResponse,
    ) {
        override fun toString(): String {
            return "json: `$source` => `$expected`"
        }
    }

    internal companion object {
        @JvmStatic
        fun parserSourceMalformedData(): List<String> = listOf(
            "",
            "[]",
        )

        @JvmStatic
        @Suppress("LongMethod")
        fun parserSource(): List<ParserTestData> = listOf(
            ParserTestData(
                """{}""",
                TwitchErrorResponse(
                    status = 0,
                    message = "",
                ),
            ),
            ParserTestData(
                """{"status": "string"}""",
                TwitchErrorResponse(
                    status = 0,
                    message = "",
                ),
            ),
            ParserTestData(
                """{"status":400,"message":"invalid grant type"}""",
                TwitchErrorResponse(
                    status = 400,
                    message = "invalid grant type",
                ),
            ),
        )
    }
}