/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import android.content.Context
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.instrumented.test.di.InstrumentationAppContext
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientInterceptor
import ru.pixnews.test.mockokhttp.MockDataInterceptor
import javax.inject.Provider

@ContributesTo(AppScope::class)
@Module
public object TestRootOkhttpModule {
    @Provides
    @IntoSet
    @RootHttpClientInterceptor
    fun provideMockingInterceptor(
        @InstrumentationAppContext context: Provider<Context>,
        logger: Logger,
    ): Interceptor = MockDataInterceptor(context, logger)
}
