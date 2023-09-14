/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.datasource.overrides

import androidx.datastore.core.DataStoreFactory
import kotlinx.coroutines.runBlocking
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.FeatureToggleException
import java.io.File

internal fun populateDataStoreFile(
    datastoreFile: File,
    serializers: Map<ExperimentKey, ExperimentVariantSerializer>,
    overrides: Map<ExperimentKey, ExperimentVariant> = emptyMap(),
) = runBlocking {
    val dataStore = DataStoreFactory.create(
        serializer = OverridesSerializer,
        scope = this,
        produceFile = { datastoreFile },
    )

    dataStore.updateData { data ->
        val toggles = overrides
            .map { (key, variant) ->
                val payloadString = serializers[key]?.toString(key, variant)
                    ?: throw FeatureToggleException("No serializer for experiment `$key` found")
                key.stringValue to VariantPayload(payloadString)
            }
            .toMap()
        data.copy(toggles = toggles)
    }
}
