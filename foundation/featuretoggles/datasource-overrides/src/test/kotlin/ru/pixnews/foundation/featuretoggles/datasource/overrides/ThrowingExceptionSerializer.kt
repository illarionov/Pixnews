/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.datasource.overrides

import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.serializers.SerializationException

@Suppress("ExceptionRaisedInUnexpectedLocation")
open class ThrowingExceptionSerializer : ExperimentVariantSerializer {
    override fun fromString(key: ExperimentKey, serialized: String): ExperimentVariant {
        throw SerializationException("Can not deserialize string `$serialized` for experiment `$key`")
    }

    override fun toString(key: ExperimentKey, deserialized: ExperimentVariant): String {
        throw SerializationException("Can not serialize group `${deserialized.key}` for experiment `$key`")
    }
}
