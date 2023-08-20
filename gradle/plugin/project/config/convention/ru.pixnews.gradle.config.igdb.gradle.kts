/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import buildparameters.BuildParametersExtension
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import ru.pixnews.gradle.config.igdb.GenerateIgdbOptionsTask
import ru.pixnews.gradle.config.igdb.IgdbConfigReader
import ru.pixnews.gradle.config.util.toProperties
import ru.pixnews.gradle.config.util.withAnyOfAndroidPlugins

/**
 * Convention plugin that generates GeneratedIgdbConfig using values from
 * configuration file
 */
project.withAnyOfAndroidPlugins { _, androidComponentsExtension ->
    with(androidComponentsExtension) {
        registerIgdbOptionsTask()
    }
}

fun AndroidComponentsExtension<*, *, *>.registerIgdbOptionsTask() {
    val pixnewsConfigFileContent = providers.fileContents(
        rootProject.layout.projectDirectory.file(
            providers.provider { extensions.getByType<BuildParametersExtension>().config },
        ),
    )

    onVariants { variant ->
        val igdbOptionsProvider = pixnewsConfigFileContent.asText.map { configFileText ->
            IgdbConfigReader(configFileText.toProperties()).read()
        }

        @Suppress("GENERIC_VARIABLE_WRONG_DECLARATION")
        val igdbOptionsTaskProvider = project.tasks.register<GenerateIgdbOptionsTask>(
            "${variant.name}GenerateIgdbOptions",
        ) {
            group = "Build"
            igdbConfig.set(igdbOptionsProvider)
            sourceOutputDir.set(layout.buildDirectory.dir("igdb-options"))
        }

        variant.sources.java?.addGeneratedSourceDirectory(
            taskProvider = igdbOptionsTaskProvider,
            wiredWith = GenerateIgdbOptionsTask::sourceOutputDir,
        )
    }
}
