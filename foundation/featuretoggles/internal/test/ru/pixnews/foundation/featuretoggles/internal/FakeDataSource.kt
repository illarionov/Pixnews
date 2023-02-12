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

import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.foundation.featuretoggles.pub.ExperimentKey
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.libraries.functional.RequestStatus.Complete
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal class FakeDataSource(
    private val initDelay: Duration = 5.seconds,
    private val getVariant: (ExperimentKey) -> Flow<DataSourceResult<ExperimentVariant>>,
) : FeatureToggleDataSource {
    override fun getAssignedVariant(experimentKey: ExperimentKey): Flow<DataSourceResult<ExperimentVariant>> {
        return getVariant(experimentKey)
    }

    override suspend fun awaitUntilInitialized() {
        delay(initDelay)
    }

    companion object {
        internal operator fun invoke(
            variant: ExperimentVariant,
        ) = FakeDataSource { flowOf(Complete(variant.right())) }

        internal operator fun invoke(
            error: FeatureToggleDataSourceError,
        ) = FakeDataSource { flowOf(Complete(error.left())) }

        internal operator fun invoke(
            variants: Map<ExperimentKey, ExperimentVariant>,
        ) = FakeDataSource {
            val result = variants[it]?.right() ?: ExperimentNotFound.left()
            flowOf(Complete(result))
        }
    }
}
