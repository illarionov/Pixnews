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

import ru.pixnews.library.igdb.IgdbClient
import ru.pixnews.library.igdb.IgdbEndpoint
import ru.pixnews.library.igdb.IgdbResult
import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse

@Suppress("TooManyFunctions")
internal class IgdbClientImplementation(
    private val requestExecutor: RequestExecutor,
) : IgdbClient {
    override suspend fun <T : Any> execute(
        endpoint: IgdbEndpoint<T>,
        query: ApicalypseQuery,
    ): IgdbResult<T, IgdbHttpErrorResponse> = requestExecutor.invoke(
        path = endpoint.protobufPath,
        query = query,
        successResponseParser = endpoint.resultParser,
    )
}
