/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("COMMENTED_OUT_CODE")

package ru.pixnews.feature.calendar.data.sync

import androidx.room.Room
import androidx.room.RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.database.PixnewsDatabase_Impl
import ru.pixnews.library.test.TestingLoggers
import java.io.File
import co.touchlab.kermit.Logger as KermitLogger

class IgdbGameModeSyncServiceTestNewRoom {
    val logger = TestingLoggers.consoleLogger

    @TempDir
    lateinit var tempDir: File
    lateinit var db: PixnewsDatabase

    @BeforeEach
    fun createDb() {
        @Suppress("UnusedPrivateProperty")
        val dbLogger = KermitLogger(
            config = object : LoggerConfig by logger.config {
                override val minSeverity: Severity = Severity.Info
            },
            "Sqlite",
        )

        val dbFile = File(tempDir, "db.sqlite")

        db = Room.databaseBuilder(
            name = dbFile.absolutePath,
            factory = ::PixnewsDatabase_Impl,
        )
            .setJournalMode(WRITE_AHEAD_LOGGING)
            // .createFromAsset("pixnews.db")
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    @AfterEach
    fun closeDb() {
        db.close()
    }

    @Test
    fun dbTest() = runTest {
        // TODO
        // val gameMode = db.gameModeNameDao().getById(1)
        // logger.i { "gem mode: $gameMode" }
    }
}
