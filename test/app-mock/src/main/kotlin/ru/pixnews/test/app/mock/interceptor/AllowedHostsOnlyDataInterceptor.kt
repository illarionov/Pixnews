/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.interceptor

import co.touchlab.kermit.Logger
import okhttp3.Interceptor.Chain
import okhttp3.Response
import ru.pixnews.foundation.network.InterceptorWithPriority
import ru.pixnews.foundation.network.InterceptorWithPriority.InterceptorPriority
import ru.pixnews.test.app.mock.NetworkBehavior
import java.io.IOException
import java.util.Collections.synchronizedSet

public class AllowedHostsOnlyDataInterceptor(
    private val networkBehavior: NetworkBehavior,
    logger: Logger,
    defaultAcceptedHosts: Set<String> = setOf("localhost"),
) : InterceptorWithPriority {
    private val log = logger.withTag("AllowlistOnlyDataInterceptor")
    public val acceptedHosts: MutableSet<String> = synchronizedSet(mutableSetOf())
    override val priority: InterceptorPriority = InterceptorPriority(19)

    init {
        this.acceptedHosts += defaultAcceptedHosts
    }

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val rejected = request.url.host !in acceptedHosts &&
                (request.url.host !in networkBehavior.acceptedHosts)
        log.v { "Intercepting `$request`: ${if (rejected) "REJECTED" else "ACCEPTED"}" }

        if (rejected) {
            throw AccessDeniedException("Access to ${request.url.host} is prohibited by policy")
        }
        return chain.proceed(request)
    }

    public class AccessDeniedException(message: String) : IOException(message)
}
