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
package ru.pixnews.library.igdb

import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import ru.pixnews.library.igdb.internal.IgdbConstants.MediaType
import ru.pixnews.library.test.okhttp.readResourceAsBuffer

internal object Fixtures {
    const val TEST_CLIENT_ID = "blev3l45p6jsjy7yo829to012bv3cp"
    const val TEST_TOKEN = "abcd5gq1rtwyp2vq47r8evh7lp4eaa"

    object MockIgdbResponseContent {
        private const val MOCK_RESPONSES_PATH = "/mock/api.igdb.com"
        val gamesSearch: Buffer
            get() = readResourceAsBuffer("$MOCK_RESPONSES_PATH/games/games_search_diablo_limit_5.pb")

        val authFailure: Buffer
            get() = readResourceAsBuffer("$MOCK_RESPONSES_PATH/401_auth_failure.json")

        val syntaxError: Buffer
            get() = readResourceAsBuffer("$MOCK_RESPONSES_PATH/400_syntax_error.json")

        val multiQueryPlatformsCountPsGames: Buffer
            get() = readResourceAsBuffer("$MOCK_RESPONSES_PATH/multiquery/multiquery_count_ps_games.pb")

        val countGames: Buffer
            get() = Buffer().apply { write(byteArrayOf(0x08, 0x22)) }

        fun createSuccessMockResponse() = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", MediaType.APPLICATION_PROTOBUF)
            .setBody(gamesSearch)
    }
}
