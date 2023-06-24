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
package ru.pixnews.library.igdb.internal.model

internal sealed interface IgdbResult<out R : Any, out E : Any> {
    /**
     * 2xx response with successfully parsed body
     */
    class Success<R : Any> internal constructor(
        val value: R,
    ) : IgdbResult<R, Nothing>

    sealed class Failure<E : Any> : IgdbResult<Nothing, E> {
        /**
         * Any network error, no HTTP response received
         */
        class NetworkFailure internal constructor(
            val error: Throwable,
        ) : Failure<Nothing>()

        /**
         * 4xx - 5xx HTTP errors
         */
        class HttpFailure<E : Any> internal constructor(
            val httpCode: Int,
            val httpMessage: String,
            val response: E?,
            val rawResponseHeaders: List<Pair<String, String>>?,
            val rawResponseBody: ByteArray?,
        ) : Failure<E>()

        /**
         * Other HTTP errors
         */
        class UnknownHttpCodeFailure internal constructor(
            val httpCode: Int,
            val httpMessage: String,
            val rawResponseBody: ByteArray?,
        ) : Failure<Nothing>()

        /**
         * 2xx HTTP response with unparsable body
         */
        class ApiFailure internal constructor(
            val error: Throwable?,
        ) : Failure<Nothing>()

        /**
         * Other failures
         */
        class UnknownFailure(val error: Throwable?) : Failure<Nothing>()
    }
}
