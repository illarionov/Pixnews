/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject

import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity.Verbose
import co.touchlab.kermit.StaticConfig
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@Module
object LoggingModule {
    @Provides
    @Reusable
    fun provideLogger(): Logger {
        val config = StaticConfig(
            minSeverity = Verbose,
            logWriterList = listOf(
                LogcatWriter(),
            ),
        )
        return Logger(config).withTag("PixnewsDBG")
    }
}
