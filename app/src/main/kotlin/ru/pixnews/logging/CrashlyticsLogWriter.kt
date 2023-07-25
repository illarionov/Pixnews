/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal class CrashlyticsLogWriter(
    private val crashlytics: FirebaseCrashlytics = FirebaseCrashlytics.getInstance(),
    private val minSeverity: Severity = Severity.Info,
    private val minCrashSeverity: Severity = Severity.Warn,
) : LogWriter() {
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        if (severity.ordinal >= minSeverity.ordinal) {
            crashlytics.log("$tag: $message")
        }
        if (throwable != null && severity.ordinal >= minCrashSeverity.ordinal) {
            crashlytics.recordException(throwable)
        }
    }
}
