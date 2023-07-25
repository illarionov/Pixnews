/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.featuretoggles.model

import androidx.compose.runtime.Immutable

@Immutable
internal class PermanentErrorMessage(
    val throwable: Throwable,
) {
    private val toString = throwable.toString()

    val localizedMessage: String
        get() {
            val message = throwable.message
            return if (message.isNullOrBlank()) {
                throwable.toString()
            } else {
                message
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as PermanentErrorMessage

        if (toString != other.toString) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return toString.hashCode()
    }
}
