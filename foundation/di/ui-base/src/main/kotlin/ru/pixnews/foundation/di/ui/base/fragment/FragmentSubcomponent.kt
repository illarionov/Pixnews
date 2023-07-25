/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.di.ui.base.fragment

import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import com.squareup.anvil.annotations.ContributesSubcomponent
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.di.ui.base.activity.ActivityScope
import javax.inject.Provider

@SingleIn(FragmentScope::class)
@ContributesSubcomponent(scope = FragmentScope::class, parentScope = ActivityScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface FragmentSubcomponent {
    public val fragmentMap: Map<Class<out Fragment>, Provider<Fragment>>

    @ContributesSubcomponent.Factory
    public fun interface Factory {
        public fun create(): FragmentSubcomponent
    }
}
