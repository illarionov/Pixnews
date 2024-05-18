/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews

import android.app.Application
import androidx.work.Configuration
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.inject.MainPixnewsAppComponent
import javax.inject.Inject

class PixnewsApplication : Application(), Configuration.Provider {
    override val workManagerConfiguration
        get() = localWorkManagerConfiguration

    @field:Inject
    lateinit var localWorkManagerConfiguration: Configuration

    override fun onCreate() {
        super.onCreate()
        (PixnewsRootComponentHolder.appComponent as MainPixnewsAppComponent).inject(this)
    }
}
