/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
