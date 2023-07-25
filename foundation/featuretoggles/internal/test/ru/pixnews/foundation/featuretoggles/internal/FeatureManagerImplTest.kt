/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.internal

import co.touchlab.kermit.Logger
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.fixtures.DarkModeTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.TestingLoggers

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
        val dataSourceLowPriority = FakeDataSource(HomeScreenGameCardTestExperiment.Variant.V1Group)
        val dataMiddlePriority = FakeDataSource(HomeScreenGameCardTestExperiment.Variant.V2Group)
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

        variant shouldBe HomeScreenGameCardTestExperiment.Variant.V2Group
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
