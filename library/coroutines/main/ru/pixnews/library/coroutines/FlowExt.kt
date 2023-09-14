/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.pixnews.library.functional.RequestStatus
import ru.pixnews.library.functional.RequestStatus.Companion

public fun <T> Flow<T>.asRequestStatus(): Flow<RequestStatus<Throwable, T>> {
    return this
        .map<T, RequestStatus<Throwable, T>>(Companion::completeSuccess)
        .onStart { emit(RequestStatus.Loading) }
        .catch { emit(RequestStatus.completeFailure(it)) }
}
