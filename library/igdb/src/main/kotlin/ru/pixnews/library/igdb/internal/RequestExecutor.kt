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

import ru.pixnews.library.igdb.apicalypse.ApicalypseQuery
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import ru.pixnews.library.igdb.internal.model.IgdbResult
import java.io.InputStream

internal interface RequestExecutor {
    suspend operator fun <T : Any> invoke(
        endpoint: String,
        query: ApicalypseQuery,
        successResponseParser: (InputStream) -> T,
    ): IgdbResult<T, IgdbHttpErrorResponse>
}
