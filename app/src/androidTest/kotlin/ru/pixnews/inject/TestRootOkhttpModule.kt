/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import android.content.Context
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.instrumented.test.di.InstrumentationAppContext
import ru.pixnews.foundation.network.InterceptorWithPriority
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientInterceptor
import ru.pixnews.test.app.mock.mockokhttp.MockDataInterceptor
import ru.pixnews.test.app.mock.mockokhttp.NetworkBehavior
import ru.pixnews.test.app.mock.mockokhttp.NetworkBehaviorDataInterceptor
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
    ): InterceptorWithPriority = MockDataInterceptor(context, logger)

    @Provides
    @SingleIn(AppScope::class)
    fun providesNetworkBehavior(): NetworkBehavior = NetworkBehavior()

    @Provides
    @IntoSet
    @RootHttpClientInterceptor
    fun providesNetworkBehaviorDataInterceptor(
        networkBehavior: NetworkBehavior,
        logger: Logger,
    ): InterceptorWithPriority = NetworkBehaviorDataInterceptor(networkBehavior, logger)
}
