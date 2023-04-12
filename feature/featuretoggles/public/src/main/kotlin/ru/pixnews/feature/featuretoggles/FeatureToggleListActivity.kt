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
