/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.serializers

import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer

public open class BooleanVariantSerializer(
    private val controlGroupVariant: ExperimentVariant,
    private val activeGroupVariant: ExperimentVariant,
) : ExperimentVariantSerializer {
    override fun fromString(key: ExperimentKey, serialized: String): ExperimentVariant {
        return when {
            "false".equals(serialized, true) -> controlGroupVariant
            "true".equals(serialized, true) -> activeGroupVariant
            else -> throw SerializationException("Can not deserialize string `$serialized` for experiment `$key`")
        }
    }

    @Suppress("ExceptionRaisedInUnexpectedLocation")
    override fun toString(key: ExperimentKey, deserialized: ExperimentVariant): String {
        return when (deserialized.key) {
            controlGroupVariant.key -> "false"
            activeGroupVariant.key -> "true"
            else -> throw SerializationException("Can not serialize group `${deserialized.key}` for experiment `$key`")
        }
    }
}
