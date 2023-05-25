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

import com.squareup.wire.Instant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Request
import ru.pixnews.library.igdb.auth.model.TwitchToken
import ru.pixnews.library.igdb.internal.IgdbConstants
import ru.pixnews.library.igdb.internal.IgdbConstants.MediaType
import ru.pixnews.library.igdb.internal.model.IgdbResult
import ru.pixnews.library.igdb.internal.okhttp.executeAsyncWithResult
import ru.pixnews.library.igdb.internal.okhttp.toIgdbResult
import java.io.InputStream

/**
 * Twitch Client Credentials Grant Flow fetcher
 */
internal class OkhttpTwitchTokenFetcher(
    private val callFactory: Call.Factory,
    private val baseUrl: HttpUrl = IgdbConstants.TWITCH_AUTH_URL,
    private val userAgent: String? = null,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val twitchTokenParser: (InputStream) -> TwitchToken = TwitchTokenParser,
    private val twitchErrorResponseParser: (InputStream) -> TwitchErrorResponse = TwitchTokenErrorResponseParser,
    private val tokenTimestampSource: () -> Long = System::currentTimeMillis,
) : suspend (TwitchCredentials) -> IgdbResult<TwitchToken, TwitchErrorResponse> {
    override suspend fun invoke(credentials: TwitchCredentials): IgdbResult<TwitchToken, TwitchErrorResponse> {
        val body = FormBody.Builder()
            .add("client_id", credentials.clientId)
            .add("client_secret", credentials.clientSecret)
            .add("grant_type", CLIENT_CREDENTIALS_GRANT_TYPE)
            .build()
        val request = Request.Builder().apply {
            url(baseUrl)
            addHeader("Accept", MediaType.APPLICATION_JSON)
            userAgent?.let {
                addHeader("User-Agent", it)
            }
            post(body)
        }.build()

        val tokenReceivedTimestamp = tokenTimestampSource()
        return callFactory
            .newCall(request)
            .executeAsyncWithResult()
            .toIgdbResult(
                successResponseParser = { inputStream ->
                    @Suppress("MagicNumber")
                    twitchTokenParser(inputStream).copy(
                        receive_timestamp = Instant.ofEpochSecond(
                            tokenReceivedTimestamp / 1000,
                            tokenReceivedTimestamp % 1000L * 1_000_000L,
                        ),
                    )
                },
                errorResponseParser = twitchErrorResponseParser,
                backgroundDispatcher = backgroundDispatcher,
            )
    }

    private companion object {
        const val CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials"
    }
}
