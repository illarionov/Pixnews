/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey.Companion.CONTROL_GROUP
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
            DataSourceResult.completeFailure(FeatureToggleDataSourceError.ExperimentNotFound),
        )
    }

    override fun getAssignedVariant(experimentKey: ExperimentKey): Flow<DataSourceResult<ExperimentVariant>> {
        val experiment = experiments[experimentKey]
        return if (experiment != null) {
            val controlVariant = checkNotNull(experiment.variants[CONTROL_GROUP]) {
                "No control variant on experiment $experimentKey"
            }
            flowOf(DataSourceResult.completeSuccess(controlVariant))
        } else {
            noSuchExperimentStateFlow
        }
    }

    override suspend fun awaitUntilInitialized(): Unit = Unit
}
