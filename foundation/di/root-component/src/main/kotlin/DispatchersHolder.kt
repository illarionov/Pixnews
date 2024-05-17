/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.di.root.component

import ru.pixnews.foundation.coroutines.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.MainCoroutineDispatcherProvider

public interface DispatchersHolder {
    public val computationDispatcherProvider: ComputationCoroutineDispatcherProvider
    public val mainDispatcherProvider: MainCoroutineDispatcherProvider
    public val ioDispatcherProvider: IoCoroutineDispatcherProvider
}
