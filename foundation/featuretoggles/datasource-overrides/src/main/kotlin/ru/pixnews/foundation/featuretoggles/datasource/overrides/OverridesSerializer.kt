/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.datasource.overrides

import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import ru.pixnews.foundation.featuretoggles.serializers.SerializationException
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
