/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.inject

import at.released.igdbclient.IgdbClient
import at.released.igdbclient.auth.twitch.InMemoryTwitchTokenStorage
import at.released.igdbclient.auth.twitch.TwitchTokenPayload
import at.released.igdbclient.okhttp.IgdbOkhttpEngine
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import ru.pixnews.feature.calendar.datasource.igdb.LazyIgdbClient
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.IgdbClientConfig
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.network.OkHttpClientProvider
import javax.inject.Provider
import javax.inject.Qualifier

@Module
@ContributesTo(AppScope::class)
public object IgdbClientModule {
    private const val IGDB_MAX_REQUESTS_PER_HOST = 8

    @SingleIn(AppScope::class)
    @Provides
    @InternalIgdbHttpClient
    internal fun providesIgdbHttpClient(
        okhttpClientProvider: OkHttpClientProvider,
    ): OkHttpClient {
        return okhttpClientProvider.get().newBuilder().apply {
            dispatcher(
                Dispatcher().apply {
                    maxRequestsPerHost = IGDB_MAX_REQUESTS_PER_HOST
                },
            )
        }.build()
    }

    @SingleIn(AppScope::class)
    @Provides
    public fun providesIgdbClient(
        config: IgdbClientConfig,
        ioDispactcher: IoCoroutineDispatcherProvider,
        @InternalIgdbHttpClient rootOkhttpClient: Provider<@JvmSuppressWildcards OkHttpClient>,
    ): IgdbClient {
        val igdbClientFactory = {
            IgdbClient(IgdbOkhttpEngine) {
                baseUrl = config.baseUrl
                httpClient {
                    callFactory = rootOkhttpClient.get()
                }
                headers {
                    if (config.apiKey.isNotEmpty()) {
                        append("x-api-key", config.apiKey)
                    }
                }
                config.twitchAuth?.let { configTwitchAuth ->
                    twitchAuth {
                        clientId = configTwitchAuth.clientId
                        clientSecret = configTwitchAuth.clientSecret
                        configTwitchAuth.token?.let { debugToken ->
                            storage = InMemoryTwitchTokenStorage(TwitchTokenPayload(debugToken))
                        }
                    }
                }
            }
        }
        return LazyIgdbClient(igdbClientFactory, ioDispactcher.get())
    }

    @Reusable
    @Provides
    public fun providesIgdbClientConfig(config: AppConfig): IgdbClientConfig = config.igdbClientConfig

    @Qualifier
    internal annotation class InternalIgdbHttpClient
}
