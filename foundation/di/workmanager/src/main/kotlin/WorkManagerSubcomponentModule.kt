/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.workmanager

import androidx.annotation.RestrictTo
import androidx.work.CoroutineWorker
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.multibindings.Multibinds
import ru.pixnews.anvil.codegen.workmanager.inject.WorkManagerScope
import ru.pixnews.anvil.codegen.workmanager.inject.wiring.CoroutineWorkerFactory
import ru.pixnews.foundation.di.base.DaggerMap

@ContributesTo(WorkManagerScope::class)
@Module
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface WorkManagerSubcomponentModule {
    @Multibinds
    public fun workerFactories(): DaggerMap<Class<out CoroutineWorker>, CoroutineWorkerFactory>
}
