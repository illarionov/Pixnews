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
package ru.pixnews.foundation.featuretoggles.datasource.overrides

import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import ru.pixnews.foundation.featuretoggles.pub.serializers.SerializationException
import java.io.InputStream
import java.io.OutputStream

internal object OverridesSerializer : Serializer<Overrides> {
    override val defaultValue: Overrides = Overrides.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Overrides {
        try {
            return Overrides.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw SerializationException("Cannot read proto.", exception)
        }
    }

    @Suppress("IDENTIFIER_LENGTH")
    override suspend fun writeTo(t: Overrides, output: OutputStream) = t.writeTo(output)
}
