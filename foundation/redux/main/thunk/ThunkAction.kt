/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.redux.thunk

import ru.pixnews.foundation.redux.Action
import ru.pixnews.foundation.redux.State

public interface ThunkAction : Action {
    public suspend fun <S : State> action(api: ThunkApi<S>)
}
