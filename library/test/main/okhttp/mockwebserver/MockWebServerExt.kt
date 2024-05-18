/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.test.okhttp.mockwebserver

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

public fun MockWebServer.takeRequestWithTimeout(
    timeout: Duration = 10.seconds,
): RecordedRequest = checkNotNull(takeRequest(timeout = timeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)) {
    "timeout during takeRequest()"
}

public fun MockWebServer.start(
    response: (RecordedRequest) -> MockResponse? = { null },
) {
    start(0, response)
}

public fun MockWebServer.start(
    port: Int = 0,
    response: (RecordedRequest) -> MockResponse? = { null },
) {
    val testServerDispatcher = ConcatMockDispatcher(response)
    dispatcher = testServerDispatcher
    start(port)
}
