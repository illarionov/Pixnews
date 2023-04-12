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

import android.app.Activity
import androidx.annotation.RestrictTo
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.BindsInstance
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.foundation.di.base.scopes.SingleIn

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
