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
