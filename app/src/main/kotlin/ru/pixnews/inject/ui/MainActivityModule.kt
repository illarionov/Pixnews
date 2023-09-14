/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.ui

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.di.ui.base.activity.ActivityScope
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.foundation.featuretoggles.getFeatureToggle
import ru.pixnews.foundation.ui.experiments.DarkModeExperiment

@ContributesTo(ActivityScope::class)
@Module
object MainActivityModule {
    @Provides
    fun providesDarkModeExperiment(
        featureManager: FeatureManager,
    ): FeatureToggle<DarkModeExperiment> {
        return featureManager.getFeatureToggle(DarkModeExperiment)
    }
}
