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
package ru.pixnews.feature.featuretoggles.inject

import android.app.Activity
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.MembersInjector
import dagger.Module
import dagger.multibindings.IntoMap
import ru.pixnews.feature.featuretoggles.FeatureToggleListActivity
import ru.pixnews.foundation.di.base.scopes.SingleIn
import ru.pixnews.foundation.di.ui.base.activity.ActivityKey
import ru.pixnews.foundation.di.ui.base.activity.ActivityScope

@Module
@ContributesTo(ActivityScope::class)
public interface FeatureToggleListActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(FeatureToggleListActivity::class)
    @SingleIn(ActivityScope::class)
    public fun binds(target: MembersInjector<FeatureToggleListActivity>): MembersInjector<out Activity>
}