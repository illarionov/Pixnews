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
package ru.pixnews.foundation.network.di

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.Multibinds
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.network.KermitOkhttpLogger
import ru.pixnews.foundation.network.OkHttpClientProvider
import ru.pixnews.foundation.network.RootOkHttpClientProvider
import ru.pixnews.foundation.network.di.qualifier.RootHttpClientEventListener
import ru.pixnews.foundation.network.di.qualifier.RootHttpClientInterceptor
import ru.pixnews.foundation.network.di.qualifier.RootHttpClientNetworkInterceptor
import ru.pixnews.libraries.android.utils.precondition.checkNotMainThread
import java.io.File
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Qualifier

private const val OKHTTP_CACHE_SUBDIR = "okhttp"

@ContributesTo(AppScope::class)
@Module
@RestrictTo(LIBRARY)
public abstract class OkHttpModule {
    @Multibinds
    @RootHttpClientInterceptor
    internal abstract fun provideRootHttpClientInterceptors(): Set<Interceptor>

    @Multibinds
    @RootHttpClientNetworkInterceptor
    internal abstract fun provideRootHttpClientNetworkInterceptors(): Set<Interceptor>

    @Multibinds
    @RootHttpClientEventListener
    internal abstract fun provideRootHttpClientEventListeners(): Set<EventListener>

    @Qualifier
    internal annotation class AppOkHttpClient

    @Qualifier
    internal annotation class RootOkHttpClient

    @RestrictTo(LIBRARY)
    public companion object {
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
            @RootHttpClientInterceptor interceptors: Set<@JvmSuppressWildcards Interceptor>,
            @RootHttpClientNetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>,
            @RootHttpClientEventListener eventListener: EventListener?,
        ): OkHttpClient {
            checkNotMainThread()
            return OkHttpClient.Builder().apply {
                connectTimeout(networkConfig.connectTimeout.inWholeMilliseconds, MILLISECONDS)
                readTimeout(networkConfig.readTimeout.inWholeMilliseconds, MILLISECONDS)
                writeTimeout(networkConfig.writeTimeout.inWholeMilliseconds, MILLISECONDS)
                connectionPool(
                    ConnectionPool(
                        maxIdleConnections = networkConfig.connectionPoolMaxIdleConnections.toInt(),
                        keepAliveDuration = networkConfig.connectionPoolKeepAliveTimeout.inWholeMilliseconds,
                        timeUnit = MILLISECONDS,
                    ),
                )
                dispatcher(
                    Dispatcher().apply {
                        maxRequestsPerHost = networkConfig.maxConnectionsPerHost.toInt()
                    },
                )
                interceptors().addAll(interceptors)
                networkInterceptors().addAll(networkInterceptors)

                if (eventListener != null) {
                    this.eventListener(eventListener)
                }
            }
                .build()
        }

        @ElementsIntoSet
        @Provides
        @RootHttpClientInterceptor
        internal fun provideOkhttpLoggingInterceptor(
            appConfig: AppConfig,
            logger: Logger,
        ): Set<@JvmSuppressWildcards Interceptor> {
            if (!appConfig.isDebug) {
                return emptySet()
            }
            val loggingInterceptor = HttpLoggingInterceptor(KermitOkhttpLogger(logger)).apply {
                level = Level.BASIC
            }
            return setOf(loggingInterceptor)
        }

        @Reusable
        @Provides
        internal fun provideAppOkHttpProvider(
            @AppOkHttpClient appClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
        ): OkHttpClientProvider {
            return object : OkHttpClientProvider {
                override fun get(): OkHttpClient = appClient.get()
            }
        }

        @AppOkHttpClient
        @Provides
        @SingleIn(AppScope::class)
        internal fun provideAppHttpClient(
            rootOkHttpClientProvider: RootOkHttpClientProvider,
            cache: Cache,
        ): OkHttpClient {
            checkNotMainThread()
            return rootOkHttpClientProvider.get().newBuilder()
                .cache(cache)
                .build()
        }

        @Provides
        internal fun provideNetworkConfig(appconfig: AppConfig): NetworkConfig = appconfig.networkConfig

        @Provides
        @RootHttpClientEventListener
        @Suppress("FunctionOnlyReturningConstant")
        internal fun providesEventListener(): EventListener? = null

        @Provides
        @SingleIn(AppScope::class)
        internal fun provideCache(
            networkConfig: NetworkConfig,
            @ApplicationContext context: Context,
        ): Cache {
            checkNotMainThread()
            @Suppress("MagicNumber")
            return Cache(
                File(context.cacheDir, OKHTTP_CACHE_SUBDIR),
                networkConfig.cacheSizeMbytes.toLong() * 1_000_000L,
            )
        }
    }
}
