/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.featuretoggles.datasource.overrides

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.featuretoggles.ExperimentVariant
import ru.pixnews.foundation.featuretoggles.ExperimentVariantSerializer
import ru.pixnews.foundation.featuretoggles.fixtures.DarkModeTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.DtfIntegrationTestExperiment
import ru.pixnews.foundation.featuretoggles.fixtures.HomeScreenGameCardTestExperiment
import ru.pixnews.library.test.TestingLoggers
import java.io.File

class DataSourceUnderTestInitializer(
    tempDir: File,
    testScope: TestScope,
    serializers: Map<ExperimentKey, ExperimentVariantSerializer> = mapOf(
        DarkModeTestExperiment.key to DarkModeTestExperiment.Serializer,
        HomeScreenGameCardTestExperiment.key to HomeScreenGameCardTestExperiment.Serializer,
        DtfIntegrationTestExperiment.key to DtfIntegrationTestExperiment.Serializer,
    ),
    populateDataStoreFileSerializers: Map<ExperimentKey, ExperimentVariantSerializer> = serializers,
    initialOverrides: Map<ExperimentKey, ExperimentVariant> = emptyMap(),
) {
    val datastoreFile: File
    val dataStore: DataStore<Overrides>
    val dataSource: OverridesDataSource

    init {
        val backgroundDispatcher = StandardTestDispatcher(
            scheduler = testScope.testScheduler,
            name = "Background dispatcher",
        )

        datastoreFile = tempDir.resolve("datastore.pb")
        if (initialOverrides.isNotEmpty()) {
            populateDataStoreFile(datastoreFile, populateDataStoreFileSerializers, initialOverrides)
        }

        dataStore = DataStoreFactory.create(
            serializer = OverridesSerializer,
            scope = testScope,
            produceFile = { datastoreFile },
        )

        dataSource = OverridesDataSource(
            dataStore = dataStore,
            serializers = serializers,
            rootCoroutineScope = testScope.backgroundScope,
            backgroundDispatcher = backgroundDispatcher,
            logger = TestingLoggers.consoleLogger,
        )
    }
}
