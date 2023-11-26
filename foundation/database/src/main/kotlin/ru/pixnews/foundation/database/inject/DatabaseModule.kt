/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.inject

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.di.base.qualifiers.ApplicationContext
import ru.pixnews.foundation.di.base.scopes.AppScope

@ContributesTo(AppScope::class)
@Module
public object DatabaseModule {
    @SingleIn(AppScope::class)
    public fun providePixnewsDatabase(
        @ApplicationContext applicationContext: Context,
    ): PixnewsDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PixnewsDatabase::class.java,
            "pixnews",
        )
            .setJournalMode(WRITE_AHEAD_LOGGING)
            .createFromAsset("pixnews.db")
            .build()
    }
}
