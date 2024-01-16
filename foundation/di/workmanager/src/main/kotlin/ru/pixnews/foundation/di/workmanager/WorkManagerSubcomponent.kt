/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.workmanager

import androidx.annotation.RestrictTo
import androidx.work.CoroutineWorker
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.optional.SingleIn
import ru.pixnews.anvil.codegen.workmanager.inject.wiring.CoroutineWorkerFactory
import ru.pixnews.anvil.codegen.workmanager.inject.wiring.WorkManagerScope
import ru.pixnews.foundation.di.base.DaggerMap
import ru.pixnews.foundation.di.base.scopes.AppScope

@SingleIn(WorkManagerScope::class)
@ContributesSubcomponent(scope = WorkManagerScope::class, parentScope = AppScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface WorkManagerSubcomponent {
    public val workerFactories: DaggerMap<Class<out CoroutineWorker>, CoroutineWorkerFactory>

    @ContributesSubcomponent.Factory
    public fun interface Factory {
        public fun create(): WorkManagerSubcomponent
    }
}
