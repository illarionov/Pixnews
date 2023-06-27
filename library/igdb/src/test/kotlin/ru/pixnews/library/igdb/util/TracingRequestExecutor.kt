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
package ru.pixnews.library.igdb.util

import ru.pixnews.library.igdb.IgdbResult
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import ru.pixnews.library.igdb.internal.RequestExecutor
import java.io.InputStream
import java.util.concurrent.atomic.AtomicLong

internal class TracingRequestExecutor(
    private val delegate: suspend (
        endpoint: String,
        query: ApicalypseQuery,
        successResponseParser: (ApicalypseQuery, InputStream) -> Any,
        requestNo: Long,
    ) -> IgdbResult<Any, IgdbHttpErrorResponse>,
) : RequestExecutor {
    private val _invokeCount: AtomicLong = AtomicLong(0)
    val invokeCount: Long
        get() = _invokeCount.get()

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Any> invoke(
        path: String,
        query: ApicalypseQuery,
        successResponseParser: (ApicalypseQuery, InputStream) -> T,
    ): IgdbResult<T, IgdbHttpErrorResponse> {
        val requestNo = _invokeCount.incrementAndGet()
        return delegate(
            path,
            query,
            successResponseParser,
            requestNo,
        ) as IgdbResult<T, IgdbHttpErrorResponse>
    }
}
