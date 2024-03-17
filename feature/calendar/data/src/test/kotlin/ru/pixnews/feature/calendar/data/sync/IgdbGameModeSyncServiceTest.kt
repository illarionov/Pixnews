/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.sync

import android.content.ContextWrapper
import androidx.room.Room
import androidx.room.RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.api.io.TempDir
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.database.util.QueryLogger
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.TestingLoggers
import ru.pixnews.wasm.sqlite.open.helper.Sqlite3Wasm
import ru.pixnews.wasm.sqlite.open.helper.WasmSqliteOpenHelperFactory
import ru.pixnews.wasm.sqlite.open.helper.graalvm.GraalvmSqliteCapi
import ru.pixnews.wasm.sqlite.open.helper.internal.DatabasePathResolver
import ru.pixnews.wasm.sqlite.open.helper.internal.SQLiteDebug
import java.io.File

class IgdbGameModeSyncServiceTest {
    val logger = TestingLoggers.consoleLogger
    val mockContext = ContextWrapper(null)

    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()

    @TempDir
    lateinit var tempDir: File

    @Test
    fun dbTest() = coroutinesExt.runTest {
        val db = createDb()
        val gameMode = db.gameModeNameDao().getByIdTestBlocking(1)
        logger.i { "gem mode: $gameMode" }
    }

    private fun createDb(): PixnewsDatabase {
        val pathResolver = DatabasePathResolver { name -> File(tempDir, name) }
        val debugConfig = SQLiteDebug(true, true, true, true)
        val sqlitecApi = GraalvmSqliteCapi(
            sqlite3Url = Sqlite3Wasm.Emscripten.sqlite3_345,
        )

        val helperFactory = WasmSqliteOpenHelperFactory(
            pathResolver = pathResolver,
            sqliteCapi = sqlitecApi,
            debugConfig = debugConfig,
        )

        return Room.databaseBuilder(
            mockContext,
            PixnewsDatabase::class.java,
            "pixnews",
        )
            .setJournalMode(WRITE_AHEAD_LOGGING)
            // .createFromAsset("pixnews.db")
            .setQueryCallback(QueryLogger(logger), QueryLogger.createLoggerExecutor())
            .openHelperFactory(helperFactory)
            .allowMainThreadQueries()
            .build()
    }
}
