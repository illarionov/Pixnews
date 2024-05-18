/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.interceptor

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

class RewriteHostDataInterceptorTest {
    val server = MockWebServer()
    val logger = TestingLoggers.consoleLogger

    @BeforeEach
    fun setUp() {
        server.start { recordedRequest ->
            when (recordedRequest.path) {
                "/test" -> MockResponse().setResponseCode(200).setBody("OK")
                "/test2" -> MockResponse().setResponseCode(200).setBody("OK2")
                else -> MockResponse().setResponseCode(404).setBody("Not Found")
            }
        }
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `interceptor should rewrite host from default rewrites`() {
        val interceptor = RewriteHostDataInterceptor(
            networkBehavior = NetworkBehavior(),
            logger = logger,
            defaultRewrites = mapOf(
                "example.com" to { _ -> server.url("/test") },
            ),
        )

        val client = OkHttpClient.Builder()
            .setupForTest()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url("http://example.com/old?url")
            .build()

        val response = client.newCall(request).execute().use {
            it.body!!.string()
        }

        response shouldBe "OK"
    }

    @Test
    fun `interceptor should pass request not from rewrites unchanged`() {
        val interceptor = RewriteHostDataInterceptor(
            networkBehavior = NetworkBehavior(),
            logger = logger,
            defaultRewrites = mapOf(
                "example.com" to { _ -> server.url("/test") },
            ),
        )

        val client = OkHttpClient.Builder()
            .setupForTest()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url(server.url("/test2"))
            .build()

        val response = client.newCall(request).execute().use {
            it.body!!.string()
        }

        response shouldBe "OK2"
    }

    @Test
    fun `interceptor should take into account network behavior`() {
        val networkBehavior = NetworkBehavior()
        val interceptor = RewriteHostDataInterceptor(
            networkBehavior = networkBehavior,
            logger = logger,
            defaultRewrites = mapOf(),
        )

        val client = OkHttpClient.Builder()
            .setupForTest()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url("http://example.com/old?url")
            .build()

        networkBehavior.urlRewrites = mapOf(
            "example.com" to { _ -> server.url("/test") },
        )

        val response = client.newCall(request).execute().use {
            it.body!!.string()
        }

        response shouldBe "OK"
    }
}
