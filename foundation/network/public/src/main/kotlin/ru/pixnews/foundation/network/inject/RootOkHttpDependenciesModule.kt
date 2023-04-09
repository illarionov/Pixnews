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

import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.Multibinds
import okhttp3.EventListener
import okhttp3.Interceptor
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
import ru.pixnews.foundation.network.KermitOkhttpLogger
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientEventListener
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientInterceptor
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientNetworkInterceptor

@ContributesTo(AppScope::class)
@Module
public abstract class RootOkHttpDependenciesModule {
    @Multibinds
    @RootHttpClientInterceptor
    internal abstract fun provideRootHttpClientInterceptors(): Set<Interceptor>

    @Multibinds
    @RootHttpClientNetworkInterceptor
    internal abstract fun provideRootHttpClientNetworkInterceptors(): Set<Interceptor>

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
        ): DaggerSet<Interceptor> {
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
            return setOf(loggingInterceptor)
        }

        @Provides
        internal fun provideNetworkConfig(appconfig: AppConfig): NetworkConfig = appconfig.networkConfig

        @Provides
        @RootHttpClientEventListener
        @Suppress("FunctionOnlyReturningConstant")
        internal fun providesEventListener(): EventListener? = null
    }
}
