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

import com.squareup.anvil.annotations.MergeComponent
import dagger.Component
import ru.pixnews.di.root.component.PixnewsAppComponent
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.initializers.qualifiers.AppInitializersScope

/**
 * AppInitializersScope component used to collect all initializers into multibining set
 */
@SingleIn(AppInitializersScope::class)
@MergeComponent(
    scope = AppInitializersScope::class,
    dependencies = [PixnewsAppComponent::class],
)
interface MainPixnewsAppInitializerComponent : PixnewsAppInitializerComponent {
    @Component.Factory
    fun interface Factory {
        fun create(appComponent: PixnewsAppComponent): MainPixnewsAppInitializerComponent
    }
}