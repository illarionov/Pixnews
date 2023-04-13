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
package ru.pixnews.foundation.initializers

import androidx.tracing.Trace
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.pixnews.library.coroutines.newChildScope

public class AppInitializer(
    private val initializers: Set<Initializer> = emptySet(),
    private val asyncInitializers: Set<AsyncInitializer> = emptySet(),
    rootCoroutineScope: CoroutineScope = MainScope(),
    asyncDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val scope: CoroutineScope = rootCoroutineScope.newChildScope(asyncDispatcher)

    public fun init() {
        startInitAsyncInitializers()
        intSync()
    }

    private fun startInitAsyncInitializers() {
        scope.launch {
            Trace.beginAsyncSection(ASYNC_INIT_SECTION_NAME, 0)
            try {
                asyncInitializers.forEach {
                    runTracing({ it.javaClass.simpleName }) {
                        it.init()
                    }
                }
            } finally {
                Trace.endAsyncSection(ASYNC_INIT_SECTION_NAME, 0)
            }
        }
    }

    private fun intSync() {
        Trace.beginSection(INIT_SECTION_NAME)
        try {
            initializers.forEach {
                runTracing({ it.javaClass.simpleName }) {
                    it.init()
                }
            }
        } finally {
            Trace.endSection()
        }
    }

    private inline fun runTracing(lazyName: () -> String, initFunction: () -> Unit) {
        if (Trace.isEnabled()) {
            Trace.beginSection(lazyName())
        }
        try {
            initFunction.invoke()
        } finally {
            if (Trace.isEnabled()) {
                Trace.endSection()
            }
        }
    }

    public companion object {
        private const val INIT_SECTION_NAME: String = "AppInitializer init"
        private const val ASYNC_INIT_SECTION_NAME = "AppInitializer async init"
    }
}
