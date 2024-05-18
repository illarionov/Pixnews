/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

    @Suppress("UNUSED_PARAMETER", "NO_CORRESPONDING_PROPERTY")
    override var level: Int
        get() = when (logger.config.minSeverity) {
            Verbose -> Log.VERBOSE
            Debug -> Log.DEBUG
            Info -> Log.INFO
            Warn -> Log.WARN
            Error -> Log.ERROR
            Assert -> Log.ASSERT
        }
        set(value) {
            /* no-op */
        }

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
