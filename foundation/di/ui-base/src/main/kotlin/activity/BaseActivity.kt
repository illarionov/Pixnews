/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.activity

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import javax.inject.Inject

public abstract class BaseActivity : FragmentActivity() {
    @Inject
    internal lateinit var fragmentFactory: FragmentFactory

    protected open fun onPostInjectPreCreate(): Unit = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        (PixnewsRootComponentHolder.appComponent as ActivitySubcomponent.ActivitySubcomponentFactoryHolder)
            .getActivitySubcomponentFactory()
            .create(this)
            .activityInjector
            .inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        onPostInjectPreCreate()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
