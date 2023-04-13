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
package ru.pixnews.initializer

import android.os.Build.VERSION
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import android.os.strictmode.DiskReadViolation
import android.os.strictmode.DiskWriteViolation
import android.os.strictmode.UntaggedSocketViolation
import android.os.strictmode.Violation
import androidx.fragment.app.strictmode.FragmentStrictMode
import androidx.fragment.app.strictmode.FragmentStrictMode.Policy
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesMultibinding
import ru.pixnews.MainActivity
import ru.pixnews.foundation.initializers.Initializer
import ru.pixnews.foundation.initializers.qualifiers.AppInitializersScope
import ru.pixnews.inject.DebugStrictModeInitializerModule
import java.util.concurrent.Executors
import javax.inject.Inject

@ContributesMultibinding(scope = AppInitializersScope::class, replaces = [DebugStrictModeInitializerModule::class])
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
                .setClassInstanceLimit(MainActivity::class.java, 3)
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
