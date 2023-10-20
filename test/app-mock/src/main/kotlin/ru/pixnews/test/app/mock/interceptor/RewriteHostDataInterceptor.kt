/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.interceptor

import co.touchlab.kermit.Logger
import okhttp3.HttpUrl
import okhttp3.Interceptor.Chain
import okhttp3.Response
import ru.pixnews.foundation.network.InterceptorWithPriority
import ru.pixnews.foundation.network.InterceptorWithPriority.InterceptorPriority
import ru.pixnews.test.app.mock.NetworkBehavior

public class RewriteHostDataInterceptor(
    private val networkBehavior: NetworkBehavior,
    logger: Logger,
    private val defaultRewrites: Map<String, (HttpUrl) -> HttpUrl> = emptyMap(),
) : InterceptorWithPriority {
    private val log = logger.withTag("RewriteHostDataInterceptor")
    private val lock: Any = Any()
    private val rewrites: MutableMap<String, (HttpUrl) -> HttpUrl> = mutableMapOf()
    override val priority: InterceptorPriority = InterceptorPriority(15)

    init {
        this.rewrites += defaultRewrites
    }

    public fun setUrlRewrite(host: String, rewrite: (HttpUrl) -> HttpUrl): Unit = synchronized(lock) {
        rewrites[host] = rewrite
    }

    public fun resetRewrites(): Unit = synchronized(lock) {
        rewrites.clear()
        rewrites += defaultRewrites
    }

    override fun intercept(chain: Chain): Response {
        val request = chain.request()

        val originalUrl = request.url
        val newUrl = rewriteUrl(originalUrl)
        if (newUrl == originalUrl) {
            return chain.proceed(request)
        }
        log.v { "Change url on request `$request` to $newUrl" }

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }

    private fun rewriteUrl(url: HttpUrl): HttpUrl = synchronized(lock) {
        var newUrl = url
        @Suppress("UnusedPrivateProperty", "LoopWithTooManyJumpStatements")
        for (rewriteNo in 1..MAX_REWRITES) {
            val mapper = rewrites[newUrl.host]
                ?: networkBehavior.urlRewrites[newUrl.host]
                ?: break

            val mappedUrl = mapper(newUrl)
            if (mappedUrl != newUrl) {
                newUrl = mappedUrl
            } else {
                break
            }
        }
        newUrl
    }

    private companion object {
        const val MAX_REWRITES = 50
    }
}
