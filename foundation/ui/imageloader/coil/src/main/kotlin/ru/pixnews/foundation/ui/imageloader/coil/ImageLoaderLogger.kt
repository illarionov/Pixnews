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
package ru.pixnews.foundation.ui.imageloader.coil

import android.util.Log
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity.Assert
import co.touchlab.kermit.Severity.Debug
import co.touchlab.kermit.Severity.Error
import co.touchlab.kermit.Severity.Info
import co.touchlab.kermit.Severity.Verbose
import co.touchlab.kermit.Severity.Warn

private const val LOG_TAG = "ImageLoader"

internal class ImageLoaderLogger(kermitLogger: Logger) : coil.util.Logger {
    private val logger = kermitLogger.withTag(LOG_TAG)

    override var level: Int
        get() = when (logger.config.minSeverity) {
            Verbose -> Log.VERBOSE
            Debug -> Log.DEBUG
            Info -> Log.INFO
            Warn -> Log.WARN
            Error -> Log.ERROR
            Assert -> Log.ASSERT
        }
        set(value) {}

    override fun log(tag: String, priority: Int, message: String?, throwable: Throwable?) {
        val severity = when (priority) {
            Log.VERBOSE -> Verbose
            Log.DEBUG -> Debug
            Log.INFO -> Info
            Log.WARN -> Warn
            Log.ERROR -> Error
            Log.ASSERT -> Assert
            else -> Assert
        }
        logger.log(severity, tag, throwable, message ?: "")
    }
}
