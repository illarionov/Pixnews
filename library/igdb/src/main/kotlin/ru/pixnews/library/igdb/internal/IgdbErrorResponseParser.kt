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

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import java.io.InputStream

/**
 * Implementation of the Igdb server response parser using the [org.json.JSONTokener]
 */
internal object IgdbErrorResponseParser : (InputStream) -> IgdbHttpErrorResponse {
    /**
     * Parse incoming [inputStream] response from the Igdb server into a [IgdbHttpErrorResponse] object.
     *
     * Note: It is the caller's responsibility to close this stream.
     *
     * @return [IgdbHttpErrorResponse] or null if the stream cannot be parsed
     */
    override fun invoke(inputStream: InputStream): IgdbHttpErrorResponse {
        val response = inputStream.reader().readText()
        val tokener = JSONTokener(response).nextValue() as? JSONArray ?: error("Malformed JSON")
        return IgdbHttpErrorResponse(readMessageArray(tokener))
    }

    private fun readMessageArray(array: JSONArray): List<IgdbHttpErrorResponse.Message> {
        return (0 until array.length())
            .map { index ->
                val messageObject = array.get(index) as? JSONObject ?: error("No object at index $index")
                readMessage(messageObject)
            }
    }

    private fun readMessage(jsonObject: JSONObject): IgdbHttpErrorResponse.Message {
        return IgdbHttpErrorResponse.Message(
            status = jsonObject.getInt("status"),
            cause = jsonObject.getStringOrNull("cause"),
            title = jsonObject.getStringOrNull("title"),
        )
    }

    private fun JSONObject.getStringOrNull(key: String): String? = if (isNull(key)) null else getString(key)
}