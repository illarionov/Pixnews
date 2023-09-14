/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.initializers

import co.touchlab.kermit.Logger
import com.google.firebase.FirebaseApp
import ru.pixnews.foundation.initializers.AsyncInitializer
import ru.pixnews.foundation.initializers.inject.ContributesInitializer
import javax.inject.Provider

@ContributesInitializer
public class FirebaseInitializer(
    private val logger: Logger,
    private val firebaseApp: Provider<@JvmSuppressWildcards FirebaseApp>,
) : AsyncInitializer {
    override fun init() {
        logger.i { "Creating firebase instance" }
        firebaseApp.get()
    }
}
