/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.activity

import android.app.Activity
import androidx.annotation.RestrictTo
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.BindsInstance
import ru.pixnews.anvil.codegen.activity.inject.wiring.ActivityInjector
import ru.pixnews.anvil.codegen.activity.inject.wiring.ActivityScope
import ru.pixnews.foundation.di.base.scopes.AppScope

@SingleIn(ActivityScope::class)
@ContributesSubcomponent(scope = ActivityScope::class, parentScope = AppScope::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface ActivitySubcomponent {
    public val activityInjector: ActivityInjector

    @ContributesSubcomponent.Factory
    public fun interface Factory {
        public fun create(@BindsInstance activity: Activity): ActivitySubcomponent
    }

    @ContributesTo(AppScope::class)
    public interface ActivitySubcomponentFactoryHolder {
        public fun getActivitySubcomponentFactory(): ActivitySubcomponent.Factory
    }
}
