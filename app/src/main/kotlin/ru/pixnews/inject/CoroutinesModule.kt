/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
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
