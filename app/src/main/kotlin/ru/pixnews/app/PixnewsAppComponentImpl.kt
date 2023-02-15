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
package ru.pixnews.app

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import ru.pixnews.di.root.component.PixnewsAppComponent
import ru.pixnews.experiments.ExperimentsComponent
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn

@MergeComponent(
    scope = AppScope::class,
    dependencies = [ExperimentsComponent::class],
)
@SingleIn(AppScope::class)
interface PixnewsAppComponentImpl : PixnewsAppComponent {
    public companion object {
        public operator fun invoke(
            @ApplicationContext context: Context,
            experimentsComponent: ExperimentsComponent = ExperimentsComponent(),
        ): PixnewsAppComponentImpl = DaggerPixnewsAppComponentImpl.factory().create(context, experimentsComponent)
    }

    @Component.Factory
    fun interface Factory {
        fun create(
            @BindsInstance @ApplicationContext context: Context,
            experimentsComponent: ExperimentsComponent,
        ): PixnewsAppComponentImpl
    }
}
