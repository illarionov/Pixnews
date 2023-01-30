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
package ru.pixnews.features.featuretoggles

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import co.touchlab.kermit.Logger
import ru.pixnews.features.featuretoggles.di.FeatureToggleListActivityComponent
import ru.pixnews.features.featuretoggles.ui.FeatureToggleListScreen
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import javax.inject.Inject
import javax.inject.Named

public class FeatureToggleListActivity : ComponentActivity() {
    private val viewModel: FeatureToggleListViewModel by viewModels { viewModelFactory }

    @Inject
    internal lateinit var appConfig: AppConfig

    @Inject
    internal lateinit var logger: Logger

    @Inject
    @Named("FeatureTogglesViewModel")
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureToggleListActivityComponent(this)
            .inject(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
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
        val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        WindowCompat.getInsetsController(window, rootView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }
}
