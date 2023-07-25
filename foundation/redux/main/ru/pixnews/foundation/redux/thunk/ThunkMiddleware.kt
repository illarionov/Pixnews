/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
