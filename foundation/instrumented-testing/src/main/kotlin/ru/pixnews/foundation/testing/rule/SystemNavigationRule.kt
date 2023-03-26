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
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import co.touchlab.kermit.Logger
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.pixnews.foundation.testing.util.executeShellCommandIs
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

public class SystemNavigationRule(
    private val mode: NavigationMode,
    logger: Logger = Logger,
) : TestRule {
    private val logger: Logger = logger.withTag("SystemNavigationRule")

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    private val androidRuntimeVersion = Build.VERSION.SDK_INT

    override fun apply(base: Statement, description: Description): Statement {
        return if (androidRuntimeVersion >= Build.VERSION_CODES.Q) {
            RequestSystemNavigationStatement(mode, base, logger)
        } else {
            base
        }
    }

    public enum class NavigationMode {
        GESTURAL,
        THREE_BUTTON,
        ;
    }

    private class RequestSystemNavigationStatement(
        private val requiredMode: NavigationMode,
        private val base: Statement,
        private val logger: Logger,
    ) : Statement() {
        val uiAutomation: UiAutomation
            get() = checkNotNull(InstrumentationRegistry.getInstrumentation().uiAutomation)

        private val overlayModeExecutor = OverlayModeCommandExecutor(uiAutomation::executeShellCommandIs)

        override fun evaluate() {
            val initialMode = overlayModeExecutor.getGesturalMode()
            val needToChangeMode: Boolean = initialMode != requiredMode
            logger.i { "Current system navigation mode: $initialMode, requested mode: $requiredMode" }
            if (needToChangeMode) {
                overlayModeExecutor.setGesturalMode(requiredMode)
                waitUntilNavigationModeIsApplied()
            }
            try {
                base.evaluate()
            } finally {
                if (needToChangeMode) {
                    @Suppress("TooGenericExceptionCaught")
                    try {
                        logger.i { "Restore initial navigation mode: $initialMode" }
                        overlayModeExecutor.setGesturalMode(initialMode)
                        waitUntilNavigationModeIsApplied()
                    } catch (throwable: Throwable) {
                        logger.e(throwable) { "setGesturalMode() failed" }
                    }
                }
            }
        }

        @OptIn(ExperimentalTime::class)
        private fun waitUntilNavigationModeIsApplied() {
            val time = measureTime {
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).waitForWindowUpdate(
                    null,
                    WAIT_NAVIGATION_APPLIED_TIMEOUT,
                )
            }
            logger.i {
                "Waiting until the new navigation mode is applied completed completed in ${time.inWholeMilliseconds}ms"
            }
        }

        private companion object {
            private const val WAIT_NAVIGATION_APPLIED_TIMEOUT = 5000L
        }
    }
}
