/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.network.inject

import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.Multibinds
import okhttp3.EventListener
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.HttpLoggingLevel.BASIC
import ru.pixnews.foundation.appconfig.HttpLoggingLevel.BODY
import ru.pixnews.foundation.appconfig.HttpLoggingLevel.HEADERS
import ru.pixnews.foundation.appconfig.HttpLoggingLevel.NONE
import ru.pixnews.foundation.appconfig.NetworkConfig
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.network.InterceptorWithPriority
import ru.pixnews.foundation.network.KermitOkhttpLogger
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientEventListener
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientInterceptor
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientNetworkInterceptor
import ru.pixnews.foundation.network.withPriority

@ContributesTo(AppScope::class)
@Module
public abstract class RootOkHttpDependenciesModule {
    @Multibinds
    @RootHttpClientInterceptor
    internal abstract fun provideRootHttpClientInterceptors(): Set<InterceptorWithPriority>

    @Multibinds
    @RootHttpClientNetworkInterceptor
    internal abstract fun provideRootHttpClientNetworkInterceptors(): Set<InterceptorWithPriority>

    @Multibinds
    @RootHttpClientEventListener
    internal abstract fun provideRootHttpClientEventListeners(): Set<EventListener>

    public companion object {
        @ElementsIntoSet
        @Provides
        @RootHttpClientInterceptor
        internal fun provideOkhttpLoggingInterceptor(
            appConfig: AppConfig,
            logger: Logger,
        ): DaggerSet<InterceptorWithPriority> {
            if (appConfig.networkConfig.httpLoggingLevel == NONE) {
                return emptySet()
            }
            val loggingInterceptor = HttpLoggingInterceptor(KermitOkhttpLogger(logger)).apply {
                level = when (appConfig.networkConfig.httpLoggingLevel) {
                    NONE -> Level.NONE
                    BASIC -> Level.BASIC
                    HEADERS -> Level.HEADERS
                    BODY -> Level.BODY
                }
            }
            @Suppress("MagicNumber")
            return setOf(loggingInterceptor.withPriority(18))
        }

        @Provides
        internal fun provideNetworkConfig(appconfig: AppConfig): NetworkConfig = appconfig.networkConfig

        @Provides
        @RootHttpClientEventListener
        @Suppress("FunctionOnlyReturningConstant")
        internal fun providesEventListener(): EventListener? = null
    }
}
