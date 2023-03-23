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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.touchlab.kermit.Logger
import ru.pixnews.features.root.PixnewsRootContent
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.ui.base.activity.BaseActivity
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.foundation.ui.experiments.DarkModeExperiment
import ru.pixnews.loadingstatus.AppLoadingStatus
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    internal lateinit var appConfig: AppConfig

    @Inject
    internal lateinit var logger: Logger

    @Inject
    internal lateinit var darkModeToggle: FeatureToggle<DarkModeExperiment>

    @Inject
    internal lateinit var appLoadingStatus: AppLoadingStatus

    override fun onPostInjectPreCreate() {
        setupSplashScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixnewsRootContent(
                appConfig = appConfig,
            )
        }
    }

    private fun setupSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !appLoadingStatus.loadingComplete
        }
    }
}
