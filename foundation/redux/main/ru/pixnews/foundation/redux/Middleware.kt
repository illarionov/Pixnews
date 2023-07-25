/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
@file:Suppress("FILE_NAME_MATCH_CLASS")

package ru.pixnews.foundation.redux

public typealias Dispatch = suspend ((action: Action) -> Unit)

public typealias Middleware<State> = (api: MiddlewareApi<State>) -> (next: Dispatch) -> Dispatch

public interface MiddlewareApi<out S : State> {
    public val state: S
    public suspend fun dispatch(action: Action)
}
