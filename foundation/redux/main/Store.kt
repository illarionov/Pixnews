/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("TOP_LEVEL_ORDER")

package ru.pixnews.foundation.redux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import ru.pixnews.foundation.redux.store.MiddlewareStore

public typealias Reducer<S> = S.(action: Action) -> S

public interface Store<out S : State> {
    public val state: StateFlow<S>
    public suspend fun dispatch(action: Action)
}

public val <S : State> Store<S>.currentState: S
    get() = state.value

@Suppress("FunctionName")
public fun <S : State> Store(
    initialState: S,
    reducer: Reducer<S>,
    middlewares: List<Middleware<S>> = emptyList(),
    scope: CoroutineScope = MainScope(),
): Store<S> = MiddlewareStore(initialState, reducer, middlewares, scope)
