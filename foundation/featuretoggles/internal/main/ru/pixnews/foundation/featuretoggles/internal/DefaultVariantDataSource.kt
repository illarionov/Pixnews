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
package ru.pixnews.foundation.featuretoggles.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariantKey.Companion.CONTROL_GROUP
import kotlin.LazyThreadSafetyMode.PUBLICATION

public class DefaultVariantDataSource(
    experiments: Set<Experiment>,
) : FeatureToggleDataSource {
    private val experiments: Map<ExperimentKey, Experiment> = experiments
        .groupingBy { experiment -> experiment.key }
        .reduce { key, _, element ->
            throw DuplicateExperimentException("Duplicate key '$key' encountered for element '$element'")
        }
    private val noSuchExperimentStateFlow: StateFlow<DataSourceResult<ExperimentVariant>> by lazy(PUBLICATION) {
        MutableStateFlow(
            DataSourceResult.failure(FeatureToggleDataSourceError.ExperimentNotFound),
        )
    }

    override fun getAssignedVariant(experimentKey: ExperimentKey): Flow<DataSourceResult<ExperimentVariant>> {
        val experiment = experiments[experimentKey]
        return if (experiment != null) {
            val controlVariant = checkNotNull(experiment.variants[CONTROL_GROUP]) {
                "No control variant on experiment $experimentKey"
            }
            flowOf(DataSourceResult.success(controlVariant))
        } else {
            noSuchExperimentStateFlow
        }
    }

    override suspend fun awaitUntilInitialized(): Unit = Unit
}
