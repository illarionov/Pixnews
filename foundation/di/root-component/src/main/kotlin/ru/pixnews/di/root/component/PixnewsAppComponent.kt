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
package ru.pixnews.di.root.component

import co.touchlab.kermit.Logger
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.dispatchers.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.dispatchers.MainCoroutineDispatcherProvider
import ru.pixnews.foundation.featuretoggles.FeatureManager

public interface PixnewsAppComponent {
    public fun getComputationDispatcherProvider(): ComputationCoroutineDispatcherProvider
    public fun getMainDispatcherProvider(): MainCoroutineDispatcherProvider
    public fun getIoDispatcherProvider(): IoCoroutineDispatcherProvider
    public fun getLogger(): Logger

    public fun getFeatureManager(): FeatureManager
    public fun getAppConfig(): AppConfig
}
