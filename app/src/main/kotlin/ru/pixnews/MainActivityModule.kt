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
package ru.pixnews

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.di.scopes.ActivityScope
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.foundation.featuretoggles.getFeatureToggle
import ru.pixnews.foundation.ui.experiments.DarkModeExperiment

@Module
@ContributesTo(ActivityScope::class)
public object MainActivityModule {
    @Provides
    fun providesDarkModeExperiment(
        featureManager: FeatureManager,
    ): FeatureToggle<DarkModeExperiment> {
        return featureManager.getFeatureToggle(DarkModeExperiment)
    }
}
