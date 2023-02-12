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
