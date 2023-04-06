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
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.OkHttpClient
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.network.OkHttpClientProvider
import ru.pixnews.foundation.network.RootOkHttpClientProvider
import ru.pixnews.library.android.utils.precondition.checkNotMainThread
import java.io.File
import javax.inject.Qualifier

private const val OKHTTP_CACHE_SUBDIR = "okhttp"

@ContributesTo(AppScope::class)
@Module
public object AppOkhttpModule {
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

    @Qualifier
    internal annotation class AppOkHttpClient
}
