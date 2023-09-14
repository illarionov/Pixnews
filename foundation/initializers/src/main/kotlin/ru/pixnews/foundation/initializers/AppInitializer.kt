/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
