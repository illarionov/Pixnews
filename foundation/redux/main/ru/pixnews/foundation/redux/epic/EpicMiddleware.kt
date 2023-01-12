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
package ru.pixnews.foundation.redux.epic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow.SUSPEND
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pixnews.foundation.redux.Action
import ru.pixnews.foundation.redux.Dispatch
import ru.pixnews.foundation.redux.Middleware
import ru.pixnews.foundation.redux.MiddlewareApi
import ru.pixnews.foundation.redux.State

public class EpicMiddleware<S : State>(
    private val scope: CoroutineScope = MainScope(),
) : Middleware<S> {
    private val rootEpicSubject: MutableSharedFlow<Epic<S>> = MutableSharedFlow(1, 2, SUSPEND)

    override fun invoke(api: MiddlewareApi<S>): (next: Dispatch) -> Dispatch {
        return EpicDispatchMapper(api, rootEpicSubject.distinctUntilChanged(), scope)
    }

    public fun run(rootEpic: Epic<S>) {
        if (!rootEpicSubject.tryEmit(rootEpic)) {
            throw RootEpicFailedException("Can not run root epic")
        }
    }

    public class RootEpicFailedException : RuntimeException {
        public constructor() : super()
        public constructor(message: String?) : super(message)
    }

    private class EpicDispatchMapper<S : State>(
        private val api: MiddlewareApi<S>,
        rootEpicSubject: Flow<Epic<S>>,
        scope: CoroutineScope,
    ) : (Dispatch) -> Dispatch {
        private val _stateFlow = MutableStateFlow(api.state)
        private val stateFlow = _stateFlow.asStateFlow()
        private val _actionsFlow: MutableSharedFlow<Action> = MutableSharedFlow(0, 0, SUSPEND)
        private val actionsFlow = _actionsFlow.asSharedFlow()

        init {
            rootEpicSubject
                .flatMapLatest { epic ->
                    epic(actionsFlow, stateFlow)
                }
                .onEach { action ->
                    api.dispatch(action)
                }
                .launchIn(scope)
        }

        override fun invoke(next: Dispatch): Dispatch = { action ->
            // Downstream middleware gets the action first,
            // which includes their reducers, so state is
            // updated before epics receive the action
            next(action)

            // It's important to update the state$ before we emit
            // the action because otherwise it would be stale
            _stateFlow.emit(api.state)
            _actionsFlow.emit(action)
        }
    }
}
