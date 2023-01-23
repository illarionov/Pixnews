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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import ru.pixnews.app.PixnewsAppComponentHolder
import ru.pixnews.databinding.ActivityMainBinding
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.FeatureToggle
import ru.pixnews.foundation.ui.experiments.DarkModeExperiment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    internal lateinit var appConfig: AppConfig

    @Inject
    internal lateinit var logger: Logger

    @Inject
    internal lateinit var darkModeToggle: FeatureToggle<DarkModeExperiment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PixnewsAppComponentHolder.getOrCreateAppComponent(applicationContext)
            .mainActivityComponentFactory()
            .create(this)
            .inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.contentMain.textView.text = "Build timestamp: ${appConfig.timestamp}"

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                val variant: ExperimentVariant = darkModeToggle.getVariant<ExperimentVariant>()
                logger.w { "Dark mode variant: $variant" }
            }
        }
    }
}
