/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.initializer

import android.os.Build.VERSION
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import android.os.strictmode.DiskReadViolation
import android.os.strictmode.DiskWriteViolation
import android.os.strictmode.InstanceCountViolation
import android.os.strictmode.UntaggedSocketViolation
import android.os.strictmode.Violation
import androidx.activity.ComponentActivity
import androidx.fragment.app.strictmode.FragmentStrictMode
import androidx.fragment.app.strictmode.FragmentStrictMode.Policy
import co.touchlab.kermit.Logger
import ru.pixnews.MainActivity
import ru.pixnews.foundation.initializers.Initializer
import ru.pixnews.foundation.initializers.inject.ContributesInitializer
import ru.pixnews.inject.DebugStrictModeInitializerModule
import java.util.concurrent.Executors
import javax.inject.Inject

@ContributesInitializer(replaces = [DebugStrictModeInitializerModule::class])
class TestStrictModeInitializer @Inject constructor(logger: Logger) : Initializer {
    private val logger = logger.withTag("TestStrictModeInitializer")

    override fun init() {
        logger.v { "Setting up StrictMode" }
        val threadExecutor = Executors.newSingleThreadExecutor()

        StrictMode.setThreadPolicy(
            Builder()
                .detectAll()
                .also { builder ->
                    if (VERSION.SDK_INT >= 28) {
                        builder.penaltyListener(threadExecutor) { violation ->
                            if (violation.shouldSkip()) {
                                logger.e { "Skipping ThreadPolicy violation `$violation: ${violation.message}`" }
                            } else {
                                throw StrictModeViolationException(violation)
                            }
                        }
                    } else {
                        builder.penaltyLog()
                    }
                }
                .build(),
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectAll()
                // Class instance limit occasionally triggered in instrumented tests for unknown reasons
                .setClassInstanceLimit(MainActivity::class.java, 5)
                .setClassInstanceLimit(ComponentActivity::class.java, 5)
                .also { builder ->
                    if (VERSION.SDK_INT >= 28) {
                        builder.penaltyListener(threadExecutor) { violation ->
                            if (violation.shouldSkip()) {
                                logger.e { "Skipping VmPolicy violation `$violation: ${violation.message}`" }
                            } else {
                                throw StrictModeViolationException(violation)
                            }
                        }
                    } else {
                        builder.penaltyLog()
                    }
                }
                .build(),
        )

        FragmentStrictMode.defaultPolicy = Policy.Builder()
            .detectFragmentReuse()
            .detectFragmentTagUsage()
            .detectRetainInstanceUsage()
            .detectSetUserVisibleHint()
            .detectTargetFragmentUsage()
            .detectWrongFragmentContainer()
            .detectWrongNestedHierarchy()
            .penaltyDeath()
            .build()
    }

    private fun Violation.shouldSkip(): Boolean = listOf(
        isInstrumentationDexMakerViolation,
        isProfileSizeOfAppViolation,
        isTypefaceFullFlipFontViolation,
        isUntaggedSocketViolation,
        isInstanceCountViolation,
    ).any { it() }
}

private class StrictModeViolationException(throwable: Throwable) : RuntimeException(throwable)

/**
 * Disk read/write violations on [androidx.test.runner.MonitoringInstrumentation.specifyDexMakerCacheProperty]
 */
private val isInstrumentationDexMakerViolation: Violation.() -> Boolean = {
    (this is DiskReadViolation || this is DiskWriteViolation) && this.stackTrace.any {
        it.fileName == "MonitoringInstrumentation.java" && it.methodName == "specifyDexMakerCacheProperty"
    }
}

private val isTypefaceFullFlipFontViolation: Violation.() -> Boolean = {
    this is DiskReadViolation && this.stackTrace.any {
        it.fileName == "Typeface.java" && (it.methodName == "getFullFlipFont" || it.methodName == "setFlipFonts")
    }
}

private val isProfileSizeOfAppViolation: Violation.() -> Boolean = {
    this is DiskReadViolation && this.stackTrace.any {
        it.fileName == "ActivityThread.java" && it.methodName == "getProfileSizeOfApp"
    }
}

private val isUntaggedSocketViolation: Violation.() -> Boolean = {
    this is UntaggedSocketViolation
}

// Class instance limit occasionally triggered in instrumented tests for unknown reasons
private val isInstanceCountViolation: Violation.() -> Boolean = {
    this is InstanceCountViolation
}
