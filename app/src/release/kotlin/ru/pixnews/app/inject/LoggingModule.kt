/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.app.inject

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.foundation.di.base.scopes.AppScope
import ru.pixnews.logging.CrashlyticsLogWriter
import javax.inject.Named

@ContributesTo(AppScope::class)
@Module
object LoggingModule {
    @Provides
    @Reusable
    @Named("CrashlyticsLogWriter")
    fun provideCrashlyticsLogWriter(firebase: FirebaseApp): LogWriter {
        return CrashlyticsLogWriter(
            crashlytics = firebase.get(FirebaseCrashlytics::class.java),
            minSeverity = Severity.Info,
            minCrashSeverity = Severity.Warn,
        )
    }

    @Provides
    @Reusable
    fun provideLogger(
        @Named("CrashlyticsLogWriter") logWriter: LogWriter,
    ): Logger {
        val config = StaticConfig(
            minSeverity = Severity.Info,
            logWriterList = listOf(logWriter),
        )
        return Logger(config).withTag("Pixnews")
    }
}
