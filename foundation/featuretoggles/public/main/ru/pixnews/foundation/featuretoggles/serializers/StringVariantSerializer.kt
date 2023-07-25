/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.serializers

import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer

@Suppress("ExceptionRaisedInUnexpectedLocation")
public open class StringVariantSerializer(
    private val serializedToVariant: Map<String, ExperimentVariant>,
) : ExperimentVariantSerializer {
    private val variantToSerialized = serializedToVariant
        .map { (serialized, variant) -> variant.key to serialized }
        .toMap()

    override fun fromString(key: ExperimentKey, serialized: String): ExperimentVariant {
        return serializedToVariant[serialized]
            ?: throw SerializationException("Can not deserialize string `$serialized` for experiment `$key`")
    }

    override fun toString(key: ExperimentKey, deserialized: ExperimentVariant): String {
        return variantToSerialized[deserialized.key]
            ?: throw SerializationException("Can not serialize group `${deserialized.key}` for experiment `$key`")
    }
}
