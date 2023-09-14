/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
