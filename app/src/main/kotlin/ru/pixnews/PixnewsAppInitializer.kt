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
import androidx.startup.Initializer
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.foundation.initializers.AppInitializer
import javax.inject.Inject

/**
 * [androidx.startup.Initializer] that sets up root app component and runs all
 * local [ru.pixnews.foundation.initializers.Initializer]'s and [ru.pixnews.foundation.initializers.AsyncInitializer]'s
 * added as multibindings to [MainPixnewsAppInitializerComponent]
 */
class PixnewsAppInitializer : Initializer<Unit> {
    @Inject
    internal lateinit var appInitializer: AppInitializer

    override fun create(context: Context) {
        val appComponent = PixnewsRootComponentHolder.init {
            PixnewsComponentsFactory.createAppComponent(context)
        }
        PixnewsComponentsFactory.createInitializersComponent(appComponent).inject(this)
        appInitializer.init()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}