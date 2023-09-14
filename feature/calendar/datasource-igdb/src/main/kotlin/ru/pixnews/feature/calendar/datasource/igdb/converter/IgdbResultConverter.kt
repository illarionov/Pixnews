/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.igdbclient.IgdbResult
import ru.pixnews.library.functional.completeFailure
import ru.pixnews.library.functional.completeSuccess
import ru.pixnews.library.functional.network.NetworkRequestFailure
import ru.pixnews.library.functional.network.NetworkResult

internal fun <R : Any, E : Any> IgdbResult<R, E>.toNetworkResult(): NetworkResult<R> {
    return when (this) {
        is IgdbResult.Failure -> toNetworkRequestFailure().completeFailure()
        is IgdbResult.Success -> value.completeSuccess()
    }
}

internal fun <E : Any> IgdbResult.Failure<E>.toNetworkRequestFailure(): NetworkRequestFailure<out E> {
    return when (this) {
        is IgdbResult.Failure.ApiFailure -> NetworkRequestFailure.ApiFailure(error)
        is IgdbResult.Failure.HttpFailure -> NetworkRequestFailure.HttpFailure(
            httpCode = httpCode,
            httpMessage = httpMessage,
            response = response,
            rawResponseHeaders = rawResponseHeaders,
            rawResponseBody = rawResponseBody,
        )

        is IgdbResult.Failure.UnknownHttpCodeFailure -> NetworkRequestFailure.UnknownHttpCodeFailure(
            httpCode = httpCode,
            httpMessage = httpMessage,
            rawResponseBody = rawResponseBody,
        )

        is IgdbResult.Failure.NetworkFailure -> NetworkRequestFailure.NetworkFailure(
            error = error,
        )

        is IgdbResult.Failure.UnknownFailure -> NetworkRequestFailure.UnknownFailure(
            error = error,
        )
    }
}
