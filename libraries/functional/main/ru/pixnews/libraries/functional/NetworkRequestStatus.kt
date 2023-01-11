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
package ru.pixnews.libraries.functional

public sealed interface NetworkRequestStatus<out T> {
    public object Loading : NetworkRequestStatus<Nothing> {
        override fun toString(): String = "Loading"
    }

    /**
     * @property result
     */
    public data class Complete<T>(val result: Result<T>) : NetworkRequestStatus<T>

    public companion object {
        public fun <T> success(result: T): Complete<T> = Complete(Result.success(result))
        public fun <T> failure(throwable: Throwable): Complete<T> = Complete(Result.failure(throwable))
    }
}
