/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.util

import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import java.util.concurrent.Executor
import java.util.concurrent.Executors

internal class QueryLogger(
    logger: Logger,
) : RoomDatabase.QueryCallback {
    private val logger = logger.withTag("Pixnews SQL")
    override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
        logger.d { sqlQuery + "; ARGS: " + bindArgs.joinToString(", ") { "`$it`" } }
    }

    internal companion object {
        fun createLoggerExecutor(): Executor = Executors.newSingleThreadExecutor { runnable ->
            Thread(runnable, "Pixnews database logger").apply {
                isDaemon = true
            }
        }
    }
}
