/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.util.strictmode

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.StrictMode
import android.os.StrictMode.OnThreadViolationListener
import android.os.StrictMode.OnVmViolationListener
import android.os.strictmode.Violation
import androidx.annotation.RequiresApi
import co.touchlab.kermit.Logger
import ru.pixnews.util.strictmode.ViolationListener.Companion.createThreadExecutor
import ru.pixnews.util.strictmode.ViolationPolicy.FAIL
import ru.pixnews.util.strictmode.ViolationPolicy.IGNORE
import ru.pixnews.util.strictmode.ViolationPolicy.LOG
import java.util.concurrent.Executor

internal fun StrictMode.VmPolicy.Builder.setupViolationListener(
    logger: Logger,
    policy: ViolationPolicy = LOG,
    logAllowListViolation: Boolean = false,
    allowList: List<Violation.() -> Boolean> = emptyList(),
): StrictMode.VmPolicy.Builder = if (VERSION.SDK_INT >= VERSION_CODES.P) {
    val listener = ViolationListener(logger, policy, logAllowListViolation, allowList)
    this.penaltyListener(createThreadExecutor(), listener)
} else {
    if (policy != IGNORE) {
        this.penaltyLog()
    } else {
        this
    }
}

internal fun StrictMode.ThreadPolicy.Builder.setupViolationListener(
    logger: Logger,
    policy: ViolationPolicy = LOG,
    logAllowListViolation: Boolean = false,
    allowList: List<Violation.() -> Boolean> = emptyList(),
): StrictMode.ThreadPolicy.Builder = if (VERSION.SDK_INT >= VERSION_CODES.P) {
    val listener = ViolationListener(logger, policy, logAllowListViolation, allowList)
    this.penaltyListener(createThreadExecutor(), listener)
} else {
    if (policy != IGNORE) {
        this.penaltyLog()
    } else {
        this
    }
}

@RequiresApi(VERSION_CODES.P)
internal class ViolationListener(
    private val logger: Logger,
    private val policy: ViolationPolicy = LOG,
    private val logAllowListViolation: Boolean = false,
    private val allowList: List<Violation.() -> Boolean> = emptyList(),
) : OnVmViolationListener, OnThreadViolationListener {
    override fun onVmViolation(violation: Violation) {
        onViolation("VM", violation)
    }

    override fun onThreadViolation(violation: Violation) {
        onViolation("Thread", violation)
    }

    private fun onViolation(
        type: String,
        violation: Violation,
    ) {
        if (violation.shouldSkip()) {
            if (logAllowListViolation) {
                logger.e {
                    "Skipping $type violation `$violation: message: ${violation.message}`, " +
                            "stracktrace: ${violation.stackTraceToString()}"
                }
            }
            return
        }
        when (policy) {
            LOG -> logger.e(violation) { "$type violation" }
            FAIL -> throw StrictModeViolationException(violation)
            IGNORE -> Unit
        }
    }

    private fun Violation.shouldSkip(): Boolean = allowList.any { it() }

    companion object {
        fun createThreadExecutor(): Executor = Executor {
            it.run()
        }
    }
}

class StrictModeViolationException(throwable: Throwable) : RuntimeException(throwable)

enum class ViolationPolicy {
    IGNORE,
    LOG,
    FAIL,
}
