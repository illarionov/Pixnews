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
package ru.pixnews.libraries.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.pixnews.libraries.functional.NetworkRequestStatus
import ru.pixnews.libraries.functional.NetworkRequestStatus.Companion

public fun <T> Flow<T>.asNetworkRequestStatus(): Flow<NetworkRequestStatus<Throwable, T>> {
    return this
        .map<T, NetworkRequestStatus<Throwable, T>>(Companion::success)
        .onStart { emit(NetworkRequestStatus.Loading) }
        .catch { emit(NetworkRequestStatus.failure(it)) }
}
