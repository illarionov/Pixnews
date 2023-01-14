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
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import ru.pixnews.app.AppConfig
import ru.pixnews.app.PixnewsAndroidApplication
import ru.pixnews.databinding.ActivityMainBinding
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity() {
    private val logger: Logger = run {
        val config = object : LoggerConfig {
            override val logWriterList: List<LogWriter> = listOf(
                object : LogWriter() {
                    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) = Unit
                },
            )
            override val minSeverity: Severity = Severity.Error
        }
        Logger(config)
    }
    private val appConfig by lazy(NONE) { AppConfig() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as PixnewsAndroidApplication).appComponent
            .mainActivityComponentFactory()
            .create(this)
            .inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.contentMain.textView.text = "Build timestamp: ${appConfig.timestamp}"

        logger.i { "Test" }
        logger.i { "Test2" }
        logger.i { "Test3" }
    }
}
