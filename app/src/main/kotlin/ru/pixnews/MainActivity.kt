/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.touchlab.kermit.Logger
import ru.pixnews.feature.root.PixnewsRootContent
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.di.ui.base.activity.BaseActivity
import ru.pixnews.foundation.di.ui.base.activity.ContributesActivity
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.foundation.ui.experiments.DarkModeExperiment
import ru.pixnews.loadingstatus.AppLoadingStatus
import javax.inject.Inject

@ContributesActivity
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
