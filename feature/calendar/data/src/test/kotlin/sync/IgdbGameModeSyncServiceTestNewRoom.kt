/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("COMMENTED_OUT_CODE")

package ru.pixnews.feature.calendar.data.sync

import android.content.ContextWrapper
import androidx.room.Room
import androidx.room.RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING
import at.released.wasm.sqlite.binary.aot.SqliteAndroidWasmEmscriptenIcuAot349
import at.released.wasm.sqlite.binary.aot.SqliteAndroidWasmEmscriptenIcuAot349Machine
import at.released.wasm.sqlite.driver.WasmSQLiteDriver
import at.released.wasm.sqlite.open.helper.Locale
import at.released.wasm.sqlite.open.helper.chicory.ChicorySqliteEmbedder
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.api.io.TempDir
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.database.PixnewsDatabase_Impl
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.TestingLoggers
import java.io.File
import co.touchlab.kermit.Logger as KermitLogger

class IgdbGameModeSyncServiceTestNewRoom {
    val logger = TestingLoggers.consoleLogger
    val mockContext = object : ContextWrapper(null) {
        override fun getDatabasePath(name: String?): File = File(name!!)
    }

    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()

    @TempDir
    lateinit var tempDir: File
    lateinit var db: PixnewsDatabase
    lateinit var queryCoroutineContext: CloseableCoroutineDispatcher

    suspend fun beforeEach() {
        val dbLogger = KermitLogger(
            config = object : LoggerConfig by logger.config {
                override val minSeverity: Severity = Severity.Info
            },
            "WasmSqlite",
        )
        val dbFile = File(tempDir, "db.sqlite")
        val driver = WasmSQLiteDriver(ChicorySqliteEmbedder) {
            logger = SqliteLogger(dbLogger)
            openParams {
                locale = Locale("ru_RU")
            }
            embedder {
                sqlite3Binary = SqliteAndroidWasmEmscriptenIcuAot349
                machineFactory = ::SqliteAndroidWasmEmscriptenIcuAot349Machine
            }
        }

        // or newSingleThreadContext("RoomDatabase")
//        queryCoroutineContext = Executors.newScheduledThreadPool(
//            1,
//            driver.runtime.managedThreadFactory,
//        ).asCoroutineDispatcher()
        queryCoroutineContext = newSingleThreadContext("RoomDatabase")

        db = Room.databaseBuilder(
            name = dbFile.absolutePath,
            factory = ::PixnewsDatabase_Impl,
            context = mockContext,
        )
            .setJournalMode(WRITE_AHEAD_LOGGING)
            // .createFromAsset("pixnews.db")
            .setDriver(driver)
            .setQueryCoroutineContext(queryCoroutineContext)
            .allowMainThreadQueries()
            .build()
    }

    suspend fun afterEach() {
        db.close()
        queryCoroutineContext.close()
    }

    @Test
    fun dbTest() = runSuspendDbTest {
        val gameMode = db.gameModeNameDao().getById(1)
        logger.i { "gem mode: $gameMode" }
    }

    private fun runSuspendDbTest(block: suspend () -> Unit) = coroutinesExt.runTest {
        beforeEach()
        try {
            block()
        } finally {
            afterEach()
        }
    }
}
