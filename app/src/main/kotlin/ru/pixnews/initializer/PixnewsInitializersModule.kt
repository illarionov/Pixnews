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
package ru.pixnews.initializer

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import ru.pixnews.foundation.dispatchers.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.initializers.AppInitializer
import ru.pixnews.foundation.initializers.AsyncInitializer
import ru.pixnews.foundation.initializers.Initializer
import ru.pixnews.foundation.initializers.qualifiers.AppInitializersScope

@ContributesTo(AppInitializersScope::class)
@Module
abstract class PixnewsInitializersModule {
    @Multibinds
    abstract fun appInitializers(): Set<@JvmSuppressWildcards Initializer>

    @Multibinds
    abstract fun appAsyncInitializers(): Set<@JvmSuppressWildcards AsyncInitializer>

    companion object {
        @Provides
        fun providesAppInitializer(
            initializers: Set<@JvmSuppressWildcards Initializer>,
            asyncInitializers: Set<@JvmSuppressWildcards AsyncInitializer>,
            dispatcher: IoCoroutineDispatcherProvider,
        ): AppInitializer {
            return AppInitializer(initializers, asyncInitializers, dispatcher.get())
        }
    }
}