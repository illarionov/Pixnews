/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.app.inject

import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.foundation.coroutines.GlobalExceptionHandler
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.inject.DevelopmentExceptionHandlersModule

@ContributesTo(AppScope::class, replaces = [DevelopmentExceptionHandlersModule::class])
@Module
object ReleaseExceptionHandlerModule {
    @Provides
    @Reusable
    fun provideGlobalExceptionHandler(
        logger: Logger,
    ): GlobalExceptionHandler = GlobalExceptionHandler { _, exception ->
        logger.e("Uncaught coroutine exception", exception)
    }
}
