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
package ru.pixnews.app

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.dispatchers.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.dispatchers.MainCoroutineDispatcherProvider

@ContributesTo(AppScope::class)
@Module
object DispatchersModule {
    @Provides
    @Reusable
    fun provideMainDispatcherProvider(): MainCoroutineDispatcherProvider =
        MainCoroutineDispatcherProvider(Dispatchers::Main)

    @Provides
    @Reusable
    fun provideIoDispatcherProvider(): IoCoroutineDispatcherProvider = IoCoroutineDispatcherProvider(Dispatchers::IO)

    @Provides
    @Reusable
    fun provideComputationDispatcherProvider(): ComputationCoroutineDispatcherProvider =
        ComputationCoroutineDispatcherProvider(Dispatchers::Default)
}
