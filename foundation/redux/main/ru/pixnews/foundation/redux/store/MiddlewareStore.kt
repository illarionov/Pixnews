/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.redux.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow.SUSPEND
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pixnews.foundation.redux.Action
import ru.pixnews.foundation.redux.Dispatch
import ru.pixnews.foundation.redux.Middleware
import ru.pixnews.foundation.redux.MiddlewareApi
import ru.pixnews.foundation.redux.Reducer
import ru.pixnews.foundation.redux.State
import ru.pixnews.foundation.redux.Store

internal open class MiddlewareStore<S : State>(
    initialState: S,
    reducer: Reducer<S>,
    middlewares: List<Middleware<S>> = emptyList(),
    scope: CoroutineScope = MainScope(),
) : Store<S> {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    private val actions: MutableSharedFlow<Action> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 0,
        onBufferOverflow = SUSPEND,
    )
    override val state: StateFlow<S> = _state.asStateFlow()

    init {
        suspend fun reduceAndEmit(action: Action) {
            val newState = reducer(state.value, action)
            _state.emit(newState)
        }
        val middlewareApi = object : MiddlewareApi<S> {
            override val state: S get() = _state.value
            override suspend fun dispatch(action: Action) = this@MiddlewareStore.dispatch(action)
        }
        var dispatch: Dispatch = ::reduceAndEmit
        for (middleware in middlewares) {
            dispatch = middleware(middlewareApi)(dispatch)
        }

        actions.onEach(dispatch).launchIn(scope)
    }

    override suspend fun dispatch(action: Action) = actions.emit(action)
}
