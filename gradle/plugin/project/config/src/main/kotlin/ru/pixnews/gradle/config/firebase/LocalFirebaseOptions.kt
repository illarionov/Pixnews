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
