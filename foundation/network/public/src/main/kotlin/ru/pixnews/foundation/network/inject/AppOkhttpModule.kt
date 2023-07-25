/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.network.inject

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
