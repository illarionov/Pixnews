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