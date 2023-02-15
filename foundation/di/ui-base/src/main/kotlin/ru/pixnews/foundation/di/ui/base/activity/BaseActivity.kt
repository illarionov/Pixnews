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
package ru.pixnews.foundation.di.ui.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import javax.inject.Inject

public abstract class BaseActivity : AppCompatActivity() {
    @Inject
    internal lateinit var fragmentFactory: FragmentFactory

    protected open fun onPostInjectPreCreate(): Unit = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        (PixnewsRootComponentHolder.appComponent as ActivitySubcomponent.ProviderInParentComponent)
            .activitySubcomponentFactory()
            .create(this)
            .activityInjector
            .inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        onPostInjectPreCreate()
        super.onCreate(savedInstanceState)
    }
}
