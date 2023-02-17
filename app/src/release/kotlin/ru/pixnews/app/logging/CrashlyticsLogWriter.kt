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
package ru.pixnews.app.logging

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
