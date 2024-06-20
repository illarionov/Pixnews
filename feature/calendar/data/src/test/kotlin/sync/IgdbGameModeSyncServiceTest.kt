/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("COMMENTED_OUT_CODE")

package ru.pixnews.feature.calendar.data.sync

import android.content.ContextWrapper
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
import ru.pixnews.wasm.sqlite.open.helper.WasmSqliteOpenHelperFactory
import ru.pixnews.wasm.sqlite.open.helper.dsl.path.JvmDatabasePathResolver
import ru.pixnews.wasm.sqlite.open.helper.graalvm.GraalvmSqliteEmbedder
import ru.pixnews.wasm.sqlite.open.helper.sqlite.common.api.SqliteDatabaseJournalMode.PERSIST
import ru.pixnews.wasm.sqlite.open.helper.sqlite.common.api.SqliteDatabaseSyncMode
import java.io.File
import co.touchlab.kermit.Logger as KermitLogger

class IgdbGameModeSyncServiceTest {
    val logger = TestingLoggers.consoleLogger
    val mockContext = ContextWrapper(null)

    @TempDir
    lateinit var tempDir: File
    lateinit var db: PixnewsDatabase

    @BeforeEach
    fun createDb() {
        val dbLogger = KermitLogger(
            config = object : LoggerConfig by logger.config {
                override val minSeverity: Severity = Severity.Info
            },
            "Sqlite",
        )

        @Suppress("UnusedPrivateProperty")
        val helperFactory = WasmSqliteOpenHelperFactory(GraalvmSqliteEmbedder) {
            logger = SqliteLogger(dbLogger)
            pathResolver = JvmDatabasePathResolver(tempDir)
            openParams {
                journalMode = PERSIST
                syncMode = SqliteDatabaseSyncMode.OFF
            }
            debug {
                sqlLog = false
                sqlStatements = false
                sqlTime = false
                logSlowQueries = false
            }
        }

        val dbFile = File(tempDir, "db.sqlite")

        // TODO
        db = Room.databaseBuilder(
            name = dbFile.absolutePath,
            factory = ::PixnewsDatabase_Impl,
            // context = mockContext,
        )
            .setJournalMode(WRITE_AHEAD_LOGGING)
            // .createFromAsset("pixnews.db")
            // .setQueryCallback(QueryLogger(logger), QueryLogger.createLoggerExecutor())
            // .openHelperFactory(helperFactory)
            // .allowMainThreadQueries()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    @AfterEach
    fun closeDb() {
        db.close()
    }

    @Test
    fun dbTest() = runTest {
        // val gameMode = db.gameModeNameDao().getById(1)
        // logger.i { "gem mode: $gameMode" }
    }
}
