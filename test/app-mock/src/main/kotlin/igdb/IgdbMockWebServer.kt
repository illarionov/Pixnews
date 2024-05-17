/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.igdb

import android.content.res.AssetManager
import co.touchlab.kermit.Logger
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import ru.auto.mockwebserver.dsl.RootRouting
import ru.auto.mockwebserver.dsl.RootRoutingDefinition
import ru.auto.mockwebserver.dsl.RoutingDispatcher
import ru.auto.mockwebserver.dsl.get
import ru.auto.mockwebserver.dsl.pathContains
import ru.pixnews.test.app.mock.assets.ResponseFromAssetsReader
import ru.pixnews.test.app.mock.igdb.IgdbConstants.IGDB_IMAGES_HOST
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

public class IgdbMockWebServer(
    assets: () -> AssetManager,
    logger: Logger = Logger,
) {
    public val webServer: MockWebServer = MockWebServer()
    private val logger = logger.withTag("IgdbMockWebServer")
    private val dispatcher = RoutingDispatcher()
    public val rootRouting: RootRouting
        get() = dispatcher.routing
    private val pendingDispatcherInit: PendingDispatcherInitialization = PendingDispatcherInitialization(dispatcher)
    private val igdbImagesFromAssetsReader = ResponseFromAssetsReader(
        assets = assets,
        originalHost = IGDB_IMAGES_HOST,
    )

    init {
        dispatcher.routing.stub {
            get(
                description = "Default images from igdb.images.com",
                request = {
                    pathContains("/igdb/image/")
                },
                response = igdbImagesFromAssetsReader,
            )
        }
        webServer.dispatcher = dispatcher
    }

    public fun start(port: Int = 0) {
        webServer.start(port)
    }

    public fun shutdown() {
        webServer.shutdown()
        dispatcher.after()
        pendingDispatcherInit.checkAwaitElapsed()
    }

    public fun <T> routing(routingDefinition: RootRoutingDefinition<T>): T =
        dispatcher.routing.routingDefinition()

    public fun await(timeout: Duration = DEFAULT_AWAIT_TIMEOUT): Unit = pendingDispatcherInit.await(timeout)

    public fun unlock(): Unit = pendingDispatcherInit.unlock()

    public fun getMockUrl(url: HttpUrl): HttpUrl {
        val mockWebServerUrl = webServer.url("/")
        return url.newBuilder()
            .scheme(mockWebServerUrl.scheme)
            .host(mockWebServerUrl.host)
            .port(mockWebServerUrl.port)
            .build()
    }

    private class PendingDispatcherInitialization(
        private val dispatcher: RoutingDispatcher,
    ) {
        private val lock: Any = Any()
        private var awaitTimeout: Duration = DEFAULT_AWAIT_TIMEOUT
        private var latch: CountDownLatch? = null
        private var isAwaitElapsed = false

        fun await(timeout: Duration) {
            synchronized(lock) {
                if (latch != null) {
                    return
                }
                latch = CountDownLatch(1)
                awaitTimeout = timeout
                dispatcher
            }.await(timeout.inWholeMilliseconds, MILLISECONDS)
        }

        fun unlock() {
            synchronized(lock) {
                val tmpLatch = latch ?: return
                latch = null
                tmpLatch
            }.countDown()
        }

        fun checkAwaitElapsed() = synchronized(lock) {
            if (isAwaitElapsed || dispatcher.isAwaitElapsed) {
                error("MockWebServer waiting time elapsed before unlock called")
            }
        }
    }

    private companion object {
        private val DEFAULT_AWAIT_TIMEOUT: Duration = 20.seconds
    }
}
