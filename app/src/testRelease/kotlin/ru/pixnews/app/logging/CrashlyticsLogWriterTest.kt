/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.app.logging

import co.touchlab.kermit.Severity
import co.touchlab.kermit.Severity.Info
import co.touchlab.kermit.Severity.Warn
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifyAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import ru.pixnews.logging.CrashlyticsLogWriter

@ExtendWith(MockKExtension::class)
class CrashlyticsLogWriterTest {
    @MockK
    internal lateinit var crashlytics: FirebaseCrashlytics
    private lateinit var logWriter: CrashlyticsLogWriter

    @BeforeEach
    fun setUp() {
        every { crashlytics.log(any()) } just runs
        every { crashlytics.recordException(any()) } just runs

        logWriter = CrashlyticsLogWriter(
            crashlytics = crashlytics,
            minSeverity = Info,
            minCrashSeverity = Warn,
        )
    }

    @ParameterizedTest
    @EnumSource(value = Severity::class, names = ["Info", "Warn", "Error", "Assert"])
    fun `CrashlyticsLogWriter should pass log messages to crashlytics`(severity: Severity) {
        logWriter.log(
            severity = severity,
            message = "Test message",
            tag = "TestTag",
            throwable = null,
        )

        verifyAll {
            crashlytics.log("TestTag: Test message")
        }
    }

    @ParameterizedTest
    @EnumSource(value = Severity::class, names = ["Verbose", "Debug"])
    fun `CrashlyticsLogWriter should not pass log messages with severity level below the set`(severity: Severity) {
        logWriter.log(
            severity = severity,
            message = "Test message",
            tag = "TestTag",
            throwable = null,
        )

        verify(exactly = 0) {
            crashlytics.log(any())
        }
    }

    @ParameterizedTest
    @EnumSource(value = Severity::class, names = ["Warn", "Error", "Assert"])
    fun `CrashlyticsLogWriter should pass exceptions to crashlytics`(severity: Severity) {
        val testException = RuntimeException("Test exception")

        logWriter.log(
            severity = severity,
            message = "Test message",
            tag = "TestTag",
            throwable = testException,
        )

        verify {
            crashlytics.recordException(testException)
        }
    }

    @ParameterizedTest
    @EnumSource(value = Severity::class, names = ["Verbose", "Debug", "Info"])
    fun `CrashlyticsLogWriter should not pass exceptions with severity level below the set`(severity: Severity) {
        val testException = RuntimeException("Test exception")

        logWriter.log(
            severity = severity,
            message = "Test message",
            tag = "TestTag",
            throwable = testException,
        )

        verify(exactly = 0) {
            crashlytics.recordException(any())
        }
    }
}
