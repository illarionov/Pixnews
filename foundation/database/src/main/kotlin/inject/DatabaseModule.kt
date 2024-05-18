/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.inject

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING
import co.touchlab.kermit.Logger
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.appconfig.logDatabaseQueries
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.database.util.QueryLogger
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@Module
public object DatabaseModule {
    @SingleIn(AppScope::class)
    @Provides
    public fun providePixnewsDatabase(
        @ApplicationContext applicationContext: Context,
        appConfig: AppConfig,
        logger: Logger,
    ): PixnewsDatabase {
        val builder = Room.databaseBuilder(
            applicationContext,
            PixnewsDatabase::class.java,
            "pixnews",
        )
            .setJournalMode(WRITE_AHEAD_LOGGING)
            .createFromAsset("pixnews.db")

        if (appConfig.logDatabaseQueries()) {
            builder.setQueryCallback(QueryLogger(logger), QueryLogger.createLoggerExecutor())
        }

        return builder.build()
    }
}
