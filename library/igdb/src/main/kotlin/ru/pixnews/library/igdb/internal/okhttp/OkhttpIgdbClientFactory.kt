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

import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Call.Factory
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import ru.pixnews.library.igdb.IgdbClient
import ru.pixnews.library.igdb.auth.twitch.InMemoryTwitchTokenStorage
import ru.pixnews.library.igdb.dsl.IgdbClientConfig
import ru.pixnews.library.igdb.internal.IgdbClientImplementation
import ru.pixnews.library.igdb.internal.RequestExecutor
import ru.pixnews.library.igdb.internal.RetryDecorator
import ru.pixnews.library.igdb.internal.model.IgdbAuthToken
import ru.pixnews.library.igdb.internal.twitch.OkhttpTwitchTokenFetcher
import ru.pixnews.library.igdb.internal.twitch.TwitchAuthenticationRequestDecorator
import ru.pixnews.library.igdb.internal.twitch.TwitchCredentials

internal class OkhttpIgdbClientFactory(
    private val config: IgdbClientConfig,
) {
    fun build(): IgdbClient = IgdbClientImplementation(buildRequestExecutor())

    private fun buildRequestExecutor(): RequestExecutor {
        val callFactory = config.httpClientConfig.callFactory ?: OkHttpClient()
        val backgroundDispatcher = config.httpClientConfig.backgroundDispatcher

        var requestExecutorFactory: (IgdbAuthToken?) -> RequestExecutor = OkhttpIgdbRequestExecutorFactory(
            callFactory = callFactory,
            baseUrl = config.baseUrl.toHttpUrl(),
            backgroundDispatcher = backgroundDispatcher,
            userAgent = config.userAgent,
            headers = config.headers,
        )
        with(config.retryPolicy) {
            if (enabled) {
                val delegate = requestExecutorFactory
                requestExecutorFactory = { token ->
                    RetryDecorator(
                        initialInterval = this.initialDelay,
                        factor = this.factor,
                        maxAttempts = this.maxRequestRetries?.let { it + 1 },
                        delayRange = this.delayRange,
                        jitterFactor = this.jitterFactor,
                        delegate = delegate(token),
                    )
                }
            }
        }

        val twitchAuthConfig = config.twitchAuthConfig
        return if (twitchAuthConfig.enabled) {
            val credentials = object : TwitchCredentials {
                override val clientId: String = twitchAuthConfig.clientId
                override val clientSecret: String = twitchAuthConfig.clientSecret
            }
            val tokenStorage = twitchAuthConfig.storage ?: InMemoryTwitchTokenStorage()

            TwitchAuthenticationRequestDecorator(
                credentials = credentials,
                tokenStorage = tokenStorage,
                twitchTokenFetcher = OkhttpTwitchTokenFetcher(
                    callFactory = callFactory,
                    backgroundDispatcher = backgroundDispatcher,
                    userAgent = config.userAgent,
                ),
                maxRequestRetries = twitchAuthConfig.maxRequestRetries,
                requestExecutorFactory = requestExecutorFactory,
            )
        } else {
            requestExecutorFactory(null)
        }
    }

    private class OkhttpIgdbRequestExecutorFactory(
        private val callFactory: Factory,
        private val baseUrl: HttpUrl,
        private val backgroundDispatcher: CoroutineDispatcher,
        private val userAgent: String?,
        private val headers: Map<String, List<String>>,
    ) : (IgdbAuthToken?) -> RequestExecutor {
        override fun invoke(token: IgdbAuthToken?): RequestExecutor = OkhttpRequestExecutor(
            callFactory = callFactory,
            baseUrl = baseUrl,
            token = token,
            userAgent = userAgent,
            headers = headers,
            backgroundDispatcher = backgroundDispatcher,
        )
    }
}
