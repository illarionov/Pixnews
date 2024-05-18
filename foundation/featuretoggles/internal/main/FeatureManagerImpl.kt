/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.internal

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.first
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.FeatureManager
import ru.pixnews.foundation.featuretoggles.FeatureToggle
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.DataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.library.functional.RequestStatus.Complete
import ru.pixnews.library.functional.Result
import ru.pixnews.library.functional.onError
import ru.pixnews.library.functional.onSuccess

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
