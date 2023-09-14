/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.ui.base.activity

import android.app.Activity
import dagger.MembersInjector

internal class DefaultActivityInjector(
    private val providers: Map<Class<out Activity>, MembersInjector<out Activity>>,
) : ActivityInjector {
    @Suppress("UNCHECKED_CAST")
    override fun inject(activity: Activity) {
        providers[activity::class.java]?.also {
            (it as MembersInjector<Activity>).injectMembers(activity)
        } ?: error("No member injector for $activity")
    }
}
