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