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
