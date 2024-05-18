/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.network.inject

import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.EventListener
import okhttp3.OkHttpClient
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.network.InterceptorWithPriority
import ru.pixnews.foundation.network.RootOkHttpClientProvider
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientEventListener
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientInterceptor
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientNetworkInterceptor
import ru.pixnews.library.android.utils.precondition.checkNotMainThread
import javax.inject.Qualifier

@ContributesTo(AppScope::class)
@Module
public object RootOkHttpModule {
    @Provides
    @Reusable
    internal fun provideRootOkHttpProvider(
        @RootOkHttpClient rootClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
    ): RootOkHttpClientProvider {
        return object : RootOkHttpClientProvider {
            override fun get(): OkHttpClient = rootClient.get()
        }
    }

    @Provides
    @RootOkHttpClient
    @SingleIn(AppScope::class)
    public fun provideRootOkhttpClient(
        networkConfig: NetworkConfig,
        @RootHttpClientInterceptor interceptors: DaggerSet<InterceptorWithPriority>,
        @RootHttpClientNetworkInterceptor networkInterceptors: DaggerSet<InterceptorWithPriority>,
        @RootHttpClientEventListener eventListener: EventListener?,
    ): OkHttpClient {
        checkNotMainThread()
        return OkHttpClient.Builder().apply {
            applyNetworkConfig(networkConfig)
            interceptors().addAll(interceptors.sortByPriority())
            networkInterceptors().addAll(networkInterceptors.sortByPriority())
            if (eventListener != null) {
                this.eventListener(eventListener)
            }
        }
            .build()
    }

    private fun Set<InterceptorWithPriority>.sortByPriority(): List<InterceptorWithPriority> =
        sortedBy(InterceptorWithPriority::priority)

    @Qualifier
    internal annotation class RootOkHttpClient
}
