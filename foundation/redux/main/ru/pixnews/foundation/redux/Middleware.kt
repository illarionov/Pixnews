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
@file:Suppress("FILE_NAME_MATCH_CLASS")

package ru.pixnews.foundation.redux

public typealias Dispatch = suspend ((action: Action) -> Unit)

public typealias Middleware<State> = (api: MiddlewareApi<State>) -> (next: Dispatch) -> Dispatch

public interface MiddlewareApi<out S : State> {
    public val state: S
    public suspend fun dispatch(action: Action)
}
