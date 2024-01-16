/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.inject.initializer

import co.touchlab.kermit.Logger
import ru.pixnews.anvil.codegen.initializer.inject.ContributesInitializer
import ru.pixnews.foundation.initializers.AsyncInitializer
import ru.pixnews.initializers.FirebaseInitializer

@ContributesInitializer(replaces = [FirebaseInitializer::class])
class TestFirebaseInitializer(
    val logger: Logger,
) : AsyncInitializer {
    override fun init() {
        logger.i("Skipping Firebase initialization")
    }
}
