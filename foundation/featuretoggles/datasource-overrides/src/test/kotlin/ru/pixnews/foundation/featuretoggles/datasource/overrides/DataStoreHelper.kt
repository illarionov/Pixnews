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
                key.stringValue to VariantPayload.newBuilder().setPayload(payloadString).build()
            }
            .toMap()
        data.toBuilder().putAllToggles(toggles).build()
    }
}
