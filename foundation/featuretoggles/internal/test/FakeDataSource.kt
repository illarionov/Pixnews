/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.internal

import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.library.functional.RequestStatus.Complete
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
