/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.initializers

import co.touchlab.kermit.Logger
import ru.pixnews.foundation.initializers.Initializer
import ru.pixnews.foundation.initializers.inject.ContributesInitializer

@ContributesInitializer
class GlobalLoggerInitializer(private val localLogger: Logger) : Initializer {
    override fun init() {
        Logger.setLogWriters(localLogger.config.logWriterList)
        Logger.setMinSeverity(localLogger.config.minSeverity)
        Logger.setTag(localLogger.tag)
    }
}
