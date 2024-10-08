/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.test.okhttp.mockwebserver

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

public open class ConcatMockDispatcher(
    private val producers: List<(RecordedRequest) -> MockResponse?>,
    private val default: MockResponse = DEFAULT_MOCK_RESPONSE,
) : Dispatcher() {
    public constructor(
        dispatcher: (RecordedRequest) -> MockResponse?,
        default: MockResponse = DEFAULT_MOCK_RESPONSE,
    ) : this(listOf(dispatcher), default)

    override fun dispatch(request: RecordedRequest): MockResponse {
        val response = producers.firstNotNullOfOrNull { it(request) }
        return response ?: default
    }

    public companion object {
        public val DEFAULT_MOCK_RESPONSE: MockResponse
            get() = MockResponse()
                .setResponseCode(404)
                .setHeader("Content-Type", "application/json")
                .setBody("Not Found")
    }
}
