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
package ru.pixnews.library.igdb.internal.twitch

import org.json.JSONObject
import org.json.JSONTokener
import ru.pixnews.library.igdb.auth.model.TwitchToken
import java.io.InputStream

/**
 * Implementation of a parser for JSON responses received from the Twitch server during the Client Credentials
 * Grant Flow.
 * Based on the [org.json.JSONTokener]
 */
internal object TwitchTokenParser : (InputStream) -> TwitchToken {
    override fun invoke(inputStream: InputStream): TwitchToken {
        val response = inputStream.reader().readText()
        val jsonObject = JSONTokener(response).nextValue() as? JSONObject ?: error("Malformed JSON")
        return TwitchToken(
            access_token = jsonObject.getString("access_token"),
            expires_in = jsonObject.optLong("expires_in"),
            token_type = jsonObject.optString("token_type"),
        )
    }
}