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
package ru.pixnews.foundation.testing.rule

import android.app.UiAutomation
import android.os.Build
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.AutoCloseInputStream
import android.util.Log
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.BufferedReader
import java.io.InputStreamReader

public class NightModeRule(
    private val mode: NightMode,
) : TestRule {
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    private val androidRuntimeVersion = Build.VERSION.SDK_INT

    private object NightModeCmd {
        @Suppress("FunctionOnlyReturningConstant")
        fun getNightModeCmd(): String = "cmd uimode night"
        fun setNightModeCmd(mode: String) = "cmd uimode night $mode"

        fun parseNightModeFromSetModeResult(cmdOutput: String): String =
            cmdOutput.trim().substringAfter("Night mode: ")
    }

    override fun apply(base: Statement, description: Description): Statement {
        return if (androidRuntimeVersion >= Build.VERSION_CODES.Q) {
            RequestNightModeStatement(mode, base)
        } else {
            base
        }
    }

    private class RequestNightModeStatement(
        private val mode: NightMode,
        private val base: Statement,
    ) : Statement() {
        private val uiAutomation: UiAutomation
            get() = checkNotNull(InstrumentationRegistry.getInstrumentation().uiAutomation)

        override fun evaluate() {
            val initialMode = readNightMode()
            if (initialMode != mode.uiModeCmdParam) {
                setNightMode(mode.uiModeCmdParam)
            }
            try {
                base.evaluate()
            } finally {
                setNightMode(initialMode)
            }
        }

        private fun readNightMode(): String {
            val cmdOutput = uiAutomation.executeShellCommand(NightModeCmd.getNightModeCmd())
                .readText()
                .trim()

            return NightModeCmd.parseNightModeFromSetModeResult(cmdOutput).also {
                Log.v(TAG, "Current night mode: \"$it\"")
            }
        }

        private fun setNightMode(mode: String) {
            Log.v(TAG, "Set night mode \"$mode\"")
            uiAutomation.executeShellCommand(NightModeCmd.setNightModeCmd(mode)).readText()
        }

        private fun ParcelFileDescriptor.readText(): String {
            val reader = BufferedReader(InputStreamReader(AutoCloseInputStream(this)))
            return reader.use(BufferedReader::readText)
        }

        private companion object {
            private const val TAG = "RequestNightModeStatement"
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
        ;
    }
}
