/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.inject

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import ru.pixnews.PixnewsApplication
import ru.pixnews.di.root.component.PixnewsAppComponent
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.inject.experiments.ExperimentsComponent

@MergeComponent(
    scope = AppScope::class,
    dependencies = [ExperimentsComponent::class],
)
@SingleIn(AppScope::class)
interface MainPixnewsAppComponent : PixnewsAppComponent {
    fun inject(application: PixnewsApplication)

    @Component.Factory
    fun interface Factory {
        fun create(
            @BindsInstance @ApplicationContext context: Context,
            experimentsComponent: ExperimentsComponent,
        ): MainPixnewsAppComponent
    }
}
