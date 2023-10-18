/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.mockokhttp

import android.content.Context
import co.touchlab.kermit.Logger
import okhttp3.Interceptor.Chain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol.HTTP_1_1
import okhttp3.Request
import okhttp3.Response
import okhttp3.Response.Builder
import okhttp3.ResponseBody.Companion.asResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.buffer
import okio.source
import ru.pixnews.foundation.network.InterceptorWithPriority
import ru.pixnews.foundation.network.InterceptorWithPriority.InterceptorPriority
import javax.inject.Provider

public class MockDataInterceptor(
    private val context: Provider<Context>,
    logger: Logger,
    ) : InterceptorWithPriority {
    private val log = logger.withTag("MockDataInterceptor")
    override val priority: InterceptorPriority = InterceptorPriority(-2)

    init {
        log.i { "MockDataInterceptor initialized" }
    }

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        log.v { "Intercept `$request`" }

        return when (request.method) {
            "GET" -> formatGetResponse(request)
            else -> Builder()
                .request(request)
                .body(
                    "Unsupported method `${request.method}` on `${request.url}`"
                        .toResponseBody("text/plain".toMediaType()),
                )
                .code(405)
                .message("Unsupported method `${request.method}`")
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

        return Builder()
            .request(request)
            .body(source)
            .message("OK")
            .code(200)
            .protocol(HTTP_1_1)
            .build()
    }
}
