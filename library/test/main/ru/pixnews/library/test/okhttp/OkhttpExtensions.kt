/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.test.okhttp

import okhttp3.OkHttpClient
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

public fun OkHttpClient.Builder.setupForTest(
    @Suppress("NewApi") timeout: Duration = 500.milliseconds,
): OkHttpClient.Builder = apply {
    val timeoutDuration = java.time.Duration.ofMillis(timeout.inWholeMilliseconds)
    connectTimeout(timeoutDuration)
    callTimeout(timeoutDuration)
    readTimeout(timeoutDuration)
    writeTimeout(timeoutDuration)
    addNetworkInterceptor(TestingLoggerInterceptor())
}
