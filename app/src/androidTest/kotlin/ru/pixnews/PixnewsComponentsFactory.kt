/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews

import android.content.Context
import ru.pixnews.di.root.component.PixnewsAppComponent
import ru.pixnews.inject.DaggerTestPixnewsAppComponent
import ru.pixnews.inject.MockWebServerHolder
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
        return DaggerTestPixnewsAppInitializerComponent.factory().create(
            appComponent,
            appComponent as MockWebServerHolder,
        )
    }
}
