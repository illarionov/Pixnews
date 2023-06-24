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

import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.shouldBe
import okhttp3.Call
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol.HTTP_1_1
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.asResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.library.igdb.Fixtures
import ru.pixnews.library.igdb.Fixtures.MockIgdbResponseContent
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery.Companion.apicalypseQuery
import ru.pixnews.library.igdb.dsl.IgdbClientConfig
import ru.pixnews.library.igdb.error.IgdbHttpException
import ru.pixnews.library.igdb.internal.IgdbConstants
import ru.pixnews.library.igdb.internal.IgdbConstants.MediaType.APPLICATION_JSON
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.okhttp.MockOkhttpCall
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

class OkhttpIgdbClientFactoryTest {
    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()

    @Test
    fun `Executor should not make requests to the twitch server if auth not configured`() = coroutinesExt.runTest {
        val mockCallFactory: Call.Factory = MockOkhttpCall.factory { call ->
            check(call.request().url == "$TEST_BASE_URL/games.pb".toHttpUrl()) {
                "Requests to the `${call.request().url}` are not allowed"
            }
            createSuccessResponse(call, MockIgdbResponseContent.gamesSearch.asResponseBody())
        }

        val config = IgdbClientConfig().apply {
            baseUrl = TEST_BASE_URL
            httpClient {
                callFactory = mockCallFactory
            }
        }
        val executor = OkhttpIgdbClientFactory(config).build()

        val response = executor.game(apicalypseQuery { })

        response.games.shouldNotBeEmpty()
    }

    @Test
    fun `Executor should make requests to the twitch server if twitch auth is configured`() = coroutinesExt.runTest {
        val twitchAuthRequested = AtomicBoolean(false)
        val mockCallFactory: Call.Factory = MockOkhttpCall.factory { call ->
            when (call.request().url) {
                "$TEST_BASE_URL/games.pb".toHttpUrl() -> createSuccessResponse(
                    call,
                    MockIgdbResponseContent.gamesSearch.asResponseBody(),
                )

                IgdbConstants.TWITCH_AUTH_URL -> {
                    twitchAuthRequested.set(true)
                    createSuccessResponse(
                        call,
                        """{"access_token":"testAccessToken"}""".toResponseBody(APPLICATION_JSON.toMediaType()),
                    )
                }

                else -> error("Requests to the `${call.request().url}` are not allowed")
            }
        }

        val config = IgdbClientConfig().apply {
            baseUrl = TEST_BASE_URL
            httpClient {
                callFactory = mockCallFactory
            }
            twitchAuth {
                clientId = Fixtures.TEST_CLIENT_ID
                clientSecret = "testClientSecret"
            }
        }
        val executor = OkhttpIgdbClientFactory(config).build()

        val response = executor.game(apicalypseQuery { })

        response.games.shouldNotBeEmpty()
        twitchAuthRequested.get() shouldBe true
    }

    @Test
    fun `Executor should retry requests on 429 error by default`() = coroutinesExt.runTest {
        val requestNo = AtomicLong(0)
        val mockCallFactory: Call.Factory = MockOkhttpCall.factory { call ->
            when (requestNo.incrementAndGet()) {
                1L, 2L -> createError429Response(call)
                else -> createSuccessResponse(call, MockIgdbResponseContent.gamesSearch.asResponseBody())
            }
        }

        val config = IgdbClientConfig().apply {
            baseUrl = TEST_BASE_URL
            httpClient {
                callFactory = mockCallFactory
            }
        }
        val executor = OkhttpIgdbClientFactory(config).build()

        val response = executor.game(apicalypseQuery { })

        response.games.shouldNotBeEmpty()
    }

    @Test
    fun `Executor should not retry requests on 429 error when retries disabled`() = coroutinesExt.runTest {
        val requestNo = AtomicLong(0)
        val mockCallFactory: Call.Factory = MockOkhttpCall.factory { call ->
            when (requestNo.incrementAndGet()) {
                1L, 2L -> createError429Response(call)
                else -> createSuccessResponse(call, MockIgdbResponseContent.gamesSearch.asResponseBody())
            }
        }

        val config = IgdbClientConfig().apply {
            baseUrl = TEST_BASE_URL
            httpClient {
                callFactory = mockCallFactory
            }

            retryPolicy {
                enabled = false
            }
        }
        val executor = OkhttpIgdbClientFactory(config).build()

        val response = kotlin.runCatching { executor.game(apicalypseQuery { }) }

        response.shouldBeFailure {
            (it as? IgdbHttpException)?.code shouldBe 429
        }
    }

    @Test
    fun `Custom retry policy configuration test`() = coroutinesExt.runTest {
        val requestNo = AtomicLong(0)
        val mockCallFactory: Call.Factory = MockOkhttpCall.factory { call ->
            when (requestNo.incrementAndGet()) {
                in 1L..10L -> createError429Response(call)
                else -> createSuccessResponse(call, MockIgdbResponseContent.gamesSearch.asResponseBody())
            }
        }

        val config = IgdbClientConfig().apply {
            baseUrl = TEST_BASE_URL
            httpClient {
                callFactory = mockCallFactory
            }

            retryPolicy {
                maxRequestRetries = 9
                initialDelay = 50.milliseconds
                factor = 1f
                delayRange = 100.milliseconds..1.minutes
                jitterFactor = 0f
            }
        }
        val executor = OkhttpIgdbClientFactory(config).build()

        val response = kotlin.runCatching { executor.game(apicalypseQuery { }) }

        response.shouldBeFailure {
            (it as? IgdbHttpException)?.code shouldBe 429
        }
        requestNo.get() shouldBe 10
        this.testScheduler.currentTime shouldBe 900
    }

    private companion object {
        const val TEST_BASE_URL = "https://test.host/v4"

        fun createSuccessResponse(call: Call, body: ResponseBody): Result<Response> = Result.success(
            Response.Builder()
                .request(call.request())
                .protocol(HTTP_1_1)
                .message("200 OK")
                .code(200)
                .body(body)
                .build(),
        )

        fun createError429Response(call: Call): Result<Response> = Result.success(
            Response.Builder()
                .request(call.request())
                .protocol(HTTP_1_1)
                .message("429 Too Many Requests")
                .code(429)
                .body("""{"message":"Too Many Requests"}""".toResponseBody(APPLICATION_JSON.toMediaType()))
                .build(),
        )
    }
}
