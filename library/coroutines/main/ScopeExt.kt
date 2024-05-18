/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

public fun CoroutineScope.newChildScope(
    childCoroutineContext: CoroutineContext = EmptyCoroutineContext,
): CoroutineScope = this + Job(this.coroutineContext[Job]) + childCoroutineContext

public fun CoroutineScope.newChildSupervisorScope(
    childCoroutineContext: CoroutineContext = EmptyCoroutineContext,
): CoroutineScope = this + SupervisorJob(this.coroutineContext[Job]) + childCoroutineContext
