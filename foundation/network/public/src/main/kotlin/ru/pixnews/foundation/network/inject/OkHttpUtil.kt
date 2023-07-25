/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.network.inject

import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import ru.pixnews.foundation.appconfig.NetworkConfig
import java.util.concurrent.TimeUnit.MILLISECONDS

public fun OkHttpClient.Builder.applyNetworkConfig(networkConfig: NetworkConfig): OkHttpClient.Builder {
    connectTimeout(networkConfig.connectTimeout.inWholeMilliseconds, MILLISECONDS)
    readTimeout(networkConfig.readTimeout.inWholeMilliseconds, MILLISECONDS)
    writeTimeout(networkConfig.writeTimeout.inWholeMilliseconds, MILLISECONDS)
    connectionPool(
        ConnectionPool(
            maxIdleConnections = networkConfig.connectionPoolMaxIdleConnections.toInt(),
            keepAliveDuration = networkConfig.connectionPoolKeepAliveTimeout.inWholeMilliseconds,
            timeUnit = MILLISECONDS,
        ),
    )
    dispatcher(
        Dispatcher().apply {
            maxRequestsPerHost = networkConfig.maxConnectionsPerHost.toInt()
        },
    )
    return this
}
