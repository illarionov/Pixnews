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

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import ru.pixnews.foundation.redux.Action

public typealias Epic<S> = (actions: Flow<Action>, states: Flow<S>) -> Flow<Action>

public fun <S> Iterable<Epic<S>>.merge(): Epic<S> = { actions, states ->
    this
        .map { epic -> epic(actions, states) }
        .merge()
}

public fun <S> combineEpics(vararg epics: Epic<S>): Epic<S> = epics.asIterable().merge()
