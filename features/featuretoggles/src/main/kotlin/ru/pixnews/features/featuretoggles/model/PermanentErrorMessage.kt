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
package ru.pixnews.features.featuretoggles.model

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
