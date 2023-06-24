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
package ru.pixnews.library.igdb.internal.twitch

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.auth.model.TwitchToken
import ru.pixnews.library.igdb.auth.twitch.TwitchTokenPayload
import ru.pixnews.library.igdb.auth.twitch.TwitchTokenStorage
import ru.pixnews.library.igdb.error.IgdbException
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse.Message
import ru.pixnews.library.igdb.internal.RequestExecutor
import ru.pixnews.library.igdb.internal.model.IgdbAuthToken
import ru.pixnews.library.igdb.internal.model.IgdbResult
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.ApiFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.HttpFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.NetworkFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.UnknownFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Failure.UnknownHttpCodeFailure
import ru.pixnews.library.igdb.internal.model.IgdbResult.Success
import java.io.IOException
import java.io.InputStream

internal class TwitchAuthenticationRequestDecorator(
    private val credentials: TwitchCredentials,
    private val tokenStorage: TwitchTokenStorage,
    private val maxRequestRetries: Int = 3,
    private val twitchTokenFetcher: suspend (TwitchCredentials) -> IgdbResult<TwitchToken, TwitchErrorResponse>,
    requestExecutorFactory: (token: IgdbAuthToken?) -> RequestExecutor,
) : RequestExecutor {
    private val fetchTokenMutex = Mutex()
    private val requestExecutorProvider = CachedRequestExecutor(credentials, requestExecutorFactory)

    override suspend fun <T : Any> invoke(
        endpoint: String,
        query: ApicalypseQuery,
        successResponseParser: (InputStream) -> T,
    ): IgdbResult<T, IgdbHttpErrorResponse> = SingleRequestExecutor(endpoint, query, successResponseParser).execute()

    private inner class SingleRequestExecutor<out T : Any>(
        private val endpoint: String,
        private val query: ApicalypseQuery,
        private val successResponseParser: (InputStream) -> T,
        private val maxFetchTokenAttempts: Int = this@TwitchAuthenticationRequestDecorator.maxRequestRetries,
        private val maxRequestRetries: Int = this@TwitchAuthenticationRequestDecorator.maxRequestRetries,
    ) {
        private var requests = 0
        private var fetchTokenRequests = 0

        suspend fun execute(): IgdbResult<T, IgdbHttpErrorResponse> {
            var lastResponse: IgdbResult<T, IgdbHttpErrorResponse> = UnknownFailure(null)
            while (requests < maxRequestRetries) {
                val getTokenResult = fetchTokenMutex.withLock {
                    getFreshToken()
                }
                val (tokenPayload, executor) = when (getTokenResult) {
                    is Success -> getTokenResult.value
                    is Failure -> return getTokenResult
                }

                lastResponse = executor.invoke(endpoint, query, successResponseParser)

                if (!lastResponse.is401UnauthorizedFailure()) {
                    return lastResponse
                }

                // Invalidate token
                tokenStorage.updateToken(tokenPayload, TwitchTokenPayload.NO_TOKEN)
                requests += 1
            }
            return lastResponse
        }

        @Suppress("ReturnCount")
        private suspend fun getFreshToken(): IgdbResult<TokenPayloadWithExecutor, IgdbHttpErrorResponse> {
            repeat(MAX_COMMIT_FRESH_TOKEN_ATTEMPTS) {
                val storedTokenPayload = tokenStorage.getToken()
                requestExecutorProvider(storedTokenPayload)?.let {
                    return Success(TokenPayloadWithExecutor(storedTokenPayload, it))
                }

                if (fetchTokenRequests >= maxFetchTokenAttempts) {
                    return ApiFailure(
                        RequestTokenException(
                            "Number of attempts to get a fresh token exceeded the limit of $maxFetchTokenAttempts",
                        ),
                    )
                }

                val fetchTokenResult = twitchTokenFetcher(credentials).asIgdbResult()
                if (fetchTokenResult is Failure) {
                    return fetchTokenResult
                }
                fetchTokenRequests += 1

                val fetchedTokenPayload = (fetchTokenResult as Success<TwitchTokenPayload>).value
                tokenStorage.updateToken(storedTokenPayload, fetchedTokenPayload)
            }

            return UnknownFailure(RequestTokenException("Failed to fetch token"))
        }
    }

    class RequestTokenException(message: String?) : IgdbException(message)

    private class CachedRequestExecutor(
        private val twitchCredentials: TwitchCredentials,
        private val requestExecutorFactory: (token: IgdbAuthToken?) -> RequestExecutor,
    ) : (TwitchTokenPayload) -> RequestExecutor? {
        private val lock: Any = Any()
        private var cachedTokenPayload: TwitchTokenPayload = TwitchTokenPayload.NO_TOKEN
        private var exeutor: RequestExecutor = dummyRequestExecutor

        override fun invoke(tokenPayload: TwitchTokenPayload): RequestExecutor? = synchronized(lock) {
            if (tokenPayload == cachedTokenPayload && exeutor != dummyRequestExecutor) {
                return exeutor
            }

            val twitchToken = tokenPayload.deserializeToken()?.toIgdbToken(twitchCredentials)
            return if (twitchToken != null) {
                return requestExecutorFactory(twitchToken).also {
                    exeutor = it
                    cachedTokenPayload = tokenPayload
                }
            } else {
                null
            }
        }
    }

    private class TokenPayloadWithExecutor(
        val payload: TwitchTokenPayload,
        val executor: RequestExecutor,
    ) {
        operator fun component1() = payload
        operator fun component2() = executor
    }

    companion object {
        internal const val MAX_COMMIT_FRESH_TOKEN_ATTEMPTS = 3
        private val dummyRequestExecutor = object : RequestExecutor {
            override suspend fun <T : Any> invoke(
                endpoint: String,
                query: ApicalypseQuery,
                successResponseParser: (InputStream) -> T,
            ): IgdbResult<T, Nothing> = UnknownFailure(null)
        }

        @Suppress("MagicNumber")
        private fun IgdbResult<*, *>.is401UnauthorizedFailure() = this is HttpFailure<*> &&
                this.httpCode == 401

        private fun TwitchTokenPayload.deserializeToken(): TwitchToken? {
            if (isEmpty()) {
                return null
            }
            return try {
                val token = TwitchToken.ADAPTER.decode(payload)
                if (token.access_token.isNotEmpty()) token else null
            } catch (@Suppress("SwallowedException") exception: IOException) {
                null
            }
        }

        private fun TwitchToken.toIgdbToken(credentials: TwitchCredentials): IgdbAuthToken = object : IgdbAuthToken {
            override val clientId: String = credentials.clientId
            override val token: String = this@toIgdbToken.access_token
        }

        private fun IgdbResult<TwitchToken, TwitchErrorResponse>.asIgdbResult():
                IgdbResult<TwitchTokenPayload, IgdbHttpErrorResponse> = when (this) {
            is Success -> Success(TwitchTokenPayload(payload = value.encode()))
            is ApiFailure -> this
            is NetworkFailure -> this
            is UnknownFailure -> this
            is UnknownHttpCodeFailure -> this
            is HttpFailure -> HttpFailure(
                httpCode = httpCode,
                httpMessage = httpMessage,
                response = response?.let { twitchResponse ->
                    IgdbHttpErrorResponse(
                        messages = listOf(
                            Message(
                                status = twitchResponse.status,
                                title = twitchResponse.message,
                                cause = "Twitch error",
                            ),
                        ),
                    )
                },
                rawResponseBody = this@asIgdbResult.rawResponseBody,
                rawResponseHeaders = this@asIgdbResult.rawResponseHeaders,
            )
        }
    }
}
