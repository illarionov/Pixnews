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
package ru.pixnews.library.igdb.internal

import ru.pixnews.library.igdb.IgdbResult
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import java.io.InputStream

internal interface RequestExecutor {
    suspend operator fun <T : Any> invoke(
        request: IgdbRequest,
    ): IgdbResult<T, IgdbHttpErrorResponse>
}

internal sealed class IgdbRequest {
    internal class ApicalypsePostRequest<out T : Any>(
        val path: String,
        val query: ApicalypseQuery,
        val successResponseParser: (ApicalypseQuery, InputStream) -> T,
    ) : IgdbRequest()

    internal class FormUrlEncodedPostRequest<out T : Any>(
        val path: String,
        val queryParameters: Map<String, String> = mapOf(),
        val formUrlEncodedParameters: Map<String, String> = mapOf(),
        val successResponseParser: (InputStream) -> T,
    ) : IgdbRequest()

    internal class GetRequest<out T : Any>(
        val path: String,
        val queryParameters: Map<String, String> = mapOf(),
        val successResponseParser: (InputStream) -> T,
    ) : IgdbRequest()

    internal class DeleteRequest<out T : Any>(
        val path: String,
        val queryParameters: Map<String, String> = mapOf(),
        val successResponseParser: (InputStream) -> T,
    ) : IgdbRequest()
}