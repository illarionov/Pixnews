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
import java.io.IOException

class AllowedHostsOnlyDataInterceptorTest {
    val server = MockWebServer()
    val logger = TestingLoggers.consoleLogger

    @BeforeEach
    fun setup() {
        server.start { _ ->
            MockResponse().setResponseCode(200).setBody("OK")
        }
    }

    @AfterEach
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `interceptor should accept requests from allowed hosts`() {
        val interceptor = AllowedHostsOnlyDataInterceptor(
            networkBehavior = NetworkBehavior(),
            logger = logger,
            defaultAcceptedHosts = setOf("localhost"),
        )
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

    @Test
    fun `interceptor should deny requests from not hosts`() {
        val interceptor = AllowedHostsOnlyDataInterceptor(
            networkBehavior = NetworkBehavior(),
            logger = logger,
            defaultAcceptedHosts = setOf(),
        )
        val client = OkHttpClient.Builder()
            .setupForTest()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url(server.url("/"))
            .build()

        shouldThrow<IOException> {
            client.newCall(request).execute()
        }
    }

    @Test
    fun `interceptor should take into account network behavior`() {
        val networkBehavior = NetworkBehavior()
        val interceptor = AllowedHostsOnlyDataInterceptor(
            networkBehavior = networkBehavior,
            logger = logger,
            defaultAcceptedHosts = setOf(),
        )
        val client = OkHttpClient.Builder()
            .setupForTest()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url(server.url("/"))
            .build()

        networkBehavior.acceptedHosts = setOf("localhost")

        val response = client.newCall(request).execute().use {
            it.body!!.string()
        }

        response shouldBe "OK"
    }
}
