/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.app.mock.igdb

import com.squareup.wire.Message
import okhttp3.mockwebserver.MockResponse
import okio.Buffer

public fun mockIgdbResponse(
    message: Message<*, Nothing>,
): MockResponse {
    val buffer = Buffer()
    message.encode(buffer)

    return MockResponse()
        .setResponseCode(200)
        .setHeader("Content-Type", "application/protobuf")
        .setBody(buffer)
}
