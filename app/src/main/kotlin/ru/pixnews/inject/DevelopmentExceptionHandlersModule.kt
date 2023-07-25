/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.foundation.coroutines.GlobalExceptionHandler
import ru.pixnews.foundation.di.base.scopes.AppScope

@Module
@ContributesTo(AppScope::class)
object DevelopmentExceptionHandlersModule {
    @Provides
    @Reusable
    fun provideGlobalExceptionHandler(): GlobalExceptionHandler = GlobalExceptionHandler { _, exception ->
        throw UncaughtCoroutineException("Uncaught coroutine exception", exception)
    }

    internal class UncaughtCoroutineException(
        message: String,
        cause: Throwable,
    ) : RuntimeException(message, cause)
}
