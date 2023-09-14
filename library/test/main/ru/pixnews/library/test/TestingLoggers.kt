/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.test

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Severity.Verbose
import co.touchlab.kermit.SystemWriter

public object TestingLoggers {
    public val consoleLogger: Logger = Logger(
        config = object : LoggerConfig {
            override val logWriterList: List<LogWriter>
                get() = listOf(SystemWriter())
            override val minSeverity: Severity = Verbose
        },
    )
}
