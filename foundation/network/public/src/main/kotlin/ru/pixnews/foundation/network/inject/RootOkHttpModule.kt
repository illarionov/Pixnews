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
package ru.pixnews.foundation.network.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
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
        @RootHttpClientInterceptor interceptors: DaggerSet<Interceptor>,
        @RootHttpClientNetworkInterceptor networkInterceptors: DaggerSet<Interceptor>,
        @RootHttpClientEventListener eventListener: EventListener?,
    ): OkHttpClient {
        checkNotMainThread()
        return OkHttpClient.Builder().apply {
            applyNetworkConfig(networkConfig)
            interceptors().addAll(interceptors.setupOrder())
            networkInterceptors().addAll(networkInterceptors.setupOrder())
            if (eventListener != null) {
                this.eventListener(eventListener)
            }
        }
            .build()
    }

    private fun Set<Interceptor>.setupOrder(): Set<Interceptor> {
        val loggingInterceptor = this.firstOrNull { it is HttpLoggingInterceptor }
        return if (loggingInterceptor != null) {
            setOf(loggingInterceptor) + (this - loggingInterceptor)
        } else {
            this
        }
    }

    @Qualifier
    internal annotation class RootOkHttpClient
}
