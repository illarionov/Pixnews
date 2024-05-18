/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.datasource.firebase

import arrow.core.Either
import com.google.android.gms.tasks.Tasks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.fixtures.DarkModeTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.DtfIntegrationTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.DataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.library.functional.RequestStatus.Complete
import ru.pixnews.library.test.MainCoroutineExtension
import ru.pixnews.library.test.TestingLoggers

@ExtendWith(MockKExtension::class)
class FirebaseDataSourceTest {
    private val serializers = mapOf(
        DarkModeTestExperiment.key to DarkModeTestExperiment.Serializer,
        HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Serializer,
    )

    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()

    @MockK
    lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var dataSource: FirebaseDataSource

    @BeforeEach
    fun setUp() {
        every { remoteConfig.activate() } returns Tasks.forResult(true)
        every { remoteConfig.ensureInitialized() } returns Tasks.forResult(mockk<FirebaseRemoteConfigInfo>())
        every { remoteConfig.fetch() } returns Tasks.forResult(null)

        val backgroundDispatcher = StandardTestDispatcher(
            scheduler = coroutinesExt.testScope.testScheduler,
            name = "Background dispatcher",
        )

        dataSource = FirebaseDataSource(
            remoteConfig = remoteConfig,
            serializers = serializers,
            rootCoroutineScope = coroutinesExt.testScope.backgroundScope,
            backgroundDispatcher = backgroundDispatcher,
            logger = TestingLoggers.consoleLogger,
        )
    }

    private suspend fun loadVariant(key: ExperimentKey): Either<FeatureToggleDataSourceError, ExperimentVariant> {
        return dataSource
            .getAssignedVariant(key)
            .filterIsInstance<Complete<FeatureToggleDataSourceError, ExperimentVariant>>()
            .first()
            .result
    }

    @Test
    fun `getAssignedVariant() should return valid remote config variant`() = coroutinesExt.runTest {
        every {
            remoteConfig.getValue(DarkModeTestExperiment.key.stringValue)
        } returns FakeFirebaseRemoteConfigValue("true")

        val variant: Either<FeatureToggleDataSourceError, ExperimentVariant> = loadVariant(DarkModeTestExperiment.key)

        variant shouldBeRight DarkModeTestExperiment.activeGroup
    }

    @Test
    @DisplayName("getAssignedVariant() should return ExperimentNotFound for an experiment not created on server")
    fun getAssignedVariantNoExperiment() = coroutinesExt.runTest {
        every {
            remoteConfig.getValue(HomeScreenGameCardTestExperiment.key.stringValue)
        } returns FakeFirebaseRemoteConfigValue("", source = FirebaseRemoteConfig.VALUE_SOURCE_STATIC)

        val variant = loadVariant(HomeScreenGameCardTestExperiment.key)

        variant shouldBeLeft ExperimentNotFound
    }

    @Test
    fun `getAssignedVariant() should return DataSourceError on deserialization error`() = coroutinesExt.runTest {
        every {
            remoteConfig.getValue(DarkModeTestExperiment.key.stringValue)
        } returns FakeFirebaseRemoteConfigValue("Value in unknown format")

        val variant = loadVariant(HomeScreenGameCardTestExperiment.key)

        variant.shouldBeLeft().should(beInstanceOf<DataSourceError>())
    }

    @Test
    fun `getAssignedVariant() should return DataSourceError on unknown experiment`() = coroutinesExt.runTest {
        every {
            remoteConfig.getValue(DtfIntegrationTestExperiment.key.stringValue)
        } returns FakeFirebaseRemoteConfigValue("true")

        val variant = loadVariant(DtfIntegrationTestExperiment.key)

        variant.shouldBeLeft().should(beInstanceOf<DataSourceError>())
    }
}
