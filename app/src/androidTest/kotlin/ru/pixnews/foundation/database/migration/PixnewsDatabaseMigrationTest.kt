/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.migration

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.pixnews.foundation.database.PixnewsDatabase

@RunWith(AndroidJUnit4::class)
class PixnewsDatabaseMigrationTest {
    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        PixnewsDatabase::class.java,
    )

    @Test
    fun testDatabase() {
        helper.createDatabase(TEST_DB, 1).apply {
            close()
        }
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }
}
