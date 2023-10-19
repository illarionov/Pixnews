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
import java.net.NoRouteToHostException

public class NetworkBehaviorDataInterceptor(
    private val networkBehavior: NetworkBehavior,
    logger: Logger,
    ) : InterceptorWithPriority {
    private val log = logger.withTag("NetworkBehaviorDataInterceptor")
    override val priority: InterceptorPriority = InterceptorPriority(-18)

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        log.v { "Intercept `$request`" }

        if (!networkBehavior.networkConnected) {
            throw NoRouteToHostException("Network status set to disconnected by network behavior")
        }

        return chain.proceed(request)
    }
}
