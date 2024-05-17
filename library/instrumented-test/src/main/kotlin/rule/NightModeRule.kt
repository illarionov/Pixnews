/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.instrumented.test.rule

import android.app.UiAutomation
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.test.platform.app.InstrumentationRegistry
import co.touchlab.kermit.Logger
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.pixnews.library.instrumented.test.util.executeShellCommandIs

public class NightModeRule(
    private val mode: NightMode,
    logger: Logger = Logger,
) : TestRule {
    private val logger: Logger = logger.withTag("NightModeRule")

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    private val androidRuntimeVersion = Build.VERSION.SDK_INT

    override fun apply(base: Statement, description: Description): Statement {
        return if (androidRuntimeVersion >= Build.VERSION_CODES.Q) {
            RequestNightModeStatement(mode, base, logger)
        } else {
            base
        }
    }

    private class RequestNightModeStatement(
        private val mode: NightMode,
        private val base: Statement,
        private val logger: Logger = Logger,
    ) : Statement() {
        private val uiAutomation: UiAutomation
            get() = checkNotNull(InstrumentationRegistry.getInstrumentation().uiAutomation)

        private val nightModeExecutor = NightModeCmdExecutor(uiAutomation::executeShellCommandIs, logger)

        override fun evaluate() {
            val initialMode = nightModeExecutor.readNightMode()
            if (initialMode != mode.uiModeCmdParam) {
                nightModeExecutor.setNightMode(mode.uiModeCmdParam)
            }
            try {
                base.evaluate()
            } finally {
                nightModeExecutor.setNightMode(initialMode)
            }
        }
    }

    public enum class NightMode(
        internal val uiModeCmdParam: String,
    ) {
        AUTO("auto"),
        DARK("yes"),
        LIGHT("no"),
        CUSTOM_SCHEDULE("custom_schedule"),
        CUSTOM_BEDTIME("custom_bedtime"),
    }
}
