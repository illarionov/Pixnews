/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.rule

import co.touchlab.kermit.Logger
import java.io.InputStream

internal class NightModeCmdExecutor(
    private val shellCommandExecutor: (String) -> InputStream,
    private val logger: Logger = Logger,
) {
    fun readNightMode(): String {
        val cmdOutput = shellCommandExecutor.invoke("cmd uimode night")
            .bufferedReader()
            .readLines()
            .first()

        return parseNightModeFromSetModeResult(cmdOutput).also {
            logger.v { "Current night mode: \"$it\"" }
        }
    }

    fun setNightMode(mode: String) {
        logger.v { "Set night mode \"$mode\"" }
        shellCommandExecutor.invoke("cmd uimode night $mode")
            .use { it.skip(Long.MAX_VALUE) }
    }

    private fun parseNightModeFromSetModeResult(cmdOutput: String): String =
        cmdOutput.trim().substringAfter("Night mode: ")
}
