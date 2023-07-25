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
import ru.pixnews.library.instrumented.test.rule.SystemNavigationRule.NavigationMode.THREE_BUTTON
import ru.pixnews.library.instrumented.test.util.HierarchyDumper
import ru.pixnews.library.instrumented.test.util.executeShellCommandIs
import java.util.concurrent.TimeoutException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
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
                waitUntilNavigationModeIsApplied(requiredMode)
            }
            try {
                base.evaluate()
            } finally {
                if (needToChangeMode) {
                    @Suppress("TooGenericExceptionCaught")
                    try {
                        logger.i { "Restore initial navigation mode: $initialMode" }
                        overlayModeExecutor.setGesturalMode(initialMode)
                        waitUntilNavigationModeIsApplied(initialMode)
                    } catch (throwable: Throwable) {
                        logger.e(throwable) { "setGesturalMode() failed" }
                    }
                }
            }
        }

        @OptIn(ExperimentalTime::class)
        private fun waitUntilNavigationModeIsApplied(
            requiredMode: NavigationMode,
        ) {
            var waitResult: Result<Unit> = Result.success(Unit)
            val time = measureTime {
                waitResult = runCatching {
                    waitForHomeButtonVisibility(
                        homeButtonShouldBeVisible = requiredMode == THREE_BUTTON,
                    )
                }
            }
            waitResult.onSuccess {
                logger.i {
                    "Waiting until the new navigation mode completed in ${time.inWholeMilliseconds}ms"
                }
            }.onFailure { error ->
                logger.e(error) {
                    "Waiting for new navigation mode to be applied timed out"
                }
            }
        }

        private fun waitForHomeButtonVisibility(
            homeButtonShouldBeVisible: Boolean,
            timeout: Duration = WAIT_NAVIGATION_APPLIED_TIMEOUT,
            pollInterval: Duration = WAIT_NAVIGATION_APPLIED_POLL_INTERVAL,
        ) {
            val entTime = System.nanoTime() + timeout.inWholeNanoseconds
            while (System.nanoTime() < entTime) {
                if (isAnyOfNavbarButtonsVisible() == homeButtonShouldBeVisible) {
                    return
                }
                Thread.sleep(pollInterval.inWholeMilliseconds)
            }
            throw TimeoutException("Timeout waiting for new navigation mode")
        }

        private fun isAnyOfNavbarButtonsVisible(): Boolean {
            // XXX need a more efficient way
            val hierarchy = HierarchyDumper.printXmlWindowHierarchyToString()
            return listOf(
                "com.android.systemui:id/home", // also covers the "com.android.systemui:id/home_button" case
                "com.android.systemui:id/back",
                "com.android.systemui:id/recent_apps",
            ).any {
                hierarchy.contains(it)
            }
        }

        private companion object {
            private val WAIT_NAVIGATION_APPLIED_TIMEOUT = 10.seconds
            private val WAIT_NAVIGATION_APPLIED_POLL_INTERVAL = 300.milliseconds
        }
    }
}
