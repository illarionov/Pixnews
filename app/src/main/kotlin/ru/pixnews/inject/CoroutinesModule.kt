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
package ru.pixnews.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import ru.pixnews.coroutines.DefaultRootCoroutineScope
import ru.pixnews.foundation.coroutines.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.GlobalExceptionHandler
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.MainCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.RootCoroutineScope
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn

@ContributesTo(AppScope::class)
@Module
object CoroutinesModule {
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

    @Provides
    @SingleIn(AppScope::class)
    fun provideRootCoroutineScope(
        rootDispatcherProvider: ComputationCoroutineDispatcherProvider,
        exceptionHandler: GlobalExceptionHandler,
    ): RootCoroutineScope = DefaultRootCoroutineScope(
        dispatcher = rootDispatcherProvider.get(),
        exceptionHandler = exceptionHandler,
    )
}
