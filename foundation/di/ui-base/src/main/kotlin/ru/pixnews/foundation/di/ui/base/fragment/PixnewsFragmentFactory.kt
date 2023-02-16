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
package ru.pixnews.foundation.di.ui.base.fragment

import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import ru.pixnews.foundation.di.ui.base.activity.ActivityScope
import javax.inject.Inject

@Reusable
@ContributesBinding(ActivityScope::class, boundType = FragmentFactory::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PixnewsFragmentFactory @Inject constructor(
    private val subcomponentFactory: FragmentSubcomponent.Factory,
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val component = subcomponentFactory.create()
        val fragmentClass = classLoader.loadClass(className)
        val fragmentProvider = component.fragmentMap[fragmentClass]

        return fragmentProvider?.get() ?: super.instantiate(classLoader, className)
    }
}
