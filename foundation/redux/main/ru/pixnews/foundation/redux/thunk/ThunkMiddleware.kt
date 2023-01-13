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
package ru.pixnews.foundation.redux.thunk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.pixnews.foundation.redux.Action
import ru.pixnews.foundation.redux.Dispatch
import ru.pixnews.foundation.redux.Middleware
import ru.pixnews.foundation.redux.MiddlewareApi
import ru.pixnews.foundation.redux.State

public class ThunkMiddleware<S : State>(
    private val scope: CoroutineScope = MainScope(),
) : Middleware<S> {
    override fun invoke(api: MiddlewareApi<S>): (Dispatch) -> Dispatch {
        val thunkApi = object : ThunkApi<S> {
            override val state: S get() = api.state
            override suspend fun dispatch(action: Action) = api.dispatch(action)
        }
        return ThunkDispatchDecorator(thunkApi, scope)
    }

    private class ThunkDispatchDecorator<S : State>(
        private val api: ThunkApi<S>,
        private val coroutineScope: CoroutineScope,
    ) : (Dispatch) -> Dispatch {
        override fun invoke(next: Dispatch): Dispatch = { action ->
            if (action !is ThunkAction) {
                next(action)
            } else {
                runAction(action)
            }
        }

        private fun runAction(thunkAction: ThunkAction) {
            coroutineScope.launch {
                thunkAction.action(api)
            }
        }
    }
}
