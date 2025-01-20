/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.initializer

import com.squareup.anvil.annotations.MergeComponent
import com.squareup.anvil.annotations.optional.SingleIn
import ru.pixnews.anvil.ksp.codegen.initializer.inject.AppInitializersScope
import ru.pixnews.di.root.component.PixnewsAppComponent
import ru.pixnews.inject.FirebaseModule.FirebaseProviderHolder

/**
 * AppInitializersScope component used to collect all initializers into multibinding set
 */
@SingleIn(AppInitializersScope::class)
@MergeComponent(
    scope = AppInitializersScope::class,
    dependencies = [PixnewsAppComponent::class, FirebaseProviderHolder::class],
)
interface MainPixnewsAppInitializerComponent : PixnewsAppInitializerComponent {
    @MergeComponent.Factory
    fun interface Factory {
        fun create(
            appComponent: PixnewsAppComponent,
            firebaseProviderHolder: FirebaseProviderHolder,
        ): MainPixnewsAppInitializerComponent
    }
}
