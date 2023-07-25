/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.loadingstatus

import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.pixnews.foundation.coroutines.MainCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.RootCoroutineScope
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.library.coroutines.newChildSupervisorScope
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@Reusable
class DefaultAppLoadingStatus @Inject constructor(
    featureManager: FeatureManager,
    rootCoroutineScope: RootCoroutineScope,
    mainDispatcherProvider: MainCoroutineDispatcherProvider,
) : AppLoadingStatus {
    private val scope: CoroutineScope = rootCoroutineScope.newChildSupervisorScope(mainDispatcherProvider.get())
    private val initJob = scope.launch {
        featureManager.suspendUntilLoaded()
    }

    override val loadingComplete: Boolean
        get() = initJob.isCompleted

    override suspend fun awaitLoadingComplete() = initJob.join()
}
