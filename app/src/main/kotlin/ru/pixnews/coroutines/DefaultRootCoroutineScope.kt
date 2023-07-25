/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import ru.pixnews.foundation.coroutines.GlobalExceptionHandler
import ru.pixnews.foundation.coroutines.RootCoroutineScope
import kotlin.coroutines.CoroutineContext

class DefaultRootCoroutineScope(
    parentJob: Job? = null,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    exceptionHandler: GlobalExceptionHandler? = null,
) : RootCoroutineScope {
    override val coroutineContext: CoroutineContext = run {
        var context = SupervisorJob(parentJob) + dispatcher + CoroutineName("Root Coroutine Scope")
        if (exceptionHandler != null) {
            context += CoroutineExceptionHandler(exceptionHandler::handleException)
        }
        context
    }

    override fun toString(): String {
        return "RootCoroutineScope(coroutineContext=$coroutineContext)"
    }
}
