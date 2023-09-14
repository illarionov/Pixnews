/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.initializer

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.RootCoroutineScope
import ru.pixnews.foundation.di.base.DaggerSet
import ru.pixnews.foundation.initializers.AppInitializer
import ru.pixnews.foundation.initializers.AsyncInitializer
import ru.pixnews.foundation.initializers.Initializer
import ru.pixnews.foundation.initializers.inject.AppInitializersScope

@ContributesTo(AppInitializersScope::class)
@Module
abstract class PixnewsInitializersModule {
    @Multibinds
    abstract fun appInitializers(): DaggerSet<Initializer>

    @Multibinds
    abstract fun appAsyncInitializers(): DaggerSet<AsyncInitializer>

    companion object {
        @Provides
        fun providesAppInitializer(
            initializers: DaggerSet<Initializer>,
            asyncInitializers: DaggerSet<AsyncInitializer>,
            rootCoroutineScope: RootCoroutineScope,
            dispatcher: IoCoroutineDispatcherProvider,
        ): AppInitializer {
            return AppInitializer(initializers, asyncInitializers, rootCoroutineScope, dispatcher.get())
        }
    }
}
