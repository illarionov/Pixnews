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

import io.kotest.matchers.collections.shouldHaveSize
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.library.igdb.IgdbEndpoint.Companion.countEndpoint
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery.Companion.apicalypseQuery
import ru.pixnews.library.igdb.auth.model.TwitchToken
import ru.pixnews.library.igdb.auth.twitch.InMemoryTwitchTokenStorage
import ru.pixnews.library.igdb.auth.twitch.TwitchTokenPayload.Companion.toTokenPayload
import ru.pixnews.library.igdb.model.Game
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.TestingLoggers
import ru.pixnews.library.test.okhttp.TestingLoggerInterceptor
import java.util.Properties
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.time.Duration.Companion.seconds

@EnabledIfEnvironmentVariable(named = "MANUAL", matches = ".+", disabledReason = "Only for manual execution")
class RealNetworkTestClient {
    private val logger = TestingLoggers.consoleLogger.withTag("okhttp")

    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()
    val testTokenProperties = TestTokenProperties.loadFromResources()
    private val rootOkhttpClient = OkHttpClient.Builder().apply {
        addNetworkInterceptor(TestingLoggerInterceptor())
        connectionPool(
            ConnectionPool(
                maxIdleConnections = 8,
                keepAliveDuration = 2000,
                timeUnit = MILLISECONDS,
            ),
        )
        dispatcher(
            Dispatcher().apply {
                maxRequestsPerHost = 8
            },
        )
    }.build()
    private val client = IgdbClient {
        userAgent = "igdbclient/1.0.0-alpha1"

        httpClient {
            callFactory = rootOkhttpClient
        }

        twitchAuth {
            clientId = testTokenProperties.clientId!!
            clientSecret = testTokenProperties.clientSecret!!
            storage = InMemoryTwitchTokenStorage(
                token = TwitchToken(access_token = testTokenProperties.token!!).toTokenPayload(),
            )
        }

        headers {
            set("Header1", "testHeader")
            append("Header2", "testHeader2")
        }

        retryPolicy {
            maxRequestRetries = 100
            initialDelay = 10.seconds
        }
    }

    @Test
    fun executeRequest() = runBlocking {
        val response = client.game {
            search("Diablo")
            fields("id")
            limit(2)
        }
        logger.i { "responses: $response" }

        response.games shouldHaveSize 2

        val response2 = client.game {
            search("War cRaft")
            fields("id")
            limit(1)
        }

        response2.games shouldHaveSize 1

        Unit
    }

    @Test
    fun executeManySimultaneousRequests() = runBlocking {
        val responses = (1..40).map {
            async {
                client.game {
                    search("Diablo $it")
                    fields("id")
                    limit(1)
                }
            }
        }.awaitAll()

        logger.i { "responses: $responses" }
    }

    @Test
    fun testMultiQuery() = runBlocking {
        val response = client.multiquery {
            query(IgdbEndpoint.PLATFORM.countEndpoint(), "Count of Platforms") {}
            query(IgdbEndpoint.GAME, "Playstation Games") {
                fields("name", "category", "platforms.name")
                where("platforms !=n ")
                limit(5)
            }
        }

        @Suppress("UNCHECKED_CAST")
        val responseGames: List<Game>? = response[1].results as List<Game>?

        logger.i { "response2: $responseGames" }
    }

    @Test
    fun testCountResponse() = runBlocking {
        val diabloGamesCount = client.executeOrThrow(
            IgdbEndpoint.GAME.countEndpoint(),
            apicalypseQuery {
                fields("*")
                search("Diablo")
            },
        )

        logger.i { "games count: $diabloGamesCount" }
    }

    class TestTokenProperties(
        val clientId: String?,
        val clientSecret: String?,
        val token: String?,
    ) {
        companion object {
            private const val TEST_TOKEN_PROPERTIES_FILES = "/test_token.properties"
            fun loadFromResources(): TestTokenProperties {
                val stream = TestTokenProperties::class.java.getResourceAsStream(TEST_TOKEN_PROPERTIES_FILES)
                    ?: error("No resource `$TEST_TOKEN_PROPERTIES_FILES`")
                return stream.use {
                    val properties = Properties().apply {
                        load(stream)
                    }
                    TestTokenProperties(
                        clientId = properties.getProperty("client_id"),
                        clientSecret = properties.getProperty("client_secret"),
                        token = properties.getProperty("token"),
                    )
                }
            }
        }
    }
}
