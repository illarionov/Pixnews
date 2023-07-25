/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.ui.base.fragment

import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.multibindings.Multibinds

@ContributesTo(FragmentScope::class)
@Module
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface FragmentModule {
    @Multibinds
    public fun fragmentProviders(): Map<Class<out Fragment>, Fragment>
}
