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
package ru.pixnews.library.igdb.internal.multiquery

import ru.pixnews.library.igdb.apicalypse.ApicalypseMultiQuery
import ru.pixnews.library.igdb.internal.multiquery.IgdbEndpointParser.getProtobufParserForEndpoint
import ru.pixnews.library.igdb.model.MultiQueryResult
import ru.pixnews.library.igdb.model.MultiQueryResultArray
import ru.pixnews.library.igdb.multiquery.UnpackedMultiQueryResult
import java.io.InputStream

internal class MultiQueryArrayParser(
    private val multiQueryRequest: ApicalypseMultiQuery,
    private val resultArrayParser: (InputStream) -> MultiQueryResultArray = MultiQueryResultArray.ADAPTER::decode,
) : (InputStream) -> List<UnpackedMultiQueryResult<*>> {
    override fun invoke(inputStream: InputStream): List<UnpackedMultiQueryResult<*>> {
        val multiQueryResultArray = resultArrayParser(inputStream)
        return multiQueryResultArray.result.mapIndexed { subqueryIndex, subqueryResult: MultiQueryResult ->
            val endpoint = multiQueryRequest.subqueries[subqueryIndex].endpoint
            UnpackedMultiQueryResult(
                name = subqueryResult.name,
                count = subqueryResult.count,
                results = subqueryResult.results.let { results ->
                    if (results.isNotEmpty()) {
                        val parser: (InputStream) -> Any = getProtobufParserForEndpoint(endpoint)
                        subqueryResult.results.map { payload -> parser(payload.toByteArray().inputStream()) }
                    } else {
                        null
                    }
                },
            )
        }
    }
}
