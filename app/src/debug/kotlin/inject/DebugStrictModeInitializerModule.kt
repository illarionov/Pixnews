/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ru.pixnews.anvil.ksp.codegen.initializer.inject.AppInitializersScope
import ru.pixnews.foundation.initializers.Initializer
import ru.pixnews.initializers.DebugStrictModeInitializer

@ContributesTo(AppInitializersScope::class)
@Module
interface DebugStrictModeInitializerModule {
    @Binds
    @IntoSet
    fun enableStrictMode(initializer: DebugStrictModeInitializer): Initializer
}
