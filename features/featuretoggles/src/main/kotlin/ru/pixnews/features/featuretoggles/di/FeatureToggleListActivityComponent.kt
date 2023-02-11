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
package ru.pixnews.features.featuretoggles.di

import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.BindsInstance
import ru.pixnews.di.root.PixnewsRootComponentHolder
import ru.pixnews.features.featuretoggles.FeatureToggleListActivity
import ru.pixnews.foundation.di.scopes.ActivityScope
import ru.pixnews.foundation.di.scopes.AppScope
import ru.pixnews.foundation.di.scopes.SingleIn

@SingleIn(ActivityScope::class)
@ContributesSubcomponent(scope = ActivityScope::class, parentScope = AppScope::class)
public interface FeatureToggleListActivityComponent {
    public fun inject(activity: FeatureToggleListActivity)

    @ContributesSubcomponent.Factory
    public fun interface Factory {
        public fun create(@BindsInstance activity: FeatureToggleListActivity): FeatureToggleListActivityComponent
    }

    @ContributesTo(AppScope::class)
    public fun interface FactoryProvider {
        public fun featureToggleActivityFactory(): Factory
    }

    public companion object {
        public operator fun invoke(activity: FeatureToggleListActivity): FeatureToggleListActivityComponent {
            return (PixnewsRootComponentHolder.appComponent as FactoryProvider)
                .featureToggleActivityFactory()
                .create(activity)
        }
    }
}
