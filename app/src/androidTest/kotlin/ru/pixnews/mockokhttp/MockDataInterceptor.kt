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
package ru.pixnews.mockokhttp

import android.content.Context
import co.touchlab.kermit.Logger
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol.HTTP_1_1
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.asResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.buffer
import okio.source
import javax.inject.Provider

class MockDataInterceptor(
    private val context: Provider<Context>,
    logger: Logger,
    ) : Interceptor {
    private val log = logger.withTag("MockDataInterceptor")

    init {
        log.i { "MockDataInterceptor initialized" }
    }

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        log.v { "Intercept `$request`" }

        return when (request.method) {
            "GET" -> formatGetResponse(request)
            else -> Response.Builder()
                .request(request)
                .body(
                    "Unsupported method `${request.method}` on `${request.url}`"
                        .toResponseBody("text/plain".toMediaType()),
                )
                .code(405)
                .addHeader("Allow", "GET")
                .protocol(HTTP_1_1)
                .build()
        }
    }

    private fun formatGetResponse(request: Request): Response {
        val url = request.url

        val assetsResource = AssetsResources.getResource(url)
        val fd = context.get().assets.openFd(assetsResource.assetsPrefix)
        val contentLength = fd.length

        val source = fd.createInputStream()
            .source()
            .buffer()
            .asResponseBody(
                contentType = assetsResource.mediaType,
                contentLength = contentLength,
            )

        return Response.Builder()
            .request(request)
            .body(source)
            .message("OK")
            .code(200)
            .protocol(HTTP_1_1)
            .build()
    }
}
