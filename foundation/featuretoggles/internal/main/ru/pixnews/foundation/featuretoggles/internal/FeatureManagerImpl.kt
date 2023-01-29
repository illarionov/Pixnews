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

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.first
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.DataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.foundation.featuretoggles.pub.Experiment
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.pub.FeatureManager
import ru.pixnews.foundation.featuretoggles.pub.FeatureToggle
import ru.pixnews.libraries.functional.NetworkRequestStatus.Complete
import ru.pixnews.libraries.functional.Result
import ru.pixnews.libraries.functional.onError
import ru.pixnews.libraries.functional.onSuccess

public class FeatureManagerImpl(
    experiments: Set<Experiment>,
    dataSources: List<DataSourceWithPriority>,
    logger: Logger,
) : FeatureManager {
    private val logger = logger.withTag(FeatureToggleImpl::class.simpleName.toString())
    private val dataSources: List<FeatureToggleDataSource> = dataSources
        .sortedBy(DataSourceWithPriority::priority)
        .map(DataSourceWithPriority::dataSource)
    override val featureToggles: Map<ExperimentKey, FeatureToggle<*>> = experiments
        .map(::FeatureToggleImpl)
        .groupingBy { featureToggle -> featureToggle.experiment.key }
        .reduce { key, _, element ->
            throw DuplicateExperimentException("Duplicate key '$key' encountered for element '$element'")
        }

    override suspend fun suspendUntilLoaded() {
        dataSources.forEach { it.awaitUntilInitialized() }
    }

    internal suspend fun <V : ExperimentVariant> getVariant(experiment: Experiment): V {
        for (dataSource in dataSources) {
            val result: Result<FeatureToggleDataSourceError, ExperimentVariant> =
                dataSource.getAssignedVariant(experiment)
                    .first { it is Complete }.let {
                        (it as Complete).result
                    }
            result.onSuccess {
                @Suppress("UNCHECKED_CAST")
                return it as V
            }.onError { error ->
                when (error) {
                    is DataSourceError -> logger.w(error.throwable) { "Can not fetch active variant from $dataSource" }
                    is ExperimentNotFound -> {}
                }
            }
        }
        throw NoDataSourceException("No data source found for experiment ${experiment.key}")
    }

    private inner class FeatureToggleImpl<E : Experiment>(
        override val experiment: E,
    ) : FeatureToggle<E> {
        override suspend fun <V : ExperimentVariant> getVariant(): V {
            return this@FeatureManagerImpl.getVariant(experiment)
        }
    }
}
