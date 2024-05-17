/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.migration

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import co.touchlab.kermit.Logger
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.pixnews.foundation.database.PixnewsDatabase
import ru.pixnews.foundation.database.migration.DatabaseFixtures.prePopulateDatabase

@RunWith(AndroidJUnit4::class)
class PixnewsDatabaseMigrationTest {
    val logger = Logger.withTag("PixnewsDatabaseMigrationTest")

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        PixnewsDatabase::class.java,
    )

    @Test
    fun testDatabase() {
        val database = helper.createDatabase(TEST_DB, 1)
            .apply {
                prePopulateDatabase()
                close()
            }
        logger.i { "database path: ${database.path} " }
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }
}
