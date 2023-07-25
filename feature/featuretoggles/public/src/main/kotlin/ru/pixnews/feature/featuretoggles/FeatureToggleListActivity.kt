/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.featuretoggles

import android.os.Bundle
import androidx.activity.compose.setContent
import co.touchlab.kermit.Logger
import ru.pixnews.feature.featuretoggles.ui.FeatureToggleListScreen
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.ui.base.activity.BaseActivity
import ru.pixnews.foundation.di.ui.base.activity.ContributesActivity
import ru.pixnews.foundation.di.ui.base.viewmodel.injectedViewModel
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import javax.inject.Inject

@ContributesActivity
public class FeatureToggleListActivity : BaseActivity() {
    private val viewModel by injectedViewModel<FeatureToggleListViewModel>()

    @Inject
    internal lateinit var appConfig: AppConfig

    @Inject
    internal lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixnewsTheme(
                appConfig = appConfig,
            ) {
                FeatureToggleListScreen(
                    viewStateFlow = viewModel.viewState,
                    onResetOverridesClicked = { viewModel.resetOverrides() },
                    onResetExperimentOverrideClicked = { viewModel.resetExperimentOverride(it) },
                    onExperimentVariantSelected = { experimentKey, variant ->
                        viewModel.onExperimentVariantSelected(
                            experimentKey,
                            variant,
                        )
                    },
                )
            }
        }
    }
}
