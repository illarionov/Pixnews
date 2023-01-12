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
package ru.pixnews.foundation.redux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow

public typealias State = Any

public typealias Reducer<S> = S.(action: Action) -> S

public typealias Dispatch = suspend ((action: Action) -> Unit)

public interface Action {
    public val type: String
        get() = toString()
}

public fun interface Middleware<S : State> {
    public fun interfere(store: Store<S>, next: Dispatch): Dispatch
}

public interface Store<S : State> {
    public val state: StateFlow<S>
    public suspend fun dispatch(action: Action)
}

@Suppress("FunctionName")
public fun <S : State> Store(
    initialState: S,
    reducer: Reducer<S>,
    middlewares: List<Middleware<S>> = emptyList(),
    scope: CoroutineScope = MainScope(),
): Store<S> = MiddlewareStore(initialState, reducer, middlewares, scope)
