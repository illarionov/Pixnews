/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import co.touchlab.kermit.Logger
import ru.pixnews.anvil.codegen.activity.inject.ContributesActivity
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.feature.featuretoggles.ui.FeatureToggleListScreen
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.ui.base.activity.BaseActivity
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import javax.inject.Inject

@ContributesActivity
public class FeatureToggleListActivity : BaseActivity() {
    private val viewModel: FeatureToggleListViewModel by viewModels<FeatureToggleListViewModel>(
        null,
        PixnewsRootComponentHolder.appComponent::viewModelFactory,
    )

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
                    onClickResetOverrides = { viewModel.resetOverrides() },
                    onClickResetExperimentOverride = { viewModel.resetExperimentOverride(it) },
                    onSelectExperimentVariant = { experimentKey, variant ->
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
