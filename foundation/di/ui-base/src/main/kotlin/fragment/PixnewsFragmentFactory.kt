/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.fragment

import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import ru.pixnews.anvil.ksp.codegen.activity.inject.ActivityScope
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
