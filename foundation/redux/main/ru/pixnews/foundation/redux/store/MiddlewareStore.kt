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
