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
package ru.pixnews.library.functional

import arrow.core.left
import arrow.core.right
import ru.pixnews.library.functional.RequestStatus.Complete
import ru.pixnews.library.functional.RequestStatus.Loading

public sealed interface RequestStatus<out E, out T> {
    public object Loading : RequestStatus<Nothing, Nothing> {
        override fun toString(): String {
            return "Loading"
        }
    }

    public data class Complete<E, T>(val result: Result<E, T>) : RequestStatus<E, T>

    public companion object {
        public fun loading(): Loading = Loading
        public fun <T> completeSuccess(result: T): Complete<Nothing, T> = Complete(result.right())
        public fun <E> completeFailure(error: E): Complete<E, Nothing> = Complete(error.left())
    }
}

public inline fun <E, T, EE, RR> RequestStatus<E, T>.mapComplete(
    crossinline mapper: (Result<E, T>) -> Result<EE, RR>,
): RequestStatus<EE, RR> {
    return when (this) {
        is Loading -> Loading
        is Complete -> Complete(mapper(this.result))
    }
}
