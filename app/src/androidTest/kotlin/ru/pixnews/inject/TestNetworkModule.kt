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
import ru.pixnews.test.app.mock.NetworkBehavior
import ru.pixnews.test.app.mock.igdb.IgdbConstants
import ru.pixnews.test.app.mock.igdb.IgdbMockWebServer
import ru.pixnews.test.app.mock.interceptor.AllowedHostsOnlyDataInterceptor
import ru.pixnews.test.app.mock.interceptor.NetworkBehaviorDataInterceptor
import ru.pixnews.test.app.mock.interceptor.RewriteHostDataInterceptor
import javax.inject.Provider

@ContributesTo(AppScope::class)
@Module
public object TestNetworkModule {
    @Provides
    @SingleIn(AppScope::class)
    fun providesNetworkBehavior(): NetworkBehavior = NetworkBehavior()

    @Provides
    @IntoSet
    @RootHttpClientInterceptor
    fun provideAllowedHostsOnlyInterceptor(
        networkBehavior: NetworkBehavior,
        logger: Logger,
    ): InterceptorWithPriority = AllowedHostsOnlyDataInterceptor(
        networkBehavior,
        logger,
    )

    @Provides
    @IntoSet
    @RootHttpClientInterceptor
    fun provideRewriteUrlInterceptor(
        networkBehavior: NetworkBehavior,
        mockWebServer: IgdbMockWebServer,
        logger: Logger,
    ): InterceptorWithPriority = RewriteHostDataInterceptor(
        networkBehavior = networkBehavior,
        logger = logger,
        defaultRewrites = mapOf(
            IgdbConstants.IGDB_IMAGES_HOST to mockWebServer::getMockUrl,
        ),
    )

    @Provides
    @IntoSet
    @RootHttpClientInterceptor
    fun providesNetworkBehaviorDataInterceptor(
        networkBehavior: NetworkBehavior,
        logger: Logger,
    ): InterceptorWithPriority = NetworkBehaviorDataInterceptor(networkBehavior, logger)

    @Provides
    @SingleIn(AppScope::class)
    fun provideIgdbMockServer(
        @InstrumentationAppContext context: Provider<Context>,
        logger: Logger,
    ): IgdbMockWebServer {
        return IgdbMockWebServer({ context.get().assets }, logger)
    }
}
