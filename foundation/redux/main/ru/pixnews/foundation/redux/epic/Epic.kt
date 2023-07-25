/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
