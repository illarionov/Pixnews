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
package ru.pixnews.foundation.featuretoggles.datasource.overrides

import app.cash.turbine.test
import arrow.core.Either
import arrow.core.right
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.fail
import io.kotest.assertions.throwables.shouldNotThrowAnyUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldMatchEach
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.api.io.TempDir
import ru.pixnews.foundation.featuretoggles.DefaultExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantKey.Companion.CONTROL_GROUP
import ru.pixnews.foundation.featuretoggles.FeatureToggleException
import ru.pixnews.foundation.featuretoggles.experimentKey
import ru.pixnews.foundation.featuretoggles.fixtures.DarkModeTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.DtfIntegrationTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.DataSourceError
import ru.pixnews.foundation.featuretoggles.internal.FeatureToggleDataSourceError.ExperimentNotFound
import ru.pixnews.foundation.featuretoggles.serializers.SerializationException
import ru.pixnews.library.functional.RequestStatus
import ru.pixnews.library.functional.RequestStatus.Complete
import ru.pixnews.library.test.MainCoroutineExtension
import java.io.File

class OverridesDataSourceTest {
    @JvmField
    @RegisterExtension
    var coroutinesExt: MainCoroutineExtension = MainCoroutineExtension()

    @JvmField
    @TempDir
    var tempDir: File? = null
    val dataSource: OverridesDataSource get() = dataSourceInitializer.dataSource

    internal lateinit var dataSourceInitializer: DataSourceUnderTestInitializer

    internal fun createDataSourceInitializer(
        initialOverrides: Map<ExperimentKey, ExperimentVariant> = emptyMap(),
    ): DataSourceUnderTestInitializer = DataSourceUnderTestInitializer(
        tempDir = tempDir!!,
        testScope = coroutinesExt.testScope,
        initialOverrides = initialOverrides,
    )

    suspend fun OverridesDataSource.loadVariant(
        key: ExperimentKey,
    ): Either<FeatureToggleDataSourceError, ExperimentVariant> {
        return getAssignedVariant(key)
            .filterIsInstance<Complete<FeatureToggleDataSourceError, ExperimentVariant>>()
            .first()
            .result
    }

    suspend fun OverridesDataSource.getVariant(
        key: ExperimentKey,
    ): Either<FeatureToggleDataSourceError, ExperimentVariant> {
        val result = getAssignedVariant(key)
            .first()
        result should beInstanceOf<Complete<FeatureToggleDataSourceError, ExperimentVariant>>()
        return (result as Complete<FeatureToggleDataSourceError, ExperimentVariant>).result
    }

    @Test
    fun `getAssignedVariant() should return ExperimentNotFound When Empty`() = coroutinesExt.runTest {
        val initializer = createDataSourceInitializer()
        val variant = initializer.dataSource.loadVariant(DarkModeTestExperiment.key)
        variant shouldBeLeft (ExperimentNotFound)
    }

    @Test
    fun `setOverride() should throw exception on experiment without serializer`() = coroutinesExt.runTest {
        val dataSource = createDataSourceInitializer().dataSource
        dataSource.awaitUntilInitialized()
        shouldThrow<FeatureToggleException> {
            dataSource.setOverride("non.existent".experimentKey(), DefaultExperimentVariant(key = CONTROL_GROUP))
        }

        shouldNotThrowAnyUnit {
            dataSource.setOverride("non.existent".experimentKey(), null)
        }
    }

    @Test
    fun `setOverride() should throw exception on serialization error`() = coroutinesExt.runTest {
        val dataSource = DataSourceUnderTestInitializer(
            tempDir = tempDir!!,
            testScope = coroutinesExt.testScope,
            initialOverrides = mapOf(DarkModeTestExperiment.key to DarkModeTestExperiment.activeGroup),
            serializers = mapOf(
                DarkModeTestExperiment.key to DarkModeTestExperiment.Serializer,
                HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Serializer,
                DtfIntegrationTestExperiment.key to ThrowingExceptionSerializer(),
            ),
        ).dataSource
        dataSource.awaitUntilInitialized()

        shouldThrow<SerializationException> {
            dataSource.setOverride(DtfIntegrationTestExperiment.key, DefaultExperimentVariant(key = CONTROL_GROUP))
        }
    }

    @Test
    fun `getOverride() should return an error and not stop on a serialization error`() = coroutinesExt.runTest {
        val dataSource = DataSourceUnderTestInitializer(
            tempDir = tempDir!!,
            testScope = coroutinesExt.testScope,
            initialOverrides = mapOf(
                DarkModeTestExperiment.key to DarkModeTestExperiment.activeGroup,
                HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Variant.V3Group,
            ),
            populateDataStoreFileSerializers = mapOf(
                DarkModeTestExperiment.key to DarkModeTestExperiment.Serializer,
                HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Serializer,
            ),
            serializers = mapOf(
                DarkModeTestExperiment.key to ThrowingExceptionSerializer(),
                HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Serializer,
            ),
        ).dataSource
        dataSource.awaitUntilInitialized()

        val loadedVariants = listOf(
            DarkModeTestExperiment.key,
            DarkModeTestExperiment.key,
            HomeScreenGameCardTestExperiment.key,
        ).map {
            advanceUntilIdle()
            dataSource.getVariant(it)
        }

        loadedVariants.shouldMatchEach(
            {
                it.shouldBeLeft().should(beInstanceOf<DataSourceError>())
            },
            {
                it.shouldBeLeft().should(beInstanceOf<DataSourceError>())
            },
            {
                it shouldBeRight HomeScreenGameCardTestExperiment.Variant.V3Group
            },
        )
    }

    @Test
    fun `getOverride() should return an error and not stop on experiment without serializer`() = coroutinesExt.runTest {
        val dataSource = DataSourceUnderTestInitializer(
            tempDir = tempDir!!,
            testScope = coroutinesExt.testScope,
            initialOverrides = mapOf(
                DarkModeTestExperiment.key to DarkModeTestExperiment.activeGroup,
                HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Variant.V3Group,
            ),
            populateDataStoreFileSerializers = mapOf(
                DarkModeTestExperiment.key to DarkModeTestExperiment.Serializer,
                HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Serializer,
            ),
            serializers = mapOf(
                DarkModeTestExperiment.key to DarkModeTestExperiment.Serializer,
            ),
        ).dataSource
        dataSource.awaitUntilInitialized()

        val loadedVariants = listOf(
            HomeScreenGameCardTestExperiment.key,
            DarkModeTestExperiment.key,
        ).map {
            advanceUntilIdle()
            dataSource.getVariant(it)
        }

        loadedVariants.shouldMatchEach(
            {
                it.shouldBeLeft().should(beInstanceOf<DataSourceError>())
            },
            {
                it shouldBeRight DarkModeTestExperiment.activeGroup
            },
        )
    }

    @Nested
    @DisplayName("Tests with preset overrides")
    internal inner class GetAssistedVariantWithOverride {
        @BeforeEach
        fun setupDataSource() {
            dataSourceInitializer = createDataSourceInitializer(
                initialOverrides = mapOf(DarkModeTestExperiment.key to DarkModeTestExperiment.activeGroup),
            )
        }

        @Test
        fun `getAssignedVariant() should always return overridden variant`() = coroutinesExt.runTest {
            val loadMultipleTimes = (1..3).map {
                dataSource.loadVariant(DarkModeTestExperiment.key)
            }

            loadMultipleTimes.forAll { result ->
                result.shouldBeRight(DarkModeTestExperiment.activeGroup)
            }
        }

        @Test
        @DisplayName("getAssignedVariant() should return ExperimentNotFound on non-overridden experiment")
        fun getAssignedVariantNonOverriddenExperiment() = coroutinesExt.runTest {
            val variant = dataSource.loadVariant(DtfIntegrationTestExperiment.key)
            variant shouldBeLeft ExperimentNotFound
        }

        @Test
        fun `getAssignedVariant() should return correct overrides after setOverride()`() = coroutinesExt.runTest {
            dataSource.awaitUntilInitialized()

            dataSource.setOverride(
                HomeScreenGameCardTestExperiment.key,
                HomeScreenGameCardTestExperiment.Variant.V2Group,
            )

            val loadedVariants = listOf(
                HomeScreenGameCardTestExperiment.key,
                DarkModeTestExperiment.key,
                DtfIntegrationTestExperiment.key,
                "nonexistent.experiment".experimentKey(),
            ).map {
                advanceUntilIdle()
                dataSource.getVariant(it)
            }

            loadedVariants.shouldMatchEach(
                {
                    it shouldBeRight HomeScreenGameCardTestExperiment.Variant.V2Group
                },
                {
                    it shouldBeRight DarkModeTestExperiment.activeGroup
                },
                {
                    it shouldBeLeft ExperimentNotFound
                },
                {
                    it shouldBeLeft ExperimentNotFound
                },
            )
        }

        @Test
        fun `setOverride(null) should reset override`() = coroutinesExt.runTest {
            dataSource.awaitUntilInitialized()

            dataSource.setOverride(DarkModeTestExperiment.key, null)

            val variant = dataSource.getVariant(DarkModeTestExperiment.key)

            variant shouldBeLeft (ExperimentNotFound)
        }

        @Test
        fun `getAssignedVariant() should return correct overrides after multiple calls to the setOverride()`() =
            coroutinesExt.runTest {
                dataSource.awaitUntilInitialized()

                listOf(
                    DarkModeTestExperiment.controlGroup,
                    DarkModeTestExperiment.activeGroup,
                    DarkModeTestExperiment.activeGroup,
                    DarkModeTestExperiment.controlGroup,
                    DarkModeTestExperiment.controlGroup,
                    DarkModeTestExperiment.activeGroup,
                ).forEach {
                    dataSource.setOverride(DarkModeTestExperiment.key, it)
                    val variant = dataSource.getVariant(DarkModeTestExperiment.key)

                    variant shouldBeRight it
                }
            }

        @Test
        fun `getAssignedVariant() should not return Loading status after initialized`() = coroutinesExt.runTest {
            dataSource.awaitUntilInitialized()
            val variant = dataSource.getAssignedVariant(DarkModeTestExperiment.key).first()

            variant should beInstanceOf<Complete<FeatureToggleDataSourceError, ExperimentVariant>>()
        }

        @Test
        fun `getAssistedVariant() flow should update when new value is set`() = coroutinesExt.runTest {
            val variantFlow = dataSource.getAssignedVariant(DarkModeTestExperiment.key)

            variantFlow.test {
                var lastReceivedStatus: RequestStatus<FeatureToggleDataSourceError, ExperimentVariant> =
                    RequestStatus.Loading
                while (lastReceivedStatus !is Complete) {
                    lastReceivedStatus = awaitItem()
                }
                val firstOverride = lastReceivedStatus.result
                firstOverride shouldBeRight DarkModeTestExperiment.activeGroup

                dataSource.setOverride(DarkModeTestExperiment.key, DarkModeTestExperiment.controlGroup)
                val afterUpdate = (awaitItem() as Complete).result
                afterUpdate shouldBeRight DarkModeTestExperiment.controlGroup

                dataSource.setOverride(DarkModeTestExperiment.key, null)
                val afterReset = (awaitItem() as Complete).result
                afterReset shouldBeLeft ExperimentNotFound
            }
        }

        @Test
        @DisplayName("getAssignedVariant() flow should emmit ExperimentNotFound when resetting the override")
        fun getAssignedVariantAfterResetOverride() = coroutinesExt.runTest {
            val variantFlow: Flow<Complete<FeatureToggleDataSourceError, ExperimentVariant>> =
                dataSource.getAssignedVariant(DarkModeTestExperiment.key).filterIsInstance()

            variantFlow.test {
                val firstOverride = awaitItem().result
                firstOverride shouldBeRight DarkModeTestExperiment.activeGroup

                dataSource.setOverride(DarkModeTestExperiment.key, null)

                val afterReset = awaitItem().result
                afterReset shouldBeLeft ExperimentNotFound
            }
        }

        @Test
        @DisplayName("getAssignedVariant() flow should emmit ExperimentNotFound when resetting all overrides")
        fun getAssignedVariantAfterResettingOverrides() = coroutinesExt.runTest {
            val variantFlow: Flow<Complete<FeatureToggleDataSourceError, ExperimentVariant>> =
                dataSource.getAssignedVariant(DarkModeTestExperiment.key).filterIsInstance()

            variantFlow.test {
                val firstOverride = awaitItem().result
                firstOverride shouldBeRight DarkModeTestExperiment.activeGroup

                dataSource.clearOverrides()

                val afterReset = awaitItem().result
                afterReset shouldBeLeft ExperimentNotFound
            }
        }

        @Test
        @DisplayName("getAssignedVariant() should return updated value when called from collect callback")
        fun getAssignedVariantFromCollectCallback() = coroutinesExt.runTest {
            val testKey = DarkModeTestExperiment.key
            val testVariant = DarkModeTestExperiment.controlGroup

            dataSource.awaitUntilInitialized()
            val collectJob = launch {
                dataSource.getAssignedVariant(testKey)
                    .filterIsInstance<Complete<*, ExperimentVariant>>()
                    .filter { complete -> complete.result.getOrNull() == testVariant }
                    .collect { _ ->
                        val variant = dataSource.getVariant(testKey)
                        variant shouldBeRight testVariant
                        cancel()
                    }
            }
            dataSource.setOverride(testKey, testVariant)
            withTimeout(5000) {
                collectJob.join()
            }
        }

        @Test
        fun `clearOverrides() should clear overrides`() = coroutinesExt.runTest {
            dataSource.awaitUntilInitialized()

            dataSource.clearOverrides()

            val variant = dataSource.getVariant(DarkModeTestExperiment.key)

            variant shouldBeLeft (ExperimentNotFound)
        }
    }

    @Nested
    @DisplayName("getOverrides() tests")
    inner class GetOverridesTests {
        private val initialOverrides = mapOf(
            DarkModeTestExperiment.key to DarkModeTestExperiment.activeGroup,
            HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Variant.V2Group,
        )

        @BeforeEach
        fun setupDataSource() {
            dataSourceInitializer = createDataSourceInitializer(
                initialOverrides = initialOverrides,
            )
        }

        @Test
        fun `getOverrides() should return correct values`() = coroutinesExt.runTest {
            dataSource.awaitUntilInitialized()

            val firstStatus = dataSource.getOverrides().first()
            val overrides = (firstStatus as? Complete<*, Map<ExperimentKey, ExperimentVariant>>)?.result
                ?: fail("Received status should be of type Complete")

            overrides.shouldBeRight().shouldContainExactly(initialOverrides)
        }

        @Test
        fun `getOverrides() flow should update when overrides changed`() = coroutinesExt.runTest {
            val overridesFlow: Flow<Complete<*, Map<ExperimentKey, ExperimentVariant>>> =
                dataSource.getOverrides().filterIsInstance()
            overridesFlow.test {
                val firstOverrides = awaitItem().result

                firstOverrides shouldBeRight initialOverrides

                dataSource.setOverride(DarkModeTestExperiment.key, null)
                awaitItem()
                dataSource.setOverride(DtfIntegrationTestExperiment.key, DtfIntegrationTestExperiment.activeGroup)

                val overridesAfterUpdate = awaitItem().result
                val expectedMap = initialOverrides - DarkModeTestExperiment.key +
                        (DtfIntegrationTestExperiment.key to DtfIntegrationTestExperiment.activeGroup)

                overridesAfterUpdate shouldBeRight expectedMap
            }
        }

        @Test
        fun `getOverrides() flow should return empty map after clear overrides`() = coroutinesExt.runTest {
            dataSource.awaitUntilInitialized()

            dataSource.clearOverrides()
            val result = dataSource.getOverrides().first()

            result shouldBe Complete(emptyMap<ExperimentKey, ExperimentVariant>().right())
        }

        @Test
        fun `getOverrides() should return updated values when called from collect`() = coroutinesExt.runTest {
            val removedKey = DarkModeTestExperiment.key
            val expectedOverrides = initialOverrides - removedKey

            dataSource.awaitUntilInitialized()
            val collectJob = launch {
                dataSource.getOverrides()
                    .filterIsInstance<Complete<*, Map<ExperimentKey, ExperimentVariant>>>()
                    .filter { complete -> complete.result.getOrNull() == expectedOverrides }
                    .collect { _ ->
                        val firstStatus = dataSource
                            .getOverrides()
                            .first() as? Complete<*, Map<ExperimentKey, ExperimentVariant>>
                        val variant = firstStatus?.result ?: fail("Received status should be of type Complete")
                        variant shouldBeRight expectedOverrides
                        cancel()
                    }
            }
            dataSource.setOverride(removedKey, null)
            withTimeout(5000) {
                collectJob.join()
            }
        }
    }
}
