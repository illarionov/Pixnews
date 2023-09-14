/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.config.igdb

import java.io.Serializable

data class LocalIgdbOptions(
    val igdbBaseUrl: String?,
    val igdbApiKey: String?,
    val igdbTwitchClientId: String?,
    val igdbTwitchClientSecret: String?,
    val igdbTwitchToken: String?,
) : Serializable {
    companion object {
        @Suppress("CONSTANT_UPPERCASE")
        private const val serialVersionUID: Long = -1
        val empty = LocalIgdbOptions(
            igdbBaseUrl = null,
            igdbApiKey = null,
            igdbTwitchClientId = null,
            igdbTwitchClientSecret = null,
            igdbTwitchToken = null,
        )
    }
}
