/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.initializer

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import androidx.activity.ComponentActivity
import androidx.fragment.app.strictmode.FragmentStrictMode
import androidx.fragment.app.strictmode.FragmentStrictMode.Policy
import co.touchlab.kermit.Logger
import ru.pixnews.MainActivity
import ru.pixnews.anvil.codegen.initializer.inject.ContributesInitializer
import ru.pixnews.foundation.initializers.Initializer
import ru.pixnews.inject.DebugStrictModeInitializerModule
import ru.pixnews.util.strictmode.ViolationPolicy.FAIL
import ru.pixnews.util.strictmode.isFontRequestViolation
import ru.pixnews.util.strictmode.isGmsDiskReadViolation
import ru.pixnews.util.strictmode.isInstanceCountViolation
import ru.pixnews.util.strictmode.isInstrumentationDexMakerViolation
import ru.pixnews.util.strictmode.isProfileSizeOfAppViolation
import ru.pixnews.util.strictmode.isTypefaceFullFlipFontViolation
import ru.pixnews.util.strictmode.isUntaggedSocketViolation
import ru.pixnews.util.strictmode.setupViolationListener
import javax.inject.Inject

@ContributesInitializer(replaces = [DebugStrictModeInitializerModule::class])
class TestStrictModeInitializer @Inject constructor(logger: Logger) : Initializer {
    private val logger = logger.withTag("TestStrictModeInitializer")

    override fun init() {
        logger.v { "Setting up StrictMode" }
        StrictMode.setThreadPolicy(
            Builder()
                .detectAll()
                .setupViolationListener(
                    logger = logger,
                    policy = FAIL,
                    logAllowListViolation = true,
                    allowList = ALLOWLIST,
                )
                .build(),
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectAll()
                // Class instance limit occasionally triggered in instrumented tests for unknown reasons
                .setClassInstanceLimit(MainActivity::class.java, 5)
                .setClassInstanceLimit(ComponentActivity::class.java, 5)
                .setupViolationListener(
                    logger = logger,
                    policy = FAIL,
                    logAllowListViolation = true,
                    allowList = ALLOWLIST,
                )
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

    private companion object {
        val ALLOWLIST = listOf(
            isFontRequestViolation,
            isGmsDiskReadViolation,
            isInstanceCountViolation,
            isInstrumentationDexMakerViolation,
            isProfileSizeOfAppViolation,
            isTypefaceFullFlipFontViolation,
            isUntaggedSocketViolation,
        )
    }
}
