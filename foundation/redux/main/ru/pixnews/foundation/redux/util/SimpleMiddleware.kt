/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.redux.util

import ru.pixnews.foundation.redux.Action
import ru.pixnews.foundation.redux.Dispatch
import ru.pixnews.foundation.redux.Middleware
import ru.pixnews.foundation.redux.MiddlewareApi
import ru.pixnews.foundation.redux.State

public abstract class SimpleMiddleware<S : State> : Middleware<S> {
    @Suppress("Wrapping")
    override fun invoke(api: MiddlewareApi<S>): (next: Dispatch) -> Dispatch = { next ->
        { action ->
            interfere(api, next, action)
        }
    }

    public abstract suspend fun interfere(api: MiddlewareApi<S>, next: Dispatch, action: Action)
}
