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
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.foundation.featuretoggles.fixtures.DarkModeTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.foundation.featuretoggles.pub.ExperimentVariant
import ru.pixnews.libraries.testing.MainCoroutineExtension
import ru.pixnews.libraries.testing.TestingLoggers

class FeatureManagerImplTest {
    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()
    private val experiments = setOf(
        DarkModeTestExperiment,
        HomeScreenGameCardTestExperiment,
    )
    private val testLogger: Logger = TestingLoggers.consoleLogger

    @Test
    fun `getVariant() should throw exception on unknown experiment`() = coroutinesExt.runTest {
        val featureManager = FeatureManagerImpl(
            experiments = experiments,
            dataSources = listOf(),
            logger = testLogger,
        )

        shouldThrow<NoDataSourceException> {
            featureManager.getVariant(DarkModeTestExperiment)
        }
    }

    @Test
    fun `getVariant() should return correct variant from data source with higher priority`() = coroutinesExt.runTest {
        val dataSourceLowPriority = FakeDataSource(HomeScreenGameCardTestExperiment.Variants.V1Group)
        val dataMiddlePriority = FakeDataSource(HomeScreenGameCardTestExperiment.Variants.V2Group)
        val dataSourceHighestPriority = FakeDataSource(ExperimentNotFound)

        val dataSources = listOf(
            DataSourceWithPriority(dataSourceLowPriority, 19),
            DataSourceWithPriority(dataMiddlePriority, 0),
            DataSourceWithPriority(dataSourceHighestPriority, -20),
        )

        val featureManager = FeatureManagerImpl(
            experiments = experiments,
            dataSources = dataSources,
            logger = testLogger,
        )

        val variant: ExperimentVariant = featureManager.getVariant(DarkModeTestExperiment)

        variant shouldBe HomeScreenGameCardTestExperiment.Variants.V2Group
    }

    @Test
    fun `getVariant() should return value from lower-priority data source on error`() = coroutinesExt.runTest {
        val dataSourceWithError = FakeDataSource(FeatureToggleDataSourceError.DataSourceError(RuntimeException()))
        val lowerPriorityDataSource = FakeDataSource(DarkModeTestExperiment.activeGroup)

        val featureManager = FeatureManagerImpl(
            experiments = experiments,
            dataSources = listOf(
                DataSourceWithPriority(dataSourceWithError, 0),
                DataSourceWithPriority(lowerPriorityDataSource, 10),
            ),
            logger = testLogger,
        )

        val variant: ExperimentVariant = featureManager.getVariant(DarkModeTestExperiment)

        variant shouldBe DarkModeTestExperiment.activeGroup
    }
}
