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
