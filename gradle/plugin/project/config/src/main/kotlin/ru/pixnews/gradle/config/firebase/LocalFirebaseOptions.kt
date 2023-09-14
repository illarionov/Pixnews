/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.config.firebase

import java.io.Serializable

data class LocalFirebaseOptions(
    val projectId: String?,
    val apiKey: String?,
    val applicationId: String?,
    val databaseUrl: String?,
    val gaTrackingId: String?,
    val gcmSenderId: String?,
    val storageBucket: String?,
) : Serializable {
    companion object {
        @Suppress("CONSTANT_UPPERCASE")
        private const val serialVersionUID: Long = -1
        val empty = LocalFirebaseOptions(
            projectId = null,
            apiKey = null,
            applicationId = null,
            databaseUrl = null,
            gaTrackingId = null,
            gcmSenderId = null,
            storageBucket = null,
        )
    }
}
