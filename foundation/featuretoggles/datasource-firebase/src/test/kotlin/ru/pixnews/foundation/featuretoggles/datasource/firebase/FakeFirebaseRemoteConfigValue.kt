/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.datasource.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

internal data class FakeFirebaseRemoteConfigValue(
    private val value: String,
    private val source: Int = FirebaseRemoteConfig.VALUE_SOURCE_REMOTE,
) : FirebaseRemoteConfigValue {
    override fun asLong(): Long {
        if (source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_LONG
        }
        return value.toLongOrNull() ?: throw IllegalArgumentException("Illegal string format")
    }

    override fun asDouble(): Double {
        return if (source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) {
            FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE
        } else {
            value.toDoubleOrNull() ?: throw IllegalArgumentException("Illegal string format")
        }
    }

    override fun asString(): String {
        return if (source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_STRING
        } else {
            value
        }
    }

    override fun asByteArray(): ByteArray {
        return if (source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) {
            FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BYTE_ARRAY
        } else {
            value.toByteArray()
        }
    }

    override fun asBoolean(): Boolean {
        return if (source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) {
            FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BOOLEAN
        } else {
            value.toBooleanStrictOrNull() ?: throw IllegalArgumentException("Illegal string format")
        }
    }

    override fun getSource(): Int = source
}
