/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.config.firebase

import ru.pixnews.gradle.config.util.toProperties
import java.util.Properties

internal class FirebaseConfigReader(
    private val properties: Properties,
    applicationId: String?,
) {
    @Suppress("NULLABLE_PROPERTY_TYPE")
    private val applicationIdPrefix: String? = applicationId?.replace('.', '_')

    fun read(): LocalFirebaseOptions {
        return LocalFirebaseOptions(
            projectId = readApplicationOrDefaultProperty("project_id"),
            apiKey = readApplicationOrDefaultProperty("google_api_key"),
            applicationId = readApplicationOrDefaultProperty("google_app_id"),
            databaseUrl = readApplicationOrDefaultProperty("database_url"),
            gaTrackingId = readApplicationOrDefaultProperty("ga_tracking_id"),
            gcmSenderId = readApplicationOrDefaultProperty("gcm_default_sender_id"),
            storageBucket = readApplicationOrDefaultProperty("storage_bucket"),
        )
    }

    private fun readApplicationOrDefaultProperty(key: String): String? {
        return applicationIdPrefix?.let {
            properties.getProperty("firebase_${applicationIdPrefix}_$key", null)
        } ?: properties.getProperty("firebase_$key", null)
    }

    companion object {
        operator fun invoke(
            configFileContext: String,
            applicationId: String?,
        ): FirebaseConfigReader = FirebaseConfigReader(
            configFileContext.toProperties(),
            applicationId,
        )
    }
}
