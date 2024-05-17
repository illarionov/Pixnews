/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.internal

import arrow.core.Either
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.fail
import io.kotest.assertions.throwables.shouldThrow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.foundation.featuretoggles.DefaultExperimentVariant
import ru.pixnews.foundation.featuretoggles.Experiment
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey
import ru.pixnews.foundation.featuretoggles.experimentKey
import ru.pixnews.foundation.featuretoggles.experimentVariantKey
import ru.pixnews.foundation.featuretoggles.fixtures.DarkModeTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.DtfIntegrationTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.library.functional.RequestStatus.Complete
import ru.pixnews.library.test.MainCoroutineExtension

class DefaultVariantDataSourceTest {
    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()
    private val experiments = setOf(
        DarkModeTestExperiment,
        HomeScreenGameCardTestExperiment,
        NoControlGroupExperiment,
    )
    private val dataSource: DefaultVariantDataSource = DefaultVariantDataSource(experiments)

    private object NoControlGroupExperiment : Experiment {
        override val key: ExperimentKey = "no.control.group".experimentKey()
        override val name: String = "Experiment without control group"
        override val description: String = ""
        override val variants: Map<ExperimentVariantKey, ExperimentVariant> = listOf(
            DefaultExperimentVariant(key = "active".experimentVariantKey()),
        ).associateBy(ExperimentVariant::key)
    }

    @Test
    fun `getAssignedVariant() should return CONTROL group on supported experiment`() = coroutinesExt.runTest {
        val groupStatus = dataSource.getAssignedVariant(DarkModeTestExperiment.key).first()

        val group = (groupStatus as? Complete)?.result ?: fail("Expect download status to be Complete")

        group shouldBeRight DarkModeTestExperiment.controlGroup
    }

    @Test
    fun `getAssignedVariant() should return ExperimentNotFound on unknown experiment`() = coroutinesExt.runTest {
        val group: Either<FeatureToggleDataSourceError, ExperimentVariant> = dataSource
            .getAssignedVariant(DtfIntegrationTestExperiment.key)
            .filterIsInstance<Complete<FeatureToggleDataSourceError, ExperimentVariant>>()
            .first()
            .result

        group shouldBeLeft ExperimentNotFound
    }

    @Test
    fun `getAssignedVariant() throws exception on malformed experiment`() = coroutinesExt.runTest {
        shouldThrow<IllegalStateException> {
            dataSource.getAssignedVariant(NoControlGroupExperiment.key)
        }
    }
}
