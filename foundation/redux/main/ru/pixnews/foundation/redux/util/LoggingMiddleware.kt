/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.redux.util

import ru.pixnews.foundation.redux.Action
import ru.pixnews.foundation.redux.Dispatch
import ru.pixnews.foundation.redux.MiddlewareApi
import ru.pixnews.foundation.redux.State

public class LoggingMiddleware<S : State>(
    private val beforeStateChanged: (action: Action, oldState: S) -> Unit = { _, _ -> },
    private val afterStateChanged: (action: Action, oldState: S, newState: S) -> Unit = { _, _, _ -> },
) : SimpleMiddleware<S>() {
    override suspend fun interfere(api: MiddlewareApi<S>, next: Dispatch, action: Action) {
        val oldState = api.state
        beforeStateChanged(action, oldState)
        next(action)
        afterStateChanged(action, oldState, api.state)
    }
}
