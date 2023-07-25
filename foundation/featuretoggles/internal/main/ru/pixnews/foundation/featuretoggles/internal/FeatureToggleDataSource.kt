/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.internal

import kotlinx.coroutines.flow.Flow
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.library.functional.RequestStatus

public typealias DataSourceResult<V> = RequestStatus<FeatureToggleDataSourceError, V>

public interface FeatureToggleDataSource {
    public fun getAssignedVariant(experimentKey: ExperimentKey): Flow<DataSourceResult<ExperimentVariant>>

    public suspend fun awaitUntilInitialized()
}

public sealed class FeatureToggleDataSourceError {
    public object ExperimentNotFound : FeatureToggleDataSourceError()

    public class DataSourceError(public val throwable: Throwable) : FeatureToggleDataSourceError()
}

public fun FeatureToggleDataSource.getAssignedVariant(
    experiment: Experiment,
): Flow<DataSourceResult<ExperimentVariant>> = getAssignedVariant(experiment.key)
