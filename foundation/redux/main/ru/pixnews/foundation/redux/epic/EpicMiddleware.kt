/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
        return EpicDispatchDecorator(api, rootEpicSubject.distinctUntilChanged(), scope)
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

    private class EpicDispatchDecorator<S : State>(
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
