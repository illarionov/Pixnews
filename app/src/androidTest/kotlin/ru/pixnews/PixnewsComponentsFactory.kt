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
package ru.pixnews

import android.content.Context
import ru.pixnews.di.root.component.PixnewsAppComponent
import ru.pixnews.inject.DaggerTestPixnewsAppComponent
import ru.pixnews.inject.experiments.ExperimentsComponent
import ru.pixnews.inject.initializer.DaggerTestPixnewsAppInitializerComponent
import ru.pixnews.inject.initializer.PixnewsAppInitializerComponent

internal object PixnewsComponentsFactory {
    fun createAppComponent(
        context: Context,
        experimentsComponent: ExperimentsComponent = ExperimentsComponent(),
    ): PixnewsAppComponent {
        return DaggerTestPixnewsAppComponent.factory().create(context, experimentsComponent)
    }

    fun createInitializersComponent(
        appComponent: PixnewsAppComponent,
    ): PixnewsAppInitializerComponent {
        return DaggerTestPixnewsAppInitializerComponent.factory().create(appComponent)
    }
}
