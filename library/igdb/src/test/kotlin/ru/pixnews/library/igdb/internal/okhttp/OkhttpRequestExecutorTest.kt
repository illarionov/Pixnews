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
package ru.pixnews.library.igdb.internal.okhttp

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.library.igdb.Fixtures
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery.Companion.apicalypseQuery
import ru.pixnews.library.igdb.internal.IgdbConstants.Header.AUTHORIZATION
import ru.pixnews.library.igdb.internal.IgdbConstants.Header.CLIENT_ID
import ru.pixnews.library.igdb.internal.IgdbRequest.ApicalypsePostRequest
import ru.pixnews.library.igdb.internal.model.IgdbAuthToken
import ru.pixnews.library.igdb.util.MockWebServerExt
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.okhttp.ConcatMockDispatcher
import java.io.InputStream

class OkhttpRequestExecutorTest {
    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()
    private val server: MockWebServer = MockWebServer()

    @Test
    fun `User headers should take precedence over AuthToken-provided headers `() = coroutinesExt.runTest {
        val testAuthToken = object : IgdbAuthToken {
            override val clientId: String = "authtoken-provided-client-id"
            override val token: String = "authtoken-provided-token"
        }

        val executor = startServerPrepareApi(
            token = testAuthToken,
            headers = mapOf(
                CLIENT_ID to listOf(Fixtures.TEST_CLIENT_ID),
                AUTHORIZATION to listOf(" Bearer ${Fixtures.TEST_TOKEN}"),
            ),
        )

        executor.invoke<Any>(
            ApicalypsePostRequest("endpoint", apicalypseQuery { }, { _: ApicalypseQuery, _: InputStream -> "" }),
        )

        server.takeRequest().run {
            headers.values("Client-Id") shouldBe listOf(Fixtures.TEST_CLIENT_ID)
            headers.values("Authorization") shouldBe listOf("Bearer ${Fixtures.TEST_TOKEN}")
        }
    }

    @Test
    fun `Executor should not encode slashes in path`() = coroutinesExt.runTest {
        val executor = startServerPrepareApi()

        executor.invoke<Any>(
            ApicalypsePostRequest("games/count", apicalypseQuery { }, { _: ApicalypseQuery, _: InputStream -> "" }),
        )

        server.takeRequest().run {
            path shouldBe "/v4/games/count"
        }
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    private fun startServerPrepareApi(
        okhttpClient: OkHttpClient = MockWebServerExt.createOkHttpClientBuilder().build(),
        token: IgdbAuthToken? = null,
        userAgent: String? = "Test user agent",
        headers: Map<String, List<String>> = emptyMap(),
        backgroundDispatcher: CoroutineDispatcher = coroutinesExt.dispatcher,
        response: (RecordedRequest) -> MockResponse? = { null },
    ): OkhttpRequestExecutor {
        val testServerDispatcher = ConcatMockDispatcher(response)
        server.dispatcher = testServerDispatcher
        server.start()

        return OkhttpRequestExecutor(
            callFactory = okhttpClient,
            baseUrl = server.url("/v4/"),
            token = token,
            userAgent = userAgent,
            headers = headers,
            backgroundDispatcher = backgroundDispatcher,
        )
    }
}