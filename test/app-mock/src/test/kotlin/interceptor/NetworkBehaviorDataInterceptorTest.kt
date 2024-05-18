/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.interceptor

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.pixnews.library.test.TestingLoggers
import ru.pixnews.library.test.okhttp.mockwebserver.start
import ru.pixnews.library.test.okhttp.setupForTest
import ru.pixnews.test.app.mock.NetworkBehavior
import java.net.SocketException

class NetworkBehaviorDataInterceptorTest {
    val server = MockWebServer()
    val logger = TestingLoggers.consoleLogger

    @BeforeEach
    fun setUp() {
        server.start { _ -> MockResponse().setResponseCode(200).setBody("OK") }
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `interceptor should short-circuit requests if the network is disabled`() {
        val behavior = NetworkBehavior()
        val interceptor = NetworkBehaviorDataInterceptor(behavior, logger)
        val client = OkHttpClient.Builder()
            .setupForTest()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url(server.url("/"))
            .build()

        behavior.networkConnected = false

        shouldThrow<SocketException> {
            client.newCall(request).execute()
        }
    }

    @Test
    fun `interceptor should pass request if the network is not disabled`() {
        val behavior = NetworkBehavior()
        val interceptor = NetworkBehaviorDataInterceptor(behavior, logger)
        val client = OkHttpClient.Builder()
            .setupForTest()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url(server.url("/"))
            .build()

        val response = client.newCall(request).execute().use {
            it.body!!.string()
        }

        response shouldBe "OK"
    }
}
