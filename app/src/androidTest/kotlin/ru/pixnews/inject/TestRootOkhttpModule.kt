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
package ru.pixnews.inject

import android.content.Context
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.instrumented.test.InstrumentationAppContext
import ru.pixnews.foundation.network.inject.qualifier.RootHttpClientInterceptor
import ru.pixnews.test.mockokhttp.MockDataInterceptor
import javax.inject.Provider

@Module
@ContributesTo(AppScope::class)
public object TestRootOkhttpModule {
    @Provides
    @IntoSet
    @RootHttpClientInterceptor
    fun provideMockingInterceptor(
        @InstrumentationAppContext context: Provider<Context>,
        logger: Logger,
    ): Interceptor = MockDataInterceptor(context, logger)
}