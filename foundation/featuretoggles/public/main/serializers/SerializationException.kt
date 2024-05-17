/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.serializers

import ru.pixnews.foundation.featuretoggles.FeatureToggleException

public open class SerializationException @JvmOverloads public constructor(
    message: String? = null,
    cause: Throwable? = null,
) : FeatureToggleException(message, cause)
