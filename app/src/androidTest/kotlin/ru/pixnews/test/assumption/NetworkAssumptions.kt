/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.assumption

import com.squareup.wire.Message
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.auto.mockwebserver.dsl.RequestDefinition
import ru.auto.mockwebserver.dsl.RequestMatcher
import ru.auto.mockwebserver.dsl.RootRouting
import ru.auto.mockwebserver.dsl.RootRoutingDefinition
import ru.auto.mockwebserver.dsl.Route
import ru.auto.mockwebserver.dsl.get
import ru.auto.mockwebserver.dsl.pathEnd
import ru.auto.mockwebserver.dsl.post
import ru.auto.mockwebserver.dsl.route
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.igdbclient.IgdbEndpoint
import ru.pixnews.inject.MockWebServerHolder
import ru.pixnews.test.app.mock.NetworkBehavior
import ru.pixnews.test.app.mock.igdb.IgdbMockWebServer
import ru.pixnews.test.app.mock.igdb.mockIgdbResponse
import ru.pixnews.test.assumption.NetworkAssumptions.RouteType.ONE_OFF
import ru.pixnews.test.assumption.NetworkAssumptions.RouteType.STUB

class NetworkAssumptions : ExternalResource() {
    private lateinit var networkBehavior: NetworkBehavior
    private lateinit var igdbMockWebServer: IgdbMockWebServer
    private lateinit var igdbRouteRegistry: RouteRegistry

    override fun apply(base: Statement?, description: Description?): Statement {
        (PixnewsRootComponentHolder.appComponent as MockWebServerHolder).let {
            networkBehavior = it.networkBehavior
            igdbMockWebServer = it.igdbMockWebServer
            igdbRouteRegistry = RouteRegistry(igdbMockWebServer.rootRouting)
        }

        return super.apply(base, description)
    }

    override fun before() {
        super.before()
        networkBehavior.reset()
    }

    override fun after() {
        super.after()
        networkBehavior.reset()
        igdbRouteRegistry.reset()
    }

    fun assumeNetworkConnected() {
        networkBehavior.networkConnected = true
    }

    fun assumeNetworkDisconnected() {
        networkBehavior.networkConnected = false
    }

    fun assumeIgdbImageResponse(
        requestMatcher: RequestDefinition,
        response: (RecordedRequest) -> MockResponse,
    ) = assumeIgdbImageResponse(requestMatcher, STUB, response)

    fun assumeIgdbImageResponse(
        requestMatcher: RequestDefinition,
        type: RouteType = STUB,
        response: (RecordedRequest) -> MockResponse,
    ) {
        val matcher = RequestMatcher.Builder().apply {
            get()
            requestMatcher()
        }.build()
        igdbRouteRegistry.add(
            description = "IGDB image response override",
            matcher = matcher,
            response = response,
            type = type,
        )
    }

    fun <R : Any> assumeIgdMessageResponse(
        endpoint: IgdbEndpoint<R>,
        response: (RecordedRequest) -> Message<*, Nothing>,
    ) = assumeIgdMessageResponse(endpoint, STUB, response)

    fun <R : Any> assumeIgdMessageResponse(
        endpoint: IgdbEndpoint<R>,
        type: RouteType = STUB,
        response: (RecordedRequest) -> Message<*, Nothing>,
    ) = assumeIgdbResponse(
        endpoint = endpoint,
        type = type,
        response = { recordedRequest: RecordedRequest ->
            val message = response(recordedRequest)
            mockIgdbResponse(message)
        },
    )

    fun <R : Any> assumeIgdbResponse(
        endpoint: IgdbEndpoint<R>,
        type: RouteType = STUB,
        response: (RecordedRequest) -> MockResponse,
    ) {
        igdbRouteRegistry.add(
            description = "IGDB response override",
            matcher = RequestMatcher.Builder().apply {
                post()
                pathEnd(endpoint.protobufPath)
            }.build(),
            response = response,
            type = type,
        )
    }

    enum class RouteType {
        STUB,
        ONE_OFF,
    }

    private class RouteRegistry(
        private val rootRoting: RootRouting,
    ) {
        private val lock: Any = Any()
        private val routes: MutableList<Route> = mutableListOf()

        fun <T : Route> add(routingDefinition: RootRoutingDefinition<T>): T {
            val route = with(rootRoting) {
                routingDefinition()
            }
            routes.add(route)
            return route
        }

        fun add(
            description: String,
            matcher: RequestMatcher,
            type: RouteType,
            response: (RecordedRequest) -> MockResponse,
        ) {
            val route = with(rootRoting) {
                when (type) {
                    STUB -> stub {
                        route(description, matcher, response)
                    }

                    ONE_OFF -> oneOff {
                        route(description, matcher, response)
                    }
                }
            }
            routes.add(route)
        }

        fun reset() {
            synchronized(lock) {
                routes.forEach(rootRoting::remove)
                routes.clear()
            }
        }
    }
}
