/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.gradle.config.firebase

import java.io.StringReader
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
        ): FirebaseConfigReader {
            val properties = Properties().apply {
                StringReader(configFileContext).use {
                    load(it)
                }
            }
            return FirebaseConfigReader(properties, applicationId)
        }
    }
}
