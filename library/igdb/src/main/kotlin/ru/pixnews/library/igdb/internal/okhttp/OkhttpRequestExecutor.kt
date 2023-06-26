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
import kotlinx.coroutines.Dispatchers
import okhttp3.Call.Factory
import okhttp3.HttpUrl
import okhttp3.Request.Builder
import okhttp3.RequestBody.Companion.toRequestBody
import ru.pixnews.library.igdb.IgdbResult
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import ru.pixnews.library.igdb.internal.IgdbConstants.Header
import ru.pixnews.library.igdb.internal.IgdbConstants.MediaType
import ru.pixnews.library.igdb.internal.IgdbErrorResponseParser
import ru.pixnews.library.igdb.internal.RequestExecutor
import ru.pixnews.library.igdb.internal.model.IgdbAuthToken
import java.io.InputStream

internal class OkhttpRequestExecutor(
    private val callFactory: Factory,
    private val baseUrl: HttpUrl,
    private val token: IgdbAuthToken? = null,
    private val userAgent: String? = null,
    private val headers: Map<String, List<String>> = emptyMap(),
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val httpErrorJsonParser: (InputStream) -> IgdbHttpErrorResponse = IgdbErrorResponseParser,
) : RequestExecutor {
    override suspend operator fun <T : Any> invoke(
        path: String,
        query: ApicalypseQuery,
        successResponseParser: (ApicalypseQuery, InputStream) -> T,
    ): IgdbResult<T, IgdbHttpErrorResponse> {
        val url = baseUrl.newBuilder().addPathSegment(path).build()
        val body = query.toString().toRequestBody(MediaType.TEXT_PLAIN)

        val okhttpRequest = Builder().apply {
            url(url)
            header("Accept", MediaType.APPLICATION_PROTOBUF)
            userAgent?.let {
                header("User-Agent", it)
            }
            token?.let {
                header(Header.CLIENT_ID, it.clientId)
                header(Header.AUTHORIZATION, "Bearer ${it.token}")
            }
            addUserDefinedHeaders()
            post(body)
        }.build()

        return callFactory
            .newCall(okhttpRequest)
            .executeAsyncWithResult()
            .toIgdbResult(
                query = query,
                successResponseParser = successResponseParser,
                errorResponseParser = { _, stream -> httpErrorJsonParser(stream) },
                backgroundDispatcher = backgroundDispatcher,
            )
    }

    private fun Builder.addUserDefinedHeaders() {
        this@OkhttpRequestExecutor.headers.forEach { (headerName, values) ->
            if (headerName.isSingleValueHeader()) {
                values.firstOrNull()?.let {
                    header(headerName, it)
                }
            } else {
                values.forEach { addHeader(headerName, it) }
            }
        }
    }

    private companion object {
        private val SINGLE_VALUE_HEADERS = setOf(
            Header.CLIENT_ID,
            Header.AUTHORIZATION,
        )

        private fun String.isSingleValueHeader(): Boolean = SINGLE_VALUE_HEADERS.any {
            it.equals(this, ignoreCase = true)
        }
    }
}
