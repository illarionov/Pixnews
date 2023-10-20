/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.initializer

import com.squareup.anvil.annotations.MergeComponent
import com.squareup.anvil.annotations.optional.SingleIn
import ru.pixnews.di.root.component.PixnewsAppComponent
import ru.pixnews.foundation.initializers.inject.AppInitializersScope
import ru.pixnews.inject.MockWebServerHolder

@SingleIn(AppInitializersScope::class)
@MergeComponent(
    scope = AppInitializersScope::class,
    dependencies = [PixnewsAppComponent::class, MockWebServerHolder::class],
)
interface TestPixnewsAppInitializerComponent : PixnewsAppInitializerComponent {
    @dagger.Component.Factory
    fun interface Factory {
        fun create(
            appComponent: PixnewsAppComponent,
            mockWebServerHolder: MockWebServerHolder,
        ): TestPixnewsAppInitializerComponent
    }
}
